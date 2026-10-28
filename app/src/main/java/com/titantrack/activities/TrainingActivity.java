package com.titantrack.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.titantrack.R;
import com.titantrack.adapters.WorkoutSetAdapter;
import com.titantrack.models.WorkoutSet;
import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {

    private TextInputEditText etWeight, etReps;
    private TextView tvTimer, tvExerciseNameDisplay, tvRestIndicator;
    private MaterialButton btnRegisterSet, btnFinishWorkout;

    // Cambiado a RecyclerView por el feedback
    private RecyclerView rvSessionLog;
    private WorkoutSetAdapter setAdapter;

    // Estructura en memoria temporal
    private ArrayList<WorkoutSet> sessionData;
    private CountDownTimer restTimer;

    // 60 segundos de descanso
    private final long REST_TIME_MS = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        sessionData = new ArrayList<>();

        // Vincular las vistas de la UI
        etWeight = findViewById(R.id.etWeight);
        etReps = findViewById(R.id.etReps);
        tvTimer = findViewById(R.id.tvTimer);
        tvExerciseNameDisplay = findViewById(R.id.tvExerciseNameDisplay);
        tvRestIndicator = findViewById(R.id.tvRestIndicator);
        btnRegisterSet = findViewById(R.id.btnRegisterSet);
        rvSessionLog = findViewById(R.id.rvSessionLog);

        // --- BOTONES ---
        btnRegisterSet = findViewById(R.id.btnRegisterSet);

        btnFinishWorkout = findViewById(R.id.btnFinishWorkout);
        btnFinishWorkout.setOnClickListener(v -> saveWorkoutToDatabase());

        // Cargar el nombre del ejercicio si venimos del Adapter del catálogo
        String exerciseName = getIntent().getStringExtra("EXERCISE_NAME");
        if (exerciseName != null) {
            tvExerciseNameDisplay.setText(exerciseName);
        }

        // Configurar el RecyclerView con el nuevo Adapter de tarjetas
        rvSessionLog.setLayoutManager(new LinearLayoutManager(this));
        setAdapter = new WorkoutSetAdapter(sessionData);
        rvSessionLog.setAdapter(setAdapter);

        // Acción principal
        btnRegisterSet.setOnClickListener(v -> registerSetAndStartTimer());
    }

    private void registerSetAndStartTimer() {
        String weightStr = etWeight.getText().toString().trim();
        String repsStr = etReps.getText().toString().trim();

        // Evitar crasheos si le dan al botón sin rellenar los datos
        if (weightStr.isEmpty() || repsStr.isEmpty()) {
            Toast.makeText(this, "Introduce peso y repeticiones", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ojo: parsear a double para admitir decimales en los kilos
        double weight = Double.parseDouble(weightStr);
        int reps = Integer.parseInt(repsStr);

        // Instancio la serie y la guardo en el ArrayList
        WorkoutSet newSet = new WorkoutSet(reps, weight);
        sessionData.add(newSet);

        // Refresco la lista visual
        updateSessionLog();

        // Limpio las cajas de texto y quito el foco para la siguiente serie
        etWeight.setText("");
        etReps.setText("");
        etWeight.clearFocus();
        etReps.clearFocus();

        startRestTimer();
    }

    private void updateSessionLog() {
        // Aviso al adaptador de que la lista ha cambiado
        setAdapter.notifyDataSetChanged();

        // Hago scroll automático hacia abajo para ver siempre la última serie insertada
        if (sessionData.size() > 0) {
            rvSessionLog.scrollToPosition(sessionData.size() - 1);
        }
    }

    private void startRestTimer() {
        // Por si acaso, cancelo el timer anterior para evitar superposiciones
        if (restTimer != null) {
            restTimer.cancel();
        }

        // Bloqueo interfaz de entrada
        btnRegisterSet.setEnabled(false);
        tvRestIndicator.setVisibility(View.VISIBLE);

        restTimer = new CountDownTimer(REST_TIME_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                // Formateo forzado a dos dígitos (ej. 00:08)
                tvTimer.setText(String.format("00:%02d", seconds));
            }

            @Override
            public void onFinish() {
                // Rehabilitar formulario
                tvTimer.setText("00:00");
                tvRestIndicator.setVisibility(View.INVISIBLE);
                btnRegisterSet.setEnabled(true);
                Toast.makeText(TrainingActivity.this, "¡A por la siguiente serie!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void saveWorkoutToDatabase() {
        // Si le dan a guardar sin haber hecho ni una serie, avisamos
        if (sessionData.isEmpty()) {
            Toast.makeText(this, "No hay series registradas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Preparamos los datos que vamos a guardar
        String exerciseName = tvExerciseNameDisplay.getText().toString();
        String summary = "Completadas " + sessionData.size() + " series.";
        String currentDate = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());

        // Llamamos a tu clase DatabaseHelper y ejecutamos el INSERT
        com.titantrack.db.DatabaseHelper db = new com.titantrack.db.DatabaseHelper(this);
        db.insertWorkout(exerciseName, summary, currentDate);

        Toast.makeText(this, "Entrenamiento guardado en disco", Toast.LENGTH_LONG).show();

        // Cerramos la pantalla de entrenamiento para volver al Dashboard
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Matar el timer si el usuario cierra la pantalla de golpe para no generar fugas de memoria
        if (restTimer != null) {
            restTimer.cancel();
        }
    }
}