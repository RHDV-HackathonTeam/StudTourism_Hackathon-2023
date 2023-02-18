package ru.denfad.studturism;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.denfad.studturism.Model.Price;
import ru.denfad.studturism.Model.Service;
import ru.denfad.studturism.Sevice.MainService;


public class PricesFragment extends Fragment {

    int hostelId;
    List<Price> prices;
    List<Service> services;

    public PricesFragment(int hostelId) {
        // Required empty public constructor
        this.hostelId = hostelId;
        prices = MainService.getInstance().getPricesOfHostel(hostelId);
        services = MainService.getInstance().getServicesOfHostel(hostelId);
    }

    public static PricesFragment newInstance(int hostelId) {
        PricesFragment fragment = new PricesFragment(hostelId);
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
        View rootView = inflater.inflate(R.layout.fragment_prices, container, false);

        RecyclerView priceList = rootView.findViewById(R.id.prices_list);
        PricesAdapter priceAdapter = new PricesAdapter();
        priceList.setAdapter(priceAdapter);
        priceList.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView serviceList = rootView.findViewById(R.id.services_list);
        ServicesAdapter serviceAdapter = new ServicesAdapter();
        serviceList.setAdapter(serviceAdapter);
        serviceList.setLayoutManager(new LinearLayoutManager(getContext()));

        return  rootView;
    }


    class ServicesAdapter extends RecyclerView.Adapter<PricesViewHolder> {

        @NonNull
        @Override
        public PricesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.price_item, parent, false);

            // Return a new holder instance
            PricesViewHolder viewHolder = new PricesViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PricesViewHolder holder, int position) {
            Service s = services.get(position);
            holder.name.setText(s.name);
            holder.price.setText(s.price + " ₽");
            holder.description.setText(s.description);
        }

        @Override
        public int getItemCount() {
            return services.size();
        }
    }

    class PricesAdapter extends RecyclerView.Adapter<PricesViewHolder> {
        @NonNull
        @Override
        public PricesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.price_item, parent, false);

            // Return a new holder instance
            PricesViewHolder viewHolder = new PricesViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PricesViewHolder holder, int position) {
            Price p = prices.get(position);
            holder.name.setText(p.name);
            holder.price.setText(p.price + " ₽");
            holder.description.setText(p.description);
        }

        @Override
        public int getItemCount() {
            return prices.size();
        }
    }

    class PricesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView description;
        public PricesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.price_card_name);
            price = itemView.findViewById(R.id.price_card_price);
            description = itemView.findViewById(R.id.price_card_description);

        }
    }
}