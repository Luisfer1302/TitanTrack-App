package com.titantrack.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.titantrack.R;
import com.titantrack.models.WorkoutSet;
import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {

    private TextInputEditText etWeight, etReps;
    private TextView tvTimer, tvSessionLog, tvExerciseNameDisplay, tvRestIndicator;
    private MaterialButton btnRegisterSet;

    // Estructura para el registro temporal
    private ArrayList<WorkoutSet> sessionData;
    private CountDownTimer restTimer;

    // Configuración de descanso
    private final long REST_TIME_MS = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // Inicializar la estructura en memoria
        sessionData = new ArrayList<>();

        // Vincular Vistas
        etWeight = findViewById(R.id.etWeight);
        etReps = findViewById(R.id.etReps);
        tvTimer = findViewById(R.id.tvTimer);
        tvSessionLog = findViewById(R.id.tvSessionLog);
        tvExerciseNameDisplay = findViewById(R.id.tvExerciseNameDisplay);
        tvRestIndicator = findViewById(R.id.tvRestIndicator);
        btnRegisterSet = findViewById(R.id.btnRegisterSet);

        // Recuperar el nombre del ejercicio
        String exerciseName = getIntent().getStringExtra("EXERCISE_NAME");
        if (exerciseName != null) {
            tvExerciseNameDisplay.setText(exerciseName);
        }

        btnRegisterSet.setOnClickListener(v -> registerSetAndStartTimer());
    }

    private void registerSetAndStartTimer() {
        String weightStr = etWeight.getText().toString();
        String repsStr = etReps.getText().toString();

        if (weightStr.isEmpty() || repsStr.isEmpty()) {
            Toast.makeText(this, "Introduce peso y repeticiones", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Captura de Métricas Temporales
        double weight = Double.parseDouble(weightStr);
        int reps = Integer.parseInt(repsStr);

        // 2. Registro Dinámico en Memoria
        WorkoutSet newSet = new WorkoutSet(reps, weight);
        sessionData.add(newSet);

        // Actualizar el log visual
        updateSessionLog();

        // Limpiar campos
        etWeight.setText("");
        etReps.setText("");

        // 3. Iniciar CountDownTimer
        startRestTimer();
    }

    private void updateSessionLog() {
        StringBuilder log = new StringBuilder();
        for (int i = 0; i < sessionData.size(); i++) {
            log.append("Serie ").append(i + 1).append(": ")
                    .append(sessionData.get(i).toString()).append("\n");
        }
        tvSessionLog.setText(log.toString());
    }

    private void startRestTimer() {
        if (restTimer != null) {
            restTimer.cancel();
        }

        // Bloquear botón durante el descanso
        btnRegisterSet.setEnabled(false);
        tvRestIndicator.setVisibility(View.VISIBLE);

        restTimer = new CountDownTimer(REST_TIME_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Actualizar interfaz en tiempo real
                long seconds = millisUntilFinished / 1000;
                String timeFormatted = String.format("00:%02d", seconds);
                tvTimer.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                // Habilitar siguiente serie
                tvTimer.setText("00:00");
                tvRestIndicator.setVisibility(View.INVISIBLE);
                btnRegisterSet.setEnabled(true);
                Toast.makeText(TrainingActivity.this, "¡Fin del descanso! A por la siguiente serie", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (restTimer != null) {
            restTimer.cancel();
        }
    }
}