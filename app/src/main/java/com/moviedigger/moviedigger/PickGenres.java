package com.moviedigger.moviedigger;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class PickGenres extends AppCompatActivity {

    ChipGroup chipGroup;
    ArrayList<Integer> checked = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_genres);

        chipGroup = findViewById(R.id.chipGroup);

        getCheckedFromServer();

        if(checked.size() != 0){
            // check the chips
        }

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(ChipGroup cg, @IdRes int checkedId){
                Toast.makeText(PickGenres.this,((Chip)findViewById(checkedId)).getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCheckedFromServer(){}
}
