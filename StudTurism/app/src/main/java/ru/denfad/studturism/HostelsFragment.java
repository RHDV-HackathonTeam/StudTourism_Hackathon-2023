package ru.denfad.studturism;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.ui.TopSheetBehavior;


public class HostelsFragment extends Fragment {

    private MainService service;


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
        service = MainService.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_hostels, container, false);

        View sheet = rootView.findViewById(R.id.top_sheet_behavior);



        RecyclerView list = rootView.findViewById(R.id.hostels_list);
        HostelAdapter adapter = new HostelAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageButton filter = rootView.findViewById(R.id.filter_button);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopSheetBehavior.from(sheet).setState(TopSheetBehavior.STATE_EXPANDED);
            }
        });

        Button room = rootView.findViewById(R.id.room);
        room.setOnClickListener(new View.OnClickListener() {
            boolean click = false;

            @Override
            public void onClick(View view) {
                if(!click) room.setBackgroundColor(getResources().getColor(R.color.purple_700));
                else room.setBackgroundColor(getResources().getColor(R.color.purple_500));
                click = !click;
            }
        });

        Button flat = rootView.findViewById(R.id.flat);
        flat.setOnClickListener(new View.OnClickListener() {
            boolean click = false;

            @Override
            public void onClick(View view) {
                if(!click) flat.setBackgroundColor(getResources().getColor(R.color.purple_700));
                else flat.setBackgroundColor(getResources().getColor(R.color.purple_500));
                click = !click;
            }
        });

        Button applyFilter = rootView.findViewById(R.id.apply_filter);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopSheetBehavior.from(sheet).setState(TopSheetBehavior.STATE_COLLAPSED);
            }
        });
        return rootView;
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
            Hostel h = service.getAll().get(position);

            holder.name.setText(h.name);
            holder.image.setImageResource(h.imageId);
            holder.price.setText(h.price + " ₽");
            holder.town.setText(h.town);
            holder.organization.setText(h.organization);
            holder.daysCount.setText(String.format("От %d до %d дней", h.minDayCount, h.maxDayCount));
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
            return service.getAll().size();
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