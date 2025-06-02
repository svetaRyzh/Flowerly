package com.example.flowerly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class BouquetAdapter extends RecyclerView.Adapter<BouquetAdapter.BouquetViewHolder> {

    private List<Bouquet> bouquets;
    private OnBouquetClickListener listener;

    public interface OnBouquetClickListener {
        void onBouquetClick(Bouquet bouquet);
    }

    public BouquetAdapter(List<Bouquet> bouquets, OnBouquetClickListener listener) {
        this.bouquets = bouquets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BouquetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bouquet, parent, false);
        return new BouquetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BouquetViewHolder holder, int position) {
        Bouquet bouquet = bouquets.get(position);
        holder.bind(bouquet, listener);
    }

    @Override
    public int getItemCount() {
        return bouquets.size();
    }

    static class BouquetViewHolder extends RecyclerView.ViewHolder {
        private ImageView bouquetImage;
        private TextView bouquetName;
        private TextView bouquetPrice;
        private Button addToCart;

        public BouquetViewHolder(@NonNull View itemView) {
            super(itemView);
            bouquetImage = itemView.findViewById(R.id.bouquet_image);
            bouquetName = itemView.findViewById(R.id.bouquet_name);
            bouquetPrice = itemView.findViewById(R.id.bouquet_price);
            addToCart = itemView.findViewById(R.id.add_to_cart);
        }

        public void bind(Bouquet bouquet, OnBouquetClickListener listener) {
            Glide.with(itemView.getContext())
                    .load(bouquet.getImageUrl())
                    .placeholder(R.drawable.error_image)
                    .error(R.drawable.error_image)      // Картинка при ошибке загрузки
                    .into(bouquetImage);
            bouquetName.setText(bouquet.getName());
            bouquetPrice.setText(String.format(Locale.getDefault(), "%,d ₽", bouquet.getPrice()));

            itemView.setOnClickListener(v -> listener.onBouquetClick(bouquet));

            addToCart.setOnClickListener(v -> {
                Cart.getInstance().addItem(bouquet);
                Toast.makeText(itemView.getContext(),
                        "Добавлено в корзину: " + bouquet.getName(),
                        Toast.LENGTH_SHORT).show();
            });
        }
    }
}