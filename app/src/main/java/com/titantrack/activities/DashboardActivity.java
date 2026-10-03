package com.titantrack.activities;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.titantrack.R;

public class DashboardActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView tvSectionContent = findViewById(R.id.tvSectionContent);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    tvSectionContent.setText(R.string.section_home);
                    return true;
                } else if (itemId == R.id.nav_workouts) {
                    tvSectionContent.setText(R.string.section_workouts);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    tvSectionContent.setText(R.string.section_profile);
                    return true;
                }
                return false;
            }

        });
    }
}
