package com.moviedigger.moviedigger.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moviedigger.moviedigger.Constants;
import com.moviedigger.moviedigger.R;
import com.moviedigger.moviedigger.RateMovies;
import com.moviedigger.moviedigger.SignInActivity;
import com.moviedigger.moviedigger.profilerecycler.ResultAdapter;
import com.moviedigger.moviedigger.profilerecycler.ResultData;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.MoviesList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class ProfileFragment extends Fragment {

    Button b;
    RecyclerView profile_recycler;
    TextView profile_username;

    ResultAdapter resultAdapter;
    ApiInterface apiInterface;

    Context context;
    private ArrayList<ResultData> data = new ArrayList<>();
    String username;

    public ProfileFragment(Context context) {
        this.context = context;
        data.clear();
        resultAdapter = new ResultAdapter(data,context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = context.getSharedPreferences("authDetails",Context.MODE_PRIVATE);
        username = sp.getString("username","Not Signed In");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        profile_username = v.findViewById(R.id.profile_username);
        profile_username.setText(username);

        b = v.findViewById(R.id.profile_signout);
        profile_recycler = v.findViewById(R.id.profile_recycler);

        profile_recycler.setAdapter(resultAdapter);

        profile_recycler.setLayoutManager(new LinearLayoutManager(context));

        retrofitCall();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("authDetails", Context.MODE_PRIVATE);
                sp.edit().clear().apply();
                Intent i = new Intent(getContext(), SignInActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        return v;
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
        Call<MoviesList> call = apiInterface.history(moviesList);
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
                    else if(responsecode == Constants.USER_NOT_EXIST){
                        Toast.makeText(context,responseMessage+"\nPlease Login Again",Toast.LENGTH_SHORT).show();
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