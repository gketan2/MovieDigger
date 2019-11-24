package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.GenresList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class SetGenres extends AppCompatActivity {

    TextView setgenres_submit;
    ListView setgenres_list;

    ApiInterface apiInterface;

    String[] GENRES ;
    ArrayList<String> selectedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_genres);
//
//        setgenres_submit = findViewById(R.id.setgenres_submit);
//        setgenres_list = findViewById(R.id.setgenres_list);
//
//        GENRES = getResources().getStringArray(R.array.genres);
//
//        setgenres_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        setgenres_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,GENRES));

        Intent i =new Intent(this,RateMovies.class);
        startActivity(i);


    }

    public void registerMovies(View v){

        SparseBooleanArray sp = setgenres_list.getCheckedItemPositions();
        selectedList.clear();
        for(int i = 0; i < sp.size(); i++){
            if(sp.valueAt(i))
                selectedList.add(GENRES[sp.keyAt(i)]);
        }
        retrofitCall();
    }

    private Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void retrofitCall() {
        final Context mContext = this;
        Retrofit retrofit = getClient();
        GenresList gl = new GenresList(getSharedPreferences("authDetails", Context.MODE_PRIVATE).getString("username",null),selectedList);
        apiInterface = retrofit.create(ApiInterface.class);
        Call<GenresList> call = apiInterface.setGenres(gl);
        call.enqueue(new Callback<GenresList>() {
            @Override
            public void onResponse(Call<GenresList> call, Response<GenresList> response) {
                if(response.body() != null){
                    String responseMessage = response.body().getResponsemessage();
                    int responseCode = response.body().getResponsecode();
                    if(responseCode == Constants.OK){
                        Intent i = new Intent(mContext,RateMovies.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(mContext,responseMessage,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext,"Something Went Wrong...",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenresList> call, Throwable t) {
                Toast.makeText(mContext,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
