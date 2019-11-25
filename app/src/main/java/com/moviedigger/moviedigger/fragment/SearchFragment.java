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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;
import com.moviedigger.moviedigger.Constants;
import com.moviedigger.moviedigger.R;
import com.moviedigger.moviedigger.RateMovies;
import com.moviedigger.moviedigger.RecommendedActivity;
import com.moviedigger.moviedigger.ratemoviesrecycler.ResultAdapter;
import com.moviedigger.moviedigger.ratemoviesrecycler.ResultData;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.MoviesList;
import com.moviedigger.moviedigger.retrofit.SetUserRating;
import com.pchmn.materialchips.ChipsInput;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class SearchFragment extends Fragment{

    RecyclerView search_view;
    Button button,search_submitrating;
    EditText search_moviesname;

    private ChipsInput chipsInput;
    private List<SearchChip> contactList = new ArrayList<>();
    Context context;
    ArrayList<ResultData> data = new ArrayList<ResultData>();
    ArrayList<Float> ratings = new ArrayList<Float>();
    ArrayList<Integer> id = new ArrayList<Integer>();
    ResultAdapter resultAdapter;

    ApiInterface apiInterface;

    LoadingDots search_loadingdots;

    public SearchFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(String x : getResources().getStringArray(R.array.genres))
            contactList.add(new SearchChip(x));
        resultAdapter = new ResultAdapter(data,context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  v =  inflater.inflate(R.layout.fragment_search, container, false);

        chipsInput = v.findViewById(R.id.chips_input);

        search_loadingdots = v.findViewById(R.id.search_loadingdots);

        chipsInput.setFilterableList(contactList);

        search_view = v.findViewById(R.id.search_view);

        search_view.setAdapter(resultAdapter);

        search_view.setLayoutManager(new LinearLayoutManager(context));

        search_moviesname = v.findViewById(R.id.search_moviename);

        search_submitrating = v.findViewById(R.id.search_submitrateing);
        search_submitrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMovies();
            }
        });

        button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitCall();
            }
        });



        //List<SearchChip> contactsSelected = (List<SearchChip>) chipsInput.getSelectedChipList();

        return v;
    }



    public void registerMovies(){

        ArrayList<Float> list = resultAdapter.getUserRatingList();
        ratings.clear();
        id.clear();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void retrofitCall() {
        List<SearchChip> contactsSelected = (List<SearchChip>) chipsInput.getSelectedChipList();
        String query = search_moviesname.getText().toString();
        String genre;
        search_loadingdots.setVisibility(View.VISIBLE);
        Retrofit retrofit = getClient();
        MoviesList moviesList = new MoviesList();
        if(query.length() != 0) {
            moviesList.setQuery(query);
        }
        if(contactsSelected.size() > 0) {
            genre = contactsSelected.get(0).getLabel();
            moviesList.setGenre(genre);
        }
        moviesList.setNum_movies(10);
        apiInterface = retrofit.create(ApiInterface.class);
        Call<MoviesList> call = apiInterface.getMovieList(moviesList);
        System.out.println("--------------------------------making call---------------------------------------");
        data.clear();
        resultAdapter.clear();
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if(response.body() != null){
                    int responsecode = response.body().getResponsecode();
                    String responseMessage = response.body().getResponsemessage();
                    if(responsecode == Constants.OK){
                        ArrayList<Integer> id = new ArrayList<Integer>(response.body().getMovieId());
                        ArrayList<String> name = new ArrayList<String>(response.body().getMovieNameList());
//                        ArrayList<Float> userrate = new ArrayList<Float>(response.body().getUserRatings());
                        System.out.println(id+"\n"+name);

//                        data.clear();
//                        resultAdapter.add(data);
                        for(int i = 0 ; i < id.size() ; i++){
                            ResultData rd = new ResultData(name.get(i), id.get(i));
                            //rd.setUserRating(userrate.get(i));
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
                search_loadingdots.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                search_loadingdots.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setMoviesCall() {
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
                        /////
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
