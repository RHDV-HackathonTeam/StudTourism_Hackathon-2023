package ru.denfad.studturism;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Sevice.MainService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        Hostel h = service.findHostel(hostelId);

        TextView name = rootView.findViewById(R.id.hostel_name);
        name.setText(h.name);
        TextView town = rootView.findViewById(R.id.hostel_town);
        town.setText(h.town);
        TextView organization = rootView.findViewById(R.id.hostel_organization);
        organization.setText(h.organization);
        ImageView image = rootView.findViewById(R.id.hostel_image);
        image.setImageResource(h.imageId);
        TextView daysCount = rootView.findViewById(R.id.hostel_day_count);
       daysCount.setText(String.format("От %d до %d дней", h.minDayCount, h.maxDayCount));

        return rootView;
    }
}