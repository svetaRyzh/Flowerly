package com.example.flowerly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CatalogFragment extends Fragment {
    private RecyclerView catalogRecycler;
    private BouquetAdapter adapter;
    private List<Bouquet> allBouquets = new ArrayList<>();
    private List<Bouquet> filteredBouquets = new ArrayList<>();
    private RangeSlider priceSlider;
    private TextView priceRangeText;
    private TextView emptyStateText;
    private float minPrice = 0;
    private float maxPrice = 5000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        // Инициализация элементов
        catalogRecycler = view.findViewById(R.id.catalog_recycler);
        priceSlider = view.findViewById(R.id.price_slider);
        priceRangeText = view.findViewById(R.id.price_range_text);
        emptyStateText = view.findViewById(R.id.empty_state_text);

        // Настройка RecyclerView
        catalogRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new BouquetAdapter(filteredBouquets, this::onBouquetClick);
        catalogRecycler.setAdapter(adapter);

        // Загрузка данных и настройка фильтров
        loadBouquets();
        setupFilters(view);

        return view;
    }

    private void onBouquetClick(Bouquet bouquet) {
        Toast.makeText(getContext(), "Выбран: " + bouquet.getName(), Toast.LENGTH_SHORT).show();
    }

    private void loadBouquets() {
        allBouquets.clear();
        allBouquets.addAll(JsonParser.parseBouquetsFromJson(requireContext()));
        filteredBouquets.addAll(allBouquets);
        if (filteredBouquets.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
        }
        adapter.notifyDataSetChanged();
    }

    private void setupFilters(View view) {
        // Настройка спиннера категорий
        Spinner categorySpinner = view.findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories_array,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        // Настройка RangeSlider
        priceSlider.setValueFrom(0);
        priceSlider.setValueTo(5000);
        priceSlider.setValues(minPrice, maxPrice);
        updatePriceText(); // Инициализируем текст

        priceSlider.addOnChangeListener((slider, value, fromUser) -> {
            minPrice = slider.getValues().get(0);
            maxPrice = slider.getValues().get(1);
            updatePriceText();
            applyFilters();
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // Новый метод для обновления текста диапазона цен
    private void updatePriceText() {
        priceRangeText.setText(String.format(Locale.getDefault(),
                "Цена: %d - %d ₽",
                (int)minPrice, (int)maxPrice));
    }

    private void applyFilters() {
        filteredBouquets.clear();

        String selectedCategory = ((Spinner)getView().findViewById(R.id.category_spinner))
                .getSelectedItem().toString();

        for (Bouquet bouquet : allBouquets) {
            boolean categoryMatches = selectedCategory.equals("Все") ||
                    bouquet.getCategory().equals(selectedCategory);
            boolean priceMatches = bouquet.getPrice() >= minPrice &&
                    bouquet.getPrice() <= maxPrice;

            if (categoryMatches && priceMatches) {
                filteredBouquets.add(bouquet);
            }
        }
        if (filteredBouquets.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
        }

        adapter.notifyDataSetChanged();
    }

    private void showEmptyState() {
        catalogRecycler.setVisibility(View.GONE);
        emptyStateText.setVisibility(View.VISIBLE);
        emptyStateText.setText("По выбранным фильтрам цветов не найдено");
    }

    private void hideEmptyState() {
        catalogRecycler.setVisibility(View.VISIBLE);
        emptyStateText.setVisibility(View.GONE);
    }
}