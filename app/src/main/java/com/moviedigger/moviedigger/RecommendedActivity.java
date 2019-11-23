package com.moviedigger.moviedigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.moviedigger.moviedigger.fragment.HomeFragment;
import com.moviedigger.moviedigger.fragment.ProfileFragment;
import com.moviedigger.moviedigger.fragment.SearchFragment;

public class RecommendedActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationItemView;

    Fragment home,search,profile;
    int current_frag = 0,top_frag = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);

        home = new HomeFragment(getBaseContext());
        search = new SearchFragment();
        profile = new ProfileFragment();

        fragmentTransaction(home);

        bottomNavigationItemView = findViewById(R.id.bottom_navigation);

        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment f = null;
                switch (item.getItemId()){
                    case R.id.home: current_frag = 0;if(home == null)home = new HomeFragment(getBaseContext());f = home;
                        break;
                    case R.id.search: current_frag = 1;if(search == null)search = new SearchFragment();f = search;
                        break;
                    case R.id.profile: current_frag = 2;if(profile == null)profile = new ProfileFragment();f = profile;
                        break;
                }
                fragmentTransaction(f);


                return true;
            }
        });




    }

    private void fragmentTransaction(Fragment f){

        Fragment f1 = null;
        if (f == null)
            return;
        if(top_frag == current_frag)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (top_frag){
            case 0:{
                if((f1 = fm.findFragmentByTag("0")) != null)
                    ft.hide(f1);
                if(f.isAdded())ft.show(f);
                else ft.add(R.id.fragment_container,f,""+current_frag);
            }
                break;
            case 1:{
                if((f1 = fm.findFragmentByTag("1")) != null)
                    ft.hide(f1);
                if(f.isAdded())ft.show(f);
                else ft.add(R.id.fragment_container,f,""+current_frag);
            }
                break;
            case 2:{
                if((f1 = fm.findFragmentByTag("2")) != null)
                    ft.hide(f1);
                if(f.isAdded())ft.show(f);
                else ft.add(R.id.fragment_container,f,""+current_frag);
            }
                break;
            default:ft.add(R.id.fragment_container,f,"0");
        }
        top_frag = current_frag;
        ft.commit();

    }

    @Override
    public void onBackPressed(){
        if(top_frag == 0)
            super.onBackPressed();
        else{
            if(home == null)home = new HomeFragment(getBaseContext());
            current_frag = 0;
            fragmentTransaction(home);

        }
    }

}
