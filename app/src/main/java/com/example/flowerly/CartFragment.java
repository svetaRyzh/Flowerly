package com.example.flowerly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemListener {
    private RecyclerView cartRecycler;
    private TextView totalPriceText;
    private Button checkoutButton;
    private CartAdapter adapter;
    private TextView emptyCart;
    private LinearLayout cartContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecycler = view.findViewById(R.id.cart_recycler);
        totalPriceText = view.findViewById(R.id.total_price_text);
        checkoutButton = view.findViewById(R.id.checkout_button);
        emptyCart = view.findViewById(R.id.empty_cart_text);
        cartContent = view.findViewById(R.id.cart_content);

        setupRecyclerView();
        updateTotalPrice();
        checkIfCartEmpty(); // Проверяем состояние корзины при создании

        checkoutButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Заказ оформлен!", Toast.LENGTH_SHORT).show();
            Cart.getInstance().clearCart();
            checkIfCartEmpty(); // Обновляем состояние после очистки
        });

        return view;
    }

    private void setupRecyclerView() {
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(Cart.getInstance().getItems(), this);
        cartRecycler.setAdapter(adapter);
    }

    private void updateTotalPrice() {
        int total = Cart.getInstance().getTotalPrice();
        totalPriceText.setText(String.format(Locale.getDefault(),
                "Итого: %,d ₽", total));
    }

    private void checkIfCartEmpty() {
        if (Cart.getInstance().getItems().isEmpty()) {
            showEmptyCart();
        } else {
            showCartContent();
        }
    }

    private void showEmptyCart() {
        emptyCart.setVisibility(View.VISIBLE);
        cartContent.setVisibility(View.GONE);
    }

    private void showCartContent() {
        emptyCart.setVisibility(View.GONE);
        cartContent.setVisibility(View.VISIBLE);
        updateCartItems();
    }

    private void updateCartItems() {
        adapter = new CartAdapter(Cart.getInstance().getItems(), this);
        cartRecycler.setAdapter(adapter);
        updateTotalPrice();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkIfCartEmpty();
    }

    @Override
    public void onItemRemoved() {
        checkIfCartEmpty();
    }
}
