package com.example.flowerly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recommendationsRecycler;
    private LinearLayout categoriesContainer;

    private List<Bouquet> bouquets = new ArrayList<>();
    private List<Category> categories = new ArrayList<Category>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Инициализация
        recommendationsRecycler = view.findViewById(R.id.recommendations_recycler);
        categoriesContainer = view.findViewById(R.id.categories_container);

        // Загрузка данных
        loadCategories();
        loadRecommendations();

        return view;
    }

    private void loadCategories() {
        categoriesContainer.removeAllViews();

        // Теперь используем изображения вместо иконок
        categories.add(new Category("Свадьба", R.drawable.wedding_category));
        categories.add(new Category("Горшки", R.drawable.pots_category));
        categories.add(new Category("Букеты", R.drawable.bouquets_category));
        categories.add(new Category("День рождения", R.drawable.birthday_category));
        categories.add(new Category("Яркие", R.drawable.bright_category));

        for (Category category : categories) {
            View categoryView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_category, categoriesContainer, false);

            ImageView image = categoryView.findViewById(R.id.category_image);
            TextView name = categoryView.findViewById(R.id.category_name);

            image.setImageResource(category.getImageRes());
            name.setText(category.getName());


            categoriesContainer.addView(categoryView);
        }
    }

    private void loadRecommendations() {
        bouquets.clear();
        bouquets.addAll(JsonParser.parseBouquetsFromJson(requireContext()));

        BouquetAdapter adapter = new BouquetAdapter(bouquets, bouquet -> {
            Toast.makeText(getContext(), "Выбран: " + bouquet.getName(), Toast.LENGTH_SHORT).show();
        });

        recommendationsRecycler.setAdapter(adapter);
    }
}