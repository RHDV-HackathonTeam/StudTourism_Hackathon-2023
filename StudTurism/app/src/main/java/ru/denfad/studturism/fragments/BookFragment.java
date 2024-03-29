package ru.denfad.studturism.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.Book;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;


public class BookFragment extends Fragment {

    int hostelId;
    Calendar dateAndTime= Calendar.getInstance();
    TextInputEditText visit;
    TextInputEditText exit;
    MainService service;
    private SharedPreferences mSharedPreferences;


    public BookFragment(int hostelId) {
        // Required empty public constructor
        this.hostelId = hostelId;
        service = MainService.getInstance();
    }


    public static BookFragment newInstance(int hostelId) {
        BookFragment fragment = new BookFragment(hostelId);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_book, container, false);
        TextInputEditText fio = rootView.findViewById(R.id.fio_text);
        TextInputEditText phone = rootView.findViewById(R.id.phone_text);
        visit = rootView.findViewById(R.id.visit_text);
        exit = rootView.findViewById(R.id.exit_text);
        TextInputEditText email = rootView.findViewById(R.id.email_text);
        TextInputEditText guestCount = rootView.findViewById(R.id.people_count_text);

        visit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) new DatePickerDialog(getContext(), visitListener,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        exit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) new DatePickerDialog(getContext(), exitListener,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        FormatWatcher phoneMask = new MaskFormatWatcher( // форматировать текст будет вот он
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        );
        phoneMask.installOn(phone);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Button book = rootView.findViewById(R.id.book_button);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book b = new Book(String.valueOf(hostelId), fio.getText().toString(), guestCount.getText().toString(), visit.getText().toString(), exit.getText().toString(), phone.getText().toString(), email.getText().toString(), mSharedPreferences.getString("username", "1"),  null);
                Log.e("Booking", mSharedPreferences.getString("username", "1"));
                NetworkService.getInstance().getJSONApi()
                        .book(b)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("Server", response.message());
                                Log.e("Server", response.toString());
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                service.addBook(b);
            }
        });
        return rootView;
    }

    DatePickerDialog.OnDateSetListener visitListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            visit.setText(DateUtils.formatDateTime(getContext(),
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    DatePickerDialog.OnDateSetListener exitListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            exit.setText(DateUtils.formatDateTime(getContext(),
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

}