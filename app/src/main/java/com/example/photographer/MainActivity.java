package com.example.photographer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton cameraButton;
    Button manualEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = findViewById(R.id.cameraButton);
        manualEntry = findViewById(R.id.manualEntry);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraButtonClick();
            }
        });
        manualEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onManualEntryButtonClick();
            }
        });
    }

    private void onCameraButtonClick() {
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    private void onManualEntryButtonClick() {
        /*
         * A blank EquationActivity is opened
         */
        Intent intent = new Intent(this, EquationActivity.class);
        intent.putExtra("latex", "");
        startActivity(intent);
    }
}
