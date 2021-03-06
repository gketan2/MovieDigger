package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moviedigger.moviedigger.ratemoviesrecycler.ResultAdapter;
import com.moviedigger.moviedigger.ratemoviesrecycler.ResultData;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.MoviesList;
import com.moviedigger.moviedigger.retrofit.SetUserRating;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class RateMovies extends AppCompatActivity {

    RecyclerView ratemovies_recyclerview;
    TextView ratemovies_submit;

    ResultAdapter ratemoviesAdapter;
    ApiInterface apiInterface;

    ArrayList<ResultData> data = new ArrayList<ResultData>();
    ArrayList<Float> ratings = new ArrayList<Float>();
    ArrayList<Integer> id = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movies);

        ratemovies_recyclerview = findViewById(R.id.ratemovies_recyclerview);
        ratemovies_submit = findViewById(R.id.ratemovies_submit);

        ratemoviesAdapter = new ResultAdapter(data,this);

        ratemovies_recyclerview.setAdapter(ratemoviesAdapter);

        ratemovies_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        ratemovies_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMovies();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getMoviesCall();
    }

    public void registerMovies(){

        ArrayList<Float> list = ratemoviesAdapter.getUserRatingList();

        if(list.size() == data.size()){
            for(int i = 0 ; i < list.size() ; i++){
                if(list.get(i) != 0){
                    ratings.add(list.get(i));
                    id.add(data.get(i).getMovieId());
                }
            }
            setMoviesCall();
        }

    }

    private Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void getMoviesCall() {
        final Context context = this;
        Retrofit retrofit = getClient();
        MoviesList moviesList = new MoviesList(context.getSharedPreferences("authDetails", Context.MODE_PRIVATE).getString("username",null));

        moviesList.setNum_movies(15);
        apiInterface = retrofit.create(ApiInterface.class);
        Call<MoviesList> call = apiInterface.getMovieList(moviesList);
        System.out.println("--------------------------------making call---------------------------------------");
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if(response.body() != null){
                    int responsecode = response.body().getResponsecode();
                    String responsemessage = response.body().getResponsemessage();
                    if(responsecode == Constants.OK){
                        ArrayList<Integer> id = new ArrayList<Integer>(response.body().getMovieId());
                        ArrayList<String> name = new ArrayList<String>(response.body().getMovieNameList());
                        System.out.println(id+"\n"+name);

                        for(int i = 0 ; i < id.size() ; i++){
                            ResultData rd = new ResultData(name.get(i), id.get(i));
                            data.add(rd);
                        }
                        System.out.println(data);
                        ratemoviesAdapter.add(data);
                    }else{
                        Toast.makeText(context,responsemessage,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setMoviesCall() {

        final Context context = this;
        Retrofit retrofit = getClient();

        SetUserRating userRating = new SetUserRating(context.getSharedPreferences("authDetails", Context.MODE_PRIVATE).getString("username",null),ratings,id);
        apiInterface = retrofit.create(ApiInterface.class);

        Call<SetUserRating> call = apiInterface.rateMovies(userRating);
        System.out.println("--------------------------------making call---------------------------------------");
        call.enqueue(new Callback<SetUserRating>() {
            @Override
            public void onResponse(Call<SetUserRating> call, Response<SetUserRating> response) {
                if(response.body() != null){
                    int responsecode = response.body().getResponsecode();
                    String responsemessage = response.body().getResponsemessage();
                    if(responsecode == Constants.OK){
                        Intent i = new Intent(context,RecommendedActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(context, responsemessage, Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Toast.makeText(context, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SetUserRating> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
