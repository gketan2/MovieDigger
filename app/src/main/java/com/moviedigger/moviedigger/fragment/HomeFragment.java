package com.moviedigger.moviedigger.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moviedigger.moviedigger.Constants;
import com.moviedigger.moviedigger.R;
import com.moviedigger.moviedigger.RateMovies;
import com.moviedigger.moviedigger.resultrecycler.ResultAdapter;
import com.moviedigger.moviedigger.resultrecycler.ResultData;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.MoviesList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class HomeFragment extends Fragment {
    RecyclerView result_view;
    FloatingActionButton fab_reload;

    ArrayList<ResultData> data = new ArrayList<ResultData>();

    ResultAdapter resultAdapter;
    ApiInterface apiInterface;
    Context context;


    public HomeFragment(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data.clear();

        resultAdapter = new ResultAdapter(data,context);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        result_view = v.findViewById(R.id.result_view);

        result_view.setAdapter(resultAdapter);

        result_view.setLayoutManager(new LinearLayoutManager(context));

        retrofitCall();

        fab_reload = v.findViewById(R.id.fab_reload);

        fab_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitCall();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void retrofitCall() {
        Retrofit retrofit = getClient();
        MoviesList moviesList = new MoviesList(context.getSharedPreferences("authDetails", Context.MODE_PRIVATE).getString("username",null));
        apiInterface = retrofit.create(ApiInterface.class);
        Call<MoviesList> call = apiInterface.recommendedMovies(moviesList);
        System.out.println("--------------------------------making call---------------------------------------");
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if(response.body() != null){
                    int responsecode = response.body().getResponsecode();
                    String responseMessage = response.body().getResponsemessage();
                    if(responsecode == Constants.OK){
                        ArrayList<Integer> id = new ArrayList<Integer>(response.body().getMovieId());
                        ArrayList<String> name = new ArrayList<String>(response.body().getMovieNameList());
                        System.out.println(id+"\n"+name);

                        for(int i = 0 ; i < id.size() ; i++){
                            ResultData rd = new ResultData(name.get(i), id.get(i));
                            data.add(rd);
                        }
                        System.out.println(data);
                        resultAdapter.add(data);
                    }else if(responsecode == Constants.NO_HISTORY){
                        Intent i = new Intent(context, RateMovies.class);
                        startActivity(i);
                        Toast.makeText(context,responseMessage,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
