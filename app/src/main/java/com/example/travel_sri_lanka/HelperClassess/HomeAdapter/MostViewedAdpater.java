package com.example.travel_sri_lanka.HelperClassess.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_sri_lanka.R;

import java.util.ArrayList;

public class MostViewedAdpater extends RecyclerView.Adapter<MostViewedAdpater.MostViewedViewHolder> {
    ArrayList<MostViewedHelperClass> mostViewedLocations;
    public MostViewedAdpater(ArrayList<MostViewedHelperClass> mostViewedLocations) {
        this.mostViewedLocations = mostViewedLocations;
    }
    @NonNull
    @Override
    public MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design, parent, false);
        MostViewedViewHolder mostViewedViewHolder = new MostViewedViewHolder(view);
        return mostViewedViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MostViewedViewHolder holder, int position) {
        MostViewedHelperClass helperClass = mostViewedLocations.get(position);
        holder.imageView.setImageResource(helperClass.getImage());
        holder.textView.setText(helperClass.getTitle());
        holder.desc.setText(helperClass.getDescription());
    }
    @Override
    public int getItemCount() {
        return mostViewedLocations.size();
    }
    public static class MostViewedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, desc;
        public MostViewedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.w2_image);
            textView = itemView.findViewById(R.id.w2_title);
            desc = itemView.findViewById(R.id.w2_desc);
        }
    }
}