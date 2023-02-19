package ru.denfad.studturism.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;

public class HostelFragment extends Fragment {

    MainService service = MainService.getInstance();
    int hostelId;

    public HostelFragment(int hostelId) {
        // Required empty public constructor
        this.hostelId = hostelId;
    }


    public static HostelFragment newInstance(int hostelId) {
        HostelFragment fragment = new HostelFragment(hostelId);
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
        View rootView = inflater.inflate(R.layout.fragment_hostel, container, false);

        TextView name = rootView.findViewById(R.id.hostel_name);
        TextView town = rootView.findViewById(R.id.hostel_town);
        TextView organization = rootView.findViewById(R.id.hostel_organization);
        ImageView image = rootView.findViewById(R.id.hostel_image);
        TextView daysCount = rootView.findViewById(R.id.hostel_day_count);
        TextView dayCount = rootView.findViewById(R.id.hostel_day_count);
        TextView contact = rootView.findViewById(R.id.hostel_contacts_name);
        TextView email = rootView.findViewById(R.id.hostel_contacts_mail);
        TextView orgTerm = rootView.findViewById(R.id.hostel_org_term);
        TextView indTerm = rootView.findViewById(R.id.hostel_indep_term);

        NetworkService.getInstance()
                .getJSONApi()
                .getHostel(hostelId)
                .enqueue(new Callback<Hostel>() {
                    @Override
                    public void onResponse(Call<Hostel> call, Response<Hostel> response) {
                        Hostel h = response.body();
                        name.setText(h.hostel);
                        town.setText(h.city);
                        organization.setText(h.university);
                        //image.setImageResource(h.imageId);
                        daysCount.setText(h.period);
                        dayCount.setText(h.period);
                        contact.setText(h.organization);
                       email.setText(h.email);

                        Picasso.get().load(h.pictureUrl).into(image);

                       if(h.conditionsForOrganizations == null)   orgTerm.setText("Отсутсвуют");
                       else orgTerm.setText(h.conditionsForOrganizations);
                        if(h.conditionsForStudents == null)   indTerm.setText("Отсутсвуют");
                        else indTerm.setText(h.conditionsForStudents);

                    }

                    @Override
                    public void onFailure(Call<Hostel> call, Throwable t) {

                    }
                });


        return rootView;
    }
}