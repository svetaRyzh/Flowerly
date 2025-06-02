package com.example.flowerly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class EnterFragment extends Fragment {
    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter, container, false);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        Button btnRegister = view.findViewById(R.id.btnRegister);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.checkUser(email, password)) {
                    saveLoginState(true, email);
                    ((MainActivity) requireActivity()).navigateToHome();
                } else {
                    Toast.makeText(getContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.addUser(email, password)) {
                    Toast.makeText(getContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ошибка регистрации (возможно, email уже занят)", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void saveLoginState(boolean isLoggedIn, String email) {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("IS_LOGGED_IN", isLoggedIn);
        editor.putString("USER_EMAIL", email);
        editor.apply();
    }


    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}