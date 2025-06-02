package com.example.flowerly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvEmail, tvPasswordHint;
    private Button btnLogout;
    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvEmail = view.findViewById(R.id.tvEmail);
        tvPasswordHint = view.findViewById(R.id.tvPasswordHint);
        btnLogout = view.findViewById(R.id.btnLogout);

        loadUserData();
        setupLogoutButton();

        return view;
    }

    private void loadUserData() {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String email = sharedPref.getString("USER_EMAIL", "Неизвестный пользователь");

        tvEmail.setText("Email: " + email);
        tvPasswordHint.setText("Пароль: ********"); // Пароль не хранится в открытом виде
    }

    private void setupLogoutButton() {
        btnLogout.setOnClickListener(v -> {
            // Очищаем данные авторизации
            SharedPreferences sharedPref = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("IS_LOGGED_IN", false);
            editor.remove("USER_EMAIL");
            editor.apply();

            // Переходим на экран входа
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToLogin();
            }
        });
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}