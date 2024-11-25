package gunung;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gohike.R;

import java.util.List;

public class gunungAdapter extends RecyclerView.Adapter<gunungAdapter.GunungViewHolder> {

    private List<gunung> gunungList;

    public interface OnGunungClickListener {
        void onGunungClick(gunung gunung);
    }

    public void setOnGunungClickListener(OnGunungClickListener listener) {
    }

    // Setter untuk mengisi data gunung
    @SuppressLint("NotifyDataSetChanged")
    public void setGunungList(List<gunung> gunungList) {
        this.gunungList = gunungList;
        notifyDataSetChanged(); // Memastikan RecyclerView di-update
    }

    @NonNull
    @Override
    public GunungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activiy_home, parent, false);
        return new GunungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GunungViewHolder holder, int position) {
        gunung currentGunung = gunungList.get(position);
        if (currentGunung != null) {
            holder.imageView.setImageResource(currentGunung.getResourceId());
            holder.textView.setText(currentGunung.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (gunungList != null) {
            return gunungList.size();
        }
        return 0;
    }

    // ViewHolder untuk memegang referensi komponen pada item layout
    public static class GunungViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public GunungViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.bromo);
        }
    }
}
