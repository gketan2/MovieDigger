package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RecommendedActivity extends AppCompatActivity {

    RecyclerView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        resultView = findViewById(R.id.resultview);
    }
}
