package com.example.gohike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter_Opentrip extends RecyclerView.Adapter<MyAdapter_Opentrip.AnggotaViewHolder> implements Filterable {

    private List<data_class_uploaddata> anggotaList;
    private List<data_class_uploaddata> anggotaListFiltered;
    private Context context;

    // Constructor
    public MyAdapter_Opentrip(List<data_class_uploaddata> anggotaList) {
        this.anggotaList = anggotaList;
        this.anggotaListFiltered = anggotaList;
    }

    @Override
    public AnggotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item1, parent, false);
        return new AnggotaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnggotaViewHolder holder, int position) {
        data_class_uploaddata anggota = anggotaListFiltered.get(position);
        holder.nameTextView.setText(anggota.getName());
        holder.addressTextView.setText(anggota.getAddress());
        holder.emailTextView.setText(anggota.getEmail());
        holder.ageTextView.setText(anggota.getAge());
    }

    @Override
    public int getItemCount() {
        return anggotaListFiltered.size();
    }

    // ViewHolder class to represent each item
    public static class AnggotaViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, addressTextView, emailTextView, ageTextView;

        public AnggotaViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.detailName);
            addressTextView = view.findViewById(R.id.detailAddress);
            emailTextView = view.findViewById(R.id.detailEmail);
            ageTextView = view.findViewById(R.id.detailAge);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<data_class_uploaddata> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(anggotaList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (data_class_uploaddata item : anggotaList) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                anggotaListFiltered.clear();
                if (results.values != null) {
                    anggotaListFiltered.addAll((List) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}
