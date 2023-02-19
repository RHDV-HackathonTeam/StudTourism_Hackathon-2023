package ru.denfad.studturism.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.Filter;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.DownloadImage;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;
import ru.denfad.studturism.activities.HostelActivity;
import ru.denfad.studturism.ui.TopSheetBehavior;


public class HostelsFragment extends Fragment {

    private List<Hostel> hostels = new ArrayList<>();
    private SmartMaterialSpinner<String> regions;
    private SmartMaterialSpinner<String> federals;
    private SmartMaterialSpinner<String> towns;
    private boolean type = true; //false - квартира true - комната
    private Button flat;
    private Button room;
    private  RecyclerView list;



    public HostelsFragment() {
        // Required empty public constructor
    }


    public static HostelsFragment newInstance() {
        HostelsFragment fragment = new HostelsFragment();

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

        View rootView = inflater.inflate(R.layout.fragment_hostels, container, false);

        View sheet = rootView.findViewById(R.id.top_sheet_behavior);



        list = rootView.findViewById(R.id.hostels_list);
        NetworkService.getInstance()
                .getJSONApi()
                .getAllHostels()
                .enqueue(new Callback<List<Hostel>>() {
                    @Override
                    public void onResponse(Call<List<Hostel>> call, Response<List<Hostel>> response) {
                        Log.e("Server", response.message());
                        Log.e("Server", response.toString());
                        hostels = response.body();
                        if(hostels != null) {
                            HostelAdapter adapter = new HostelAdapter();
                            list.setAdapter(adapter);
                            list.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                        else Log.e("Hostels", "Null hostels");

                    }

                    @Override
                    public void onFailure(Call<List<Hostel>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


        ImageButton filter = rootView.findViewById(R.id.filter_button);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopSheetBehavior.from(sheet).setState(TopSheetBehavior.STATE_EXPANDED);
            }
        });

        Button room = rootView.findViewById(R.id.room);
        Button flat = rootView.findViewById(R.id.flat);
        room.setBackgroundColor(getResources().getColor(R.color.purple_700));
        room.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(!type){
                    room.setBackgroundColor(getResources().getColor(R.color.purple_700));
                    flat.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    type = !type;
                }
                else {
                    room.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    flat.setBackgroundColor(getResources().getColor(R.color.purple_700));
                    type = !type;
                }
            }
        });


        flat.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(type){
                    room.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    flat.setBackgroundColor(getResources().getColor(R.color.purple_700));
                    type = !type;
                }
                else {
                    room.setBackgroundColor(getResources().getColor(R.color.purple_700));
                    flat.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    type = !type;
                }
            }
        });



        regions = rootView.findViewById(R.id.region);
        regions.setItem(MainService.getInstance().getRegions());
       regions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });





       towns = rootView.findViewById(R.id.town);
        towns.setItem(MainService.getInstance().getTowns());
        towns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        RadioGroup bedCount = rootView.findViewById(R.id.bed_count);
        bedCount.check(R.id.difference);

        RadioGroup food = rootView.findViewById(R.id.food_type);
        food.check(R.id.f1);

        Button applyFilter = rootView.findViewById(R.id.apply_filter);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = food.getCheckedRadioButtonId();

                String bed = ((RadioButton)rootView.findViewById(bedCount.getCheckedRadioButtonId())).getText().toString();
                if(bed.equals("Неважно")) bed = null;
                String food = ((RadioButton)rootView.findViewById(id)).getText().toString();
                String flatType = type ? "квартира" : "комната";
                Filter f = new Filter(regions.getSelectedItem(), towns.getSelectedItem(), flatType, bed, food);
                Log.e("Filter", f.region + " " + f.city + " " + f.flatType + " "+f.bedCount + " " +f.food);
                NetworkService.getInstance()
                        .getJSONApi()
                        .filter(f)
                        .enqueue(new Callback<List<Hostel>>() {
                            @Override
                            public void onResponse(Call<List<Hostel>> call, Response<List<Hostel>> response) {
                                Log.e("Server",  bodyToString(call.request().body()));
                                Log.e("Server", response.message());
                                Log.e("Server", response.toString());

                                hostels = response.body();
                                Log.e("Server", new Gson().toJson(hostels.get(0).rates));
                                if(hostels != null) {
                                    HostelAdapter adapter = new HostelAdapter();
                                    list.setAdapter(adapter);
                                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                                }
                                else Log.e("Hostels", "Null hostels");


                            }

                            @Override
                            public void onFailure(Call<List<Hostel>> call, Throwable t) {

                            }
                        });
                TopSheetBehavior.from(sheet).setState(TopSheetBehavior.STATE_COLLAPSED);
            }
        });
        return rootView;
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


    class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> {
        @NonNull
        @Override
        public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.hostel_item, parent, false);

            // Return a new holder instance
            HostelViewHolder viewHolder = new HostelViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HostelViewHolder holder, int position) {
            Hostel h = hostels.get(position);
            holder.name.setText(h.hostel);

            Log.e("Hostel", new Gson().toJson(h.rates));
            Picasso.get().load(h.pictureUrl).into(holder.image);
            try {
                holder.price.setText(h.rates.get(0).price + " ₽");
            }
            catch (Exception e) {
                e.printStackTrace();
                holder.price.setText( "0 ₽");
            }

            holder.town.setText(h.city);
            holder.organization.setText(h.university);
            holder.daysCount.setText(h.period);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), HostelActivity.class);
                    intent.putExtra("id", h.id);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return hostels.size();
        }

        class HostelViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView town;
            TextView organization;
            TextView price;
            TextView daysCount;
            ImageView image;
            CardView card;
            public HostelViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.hostel_card_name);
                image = itemView.findViewById(R.id.hostel_card_image);
                town = itemView.findViewById(R.id.hostel_card_town);
                organization = itemView.findViewById(R.id.hostel_card_organization);
                price = itemView.findViewById(R.id.hostel_card_price);
                daysCount = itemView.findViewById(R.id.day_count_text);
                card = itemView.findViewById(R.id.hostel_card);
            }
        }

    }


    }
