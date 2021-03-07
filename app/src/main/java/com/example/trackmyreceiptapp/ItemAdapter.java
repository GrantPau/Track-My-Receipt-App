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
        holder.itemname.setText(item.get(position).getItemname());
        holder.itemdate.setText(item.get(position).getItemdate());
        holder.itemname.setText(item.get(position).getItemname());

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemname, itemdate, itemprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = (TextView) itemView.findViewById(R.id.item_name);
            itemdate = (TextView) itemView.findViewById(R.id.item_date);
            itemprice = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}
