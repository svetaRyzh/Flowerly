package com.example.flowerly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Bouquet> cartItems;
    private OnCartItemListener onCartItemListener;

    public interface OnCartItemListener {
        void onItemRemoved();
    }

    public CartAdapter(List<Bouquet> cartItems, OnCartItemListener listener) {
        this.cartItems = cartItems;
        this.onCartItemListener = listener;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Bouquet bouquet = cartItems.get(position);
        holder.bind(bouquet, position, onCartItemListener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView bouquetImage;
        private TextView bouquetName;
        private TextView bouquetPrice;
        private Button removeFromCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bouquetImage = itemView.findViewById(R.id.cart_item_image);
            bouquetName = itemView.findViewById(R.id.cart_item_name);
            bouquetPrice = itemView.findViewById(R.id.cart_item_price);
            removeFromCart = itemView.findViewById(R.id.remove_from_cart);
        }

        public void bind(Bouquet bouquet, int position, OnCartItemListener listener) {
            Glide.with(itemView.getContext())
                    .load(bouquet.getImageUrl())
                    .placeholder(R.drawable.error_image)
                    .error(R.drawable.error_image)      // Картинка при ошибке загрузки
                    .into(bouquetImage);
            bouquetName.setText(bouquet.getName());
            bouquetPrice.setText(String.format(Locale.getDefault(), "%,d ₽", bouquet.getPrice()));

            removeFromCart.setOnClickListener(v -> {
                Cart.getInstance().removeItem(bouquet);
                listener.onItemRemoved();
            });
        }
    }
}