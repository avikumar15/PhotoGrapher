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
        /*
         * For the time being let the string 'latexString' be returned from the API that retrieves
         * the math equations from the image. Now, we have to pass this into the webView and update
         * the contents of the math input box. This is demonstrated by passing a test LaTex string
         * to the webView.
         */
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        //Intent intent = new Intent(MainActivity.this, EquationActivity.class);
        startActivity(intent);
    }

    private void onManualEntryButtonClick() {
        Intent intent = new Intent(this, EquationActivity.class);
        intent.putExtra("latex", "");
        startActivity(intent);
    }
}
