package com.titantrack.activities;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.titantrack.R;
import com.titantrack.adapters.HistoryAdapter;
import com.titantrack.db.DatabaseHelper;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        // 1. Instanciar Helper y pedir Cursor
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getAllWorkouts();

        ArrayList<String> historyList = new ArrayList<>();

        // 2. Extraer datos del Cursor
        if (cursor.moveToFirst()) {
            do {
                String exercise = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE));
                String summary = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUMMARY));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

                historyList.add(date + " - " + exercise + "\n" + summary);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // 3. Conectar lógicamente al adaptador
        HistoryAdapter adapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(adapter);
    }
}