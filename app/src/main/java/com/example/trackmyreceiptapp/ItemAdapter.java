package com.example.trackmyreceiptapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<item> item;

    public ItemAdapter(Context c, ArrayList<item> p) {
        context = c;
        item = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_array, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_name.setText(item.get(position).getItem_name());
        holder.item_date.setText(item.get(position).getItem_date());
        holder.item_price.setText(item.get(position).getItem_price());

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_date, item_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_date = (TextView) itemView.findViewById(R.id.item_date);
            item_price = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}
