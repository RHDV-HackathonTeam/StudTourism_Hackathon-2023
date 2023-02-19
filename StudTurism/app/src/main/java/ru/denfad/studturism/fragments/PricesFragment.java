package ru.denfad.studturism.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Model.Rate;
import ru.denfad.studturism.Model.Service;
import ru.denfad.studturism.R;
import ru.denfad.studturism.Sevice.MainService;
import ru.denfad.studturism.Sevice.NetworkService;


public class PricesFragment extends Fragment {

    int hostelId;
    List<Rate> rates;
    List<Service> services;
    private Hostel hostel;

    public PricesFragment(int hostelId) {
        // Required empty public constructor
        this.hostelId = hostelId;
        rates = MainService.getInstance().getPricesOfHostel(hostelId);
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
        RecyclerView serviceList = rootView.findViewById(R.id.services_list);
        NetworkService.getInstance()
                .getJSONApi()
                .getHostel(hostelId)
                .enqueue(new Callback<Hostel>() {
                    @Override
                    public void onResponse(Call<Hostel> call, Response<Hostel> response) {
                        hostel = response.body();
                        if(hostel.rates.size()>0) {
                            PricesAdapter priceAdapter = new PricesAdapter();
                            priceList.setAdapter(priceAdapter);
                            priceList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }


                        if(hostel.services.size() > 0) {
                            ServicesAdapter serviceAdapter = new ServicesAdapter();
                            serviceList.setAdapter(serviceAdapter);
                            serviceList.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                        else {
                            serviceList.setVisibility(View.GONE);
                            TextView text = rootView.findViewById(R.id.none_service);
                            text.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Hostel> call, Throwable t) {

                    }
                });
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
            Service s = hostel.services.get(position);
            holder.name.setText(s.service);
            holder.price.setText(s.price + " ₽");
            holder.description.setText(s.description);
        }

        @Override
        public int getItemCount() {
            return hostel.services.size();
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


            Rate p = hostel.rates.get(position);
                holder.name.setText(p.roomType.toUpperCase() + ", колличество кроватей: " + p.bedCount);
                holder.price.setText(p.price + " ₽");
                holder.description.setText(p.description);

        }

        @Override
        public int getItemCount() {
            return hostel.rates.size();
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