package com.titantrack.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton; // Añadida la importación del botón
import com.google.android.material.navigation.NavigationBarView;
import com.titantrack.R;
import com.titantrack.adapters.ExerciseAdapter;
import com.titantrack.models.Exercise;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView rvExercises;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView tvSectionContent = findViewById(R.id.tvSectionContent);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        rvExercises = findViewById(R.id.rvExercises);

        // --- INICIO CÓDIGO NUEVO (Botón de Historial) ---
        MaterialButton btnViewHistory = findViewById(R.id.btnViewHistory);
        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
        // --- FIN CÓDIGO NUEVO ---

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        initMockData();
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        rvExercises.setAdapter(exerciseAdapter);

        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String userEmail = prefs.getString(MainActivity.KEY_USER_EMAIL, "Usuario");

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    tvSectionContent.setText(getString(R.string.section_home));
                    rvExercises.setVisibility(View.GONE);
                    btnViewHistory.setVisibility(View.GONE); // Ocultar el botón si no estamos en workouts
                    return true;
                } else if (itemId == R.id.nav_workouts) {
                    tvSectionContent.setText(getString(R.string.section_workouts));
                    rvExercises.setVisibility(View.VISIBLE);
                    btnViewHistory.setVisibility(View.VISIBLE); // Mostrar el botón en la pestaña de workouts
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    tvSectionContent.setText("Sesión iniciada como:\n" + userEmail + "\n\n(Pulsa aquí para Cerrar Sesión)");
                    rvExercises.setVisibility(View.GONE);
                    btnViewHistory.setVisibility(View.GONE); // Ocultar el botón

                    // Pulsar texto para poder cerrar sesión
                    tvSectionContent.setOnClickListener(v -> logout());
                    return true;
                }
                return false;
            }
        });

        bottomNav.setSelectedItemId(R.id.nav_workouts);
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();

        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initMockData() {
        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Press de Banca Plano", "Pectoral / Tríceps", "4 series x 8-10 reps", "Intermedio"));
        exerciseList.add(new Exercise("Sentadilla con Barra", "Cuádriceps / Glúteo", "4 series x 6-8 reps", "Avanzado"));
        exerciseList.add(new Exercise("Peso Muerto", "Espalda / Isquios", "3 series x 5 reps", "Avanzado"));
        exerciseList.add(new Exercise("Press Militar con Barra", "Hombros / Core", "4 series x 8 reps", "Intermedio"));
        exerciseList.add(new Exercise("Dominadas Pronas Lastradas", "Dorsal / Bíceps", "4 series x 6-8 reps", "Avanzado"));
        exerciseList.add(new Exercise("Remo con Barra 90º", "Espalda Media / Trapecios", "4 series x 10 reps", "Intermedio"));
        exerciseList.add(new Exercise("Fondos en Paralelas", "Pectoral Inferior / Tríceps", "3 series x 10-12 reps", "Intermedio"));
        exerciseList.add(new Exercise("Curl de Bíceps con Barra Z", "Bíceps Braquial", "3 series x 12 reps", "Principiante"));
        exerciseList.add(new Exercise("Elevaciones Laterales con Mancuerna", "Deltoides Lateral", "4 series x 15 reps", "Principiante"));
        exerciseList.add(new Exercise("Prensa de Piernas 45º", "Cuádriceps / Glúteos", "4 series x 12 reps", "Principiante"));
        exerciseList.add(new Exercise("Extensiones de Tríceps en Polea", "Tríceps", "3 series x 15 reps", "Principiante"));
        exerciseList.add(new Exercise("Zancadas Búlgaras", "Glúteo / Cuádriceps", "3 series x 10 por pierna", "Intermedio"));
    }
}