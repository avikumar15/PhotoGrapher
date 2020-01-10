package com.example.photographer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.photographer.R;
import com.example.photographer.activities.MainActivity;

public class MainFragment extends Fragment {
    private ImageButton cameraButton;
    private Button manualEntry;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        cameraButton = view.findViewById(R.id.cameraButton);
        manualEntry = view.findViewById(R.id.manualEntry);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "camera button clicked", Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });

        manualEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Manual Entry button clicked", Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).setViewPager(2);
            }
        });
        return view;
    }

}
