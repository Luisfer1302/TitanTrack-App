package com.titantrack.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        // 1. Configurar RecyclerView con LayoutManager vertical
        rvExercises.setLayoutManager(new LinearLayoutManager(this));

        // 2. Poblar lista con datos simulados (Mock Data)
        initMockData();

        // 3. Vincular Adaptador
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        rvExercises.setAdapter(exerciseAdapter);

        // 4. Control del BottomNavigationView con visibilidad dinámica
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    tvSectionContent.setText(R.string.section_home);
                    rvExercises.setVisibility(View.GONE);
                    return true;
                } else if (itemId == R.id.nav_workouts) {
                    tvSectionContent.setText(R.string.section_workouts);
                    rvExercises.setVisibility(View.VISIBLE);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    tvSectionContent.setText(R.string.section_profile);
                    rvExercises.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        // Estado inicial en la sección de Rutinas
        bottomNav.setSelectedItemId(R.id.nav_workouts);
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