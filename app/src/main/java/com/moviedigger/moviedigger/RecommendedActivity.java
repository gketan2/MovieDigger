package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.moviedigger.moviedigger.resultrecycler.ResultAdapter;
import com.moviedigger.moviedigger.resultrecycler.ResultData;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.LoginData;
import com.moviedigger.moviedigger.retrofit.MoviesList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class RecommendedActivity extends AppCompatActivity {

    RecyclerView resultView;

    List<ResultData> data = new ArrayList<>();

    ResultAdapter resultAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        resultView = findViewById(R.id.result_view);


        //retrofitCall();

        data.add(new ResultData("name1",550));
        data.add(new ResultData("name2",552));

        resultAdapter = new ResultAdapter(data,this);
        resultView.setAdapter(resultAdapter);

        resultView.setLayoutManager(new LinearLayoutManager(this));
    }

    public Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void retrofitCall() {
        Retrofit retrofit = getClient();
        MoviesList moviesList = new MoviesList(getSharedPreferences("authDetails", Context.MODE_PRIVATE).getString("username",null));
        apiInterface = retrofit.create(ApiInterface.class);
        Call<MoviesList> call = apiInterface.recommendedMovies(moviesList);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if(response.body() != null){

                    List<Integer> id = response.body().getMovieId();
                    List<String> name = response.body().getMovieNameList();

                    for(int i = 0 ; i < id.size() ; i++){
                        ResultData rd = new ResultData(name.get(i), id.get(i));
                        data.add(rd);
                    }
                    resultAdapter.add(data);
                }
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {

            }
        });

    }
}
