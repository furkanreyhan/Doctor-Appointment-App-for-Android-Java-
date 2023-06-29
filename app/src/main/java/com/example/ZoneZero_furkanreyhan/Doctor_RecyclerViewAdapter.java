package com.example.ZoneZero_furkanreyhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Doctor_RecyclerViewAdapter extends RecyclerView.Adapter<Doctor_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Doctor> doctors;

    Doctor_RecyclerViewAdapter(Context context, ArrayList<Doctor> doctors,
                               RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.doctors = doctors;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }


    @NonNull
    @Override
    public Doctor_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Doctor_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctor_RecyclerViewAdapter.MyViewHolder holder, int position) {
        String name = doctors.get(position).getFullName();

        holder.tvName.setText(name);
        Glide.with(this.context).load(doctors.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageViewPhoto);
            tvName = itemView.findViewById(R.id.textViewFullname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

