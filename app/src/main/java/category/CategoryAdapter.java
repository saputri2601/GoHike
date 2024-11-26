package category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gohike.R;

import java.util.List;

import gunung.gunungAdapter;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context mContext;
    private List<category> categoryList;
    private final OnCategoryClickListener categoryClickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryName);
    }

    public CategoryAdapter(Context mContext, OnCategoryClickListener listener) {
        this.mContext = mContext;
        this.categoryClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        category category = categoryList.get(position);
        if (category == null) {
            return;
        }

        holder.titleGunung.setText(category.getTitleGunung());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvGunung.setLayoutManager(linearLayoutManager);

        gunungAdapter gunungAdapter = new gunungAdapter();
        gunungAdapter.setGunungList(category.getGunungList());
        holder.rcvGunung.setAdapter(gunungAdapter);

        // Tambahkan listener klik untuk kategori
        holder.itemView.setOnClickListener(v -> {
            if (categoryClickListener != null) {
                categoryClickListener.onCategoryClick(category.getTitleGunung());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleGunung;
        private final RecyclerView rcvGunung;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleGunung = itemView.findViewById(R.id.titleGunung);
            rcvGunung = itemView.findViewById(R.id.rcvgunung);
        }
    }
}
