package com.titantrack.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titantrack.R;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "TitanTrackPrefs";
    public static final String KEY_IS_LOGGED = "isLoggedIn";
    public static final String KEY_USER_EMAIL = "userEmail";

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprobar sesión antes de inflar la vista
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.getBoolean(KEY_IS_LOGGED, false)) {
            navigateToDashboard();
            return;
        }

        setContentView(R.layout.activity_main);

        // Vincular vistas
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Navegación hacia Registro
        TextView tvGoToRegister = findViewById(R.id.tvGoToRegister);
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Botón de Login con validaciones lógicas
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            if (validateCredentials()) {
                // Guardar estado de sesión en SharedPreferences
                String email = etEmail.getText().toString().trim();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(KEY_IS_LOGGED, true);
                editor.putString(KEY_USER_EMAIL, email);
                editor.apply();

                // Navegar al Dashboard
                navigateToDashboard();
            }
        });
    }

    private boolean validateCredentials() {
        boolean isValid = true;

        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // Validación de Correo
        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.error_empty_email));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        } else {
            tilEmail.setError(null);
        }

        // Validación de Contraseña
        if (password.isEmpty()) {
            tilPassword.setError(getString(R.string.error_empty_password));
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.error_short_password));
            isValid = false;
        } else {
            tilPassword.setError(null);
        }

        return isValid;
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}