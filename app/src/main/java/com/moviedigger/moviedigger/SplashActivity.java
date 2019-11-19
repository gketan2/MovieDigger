package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.ProfileStatus;
import com.moviedigger.moviedigger.retrofit.TokenAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class SplashActivity extends AppCompatActivity {
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        verify_token();

//        Intent i = new Intent(this,RecommendedActivity.class);
//        startActivity(i);
//        finish();
    }

    private void verify_token(){
        SharedPreferences sp = getSharedPreferences("authDetails", Context.MODE_PRIVATE);
        String username = sp.getString("username",null);
        //String token = sp.getString("token",null);

        if(username == null/* || token == null*/){
            delay();
            Intent i = new Intent(this,SignInActivity.class);
            startActivity(i);
            finish();
        }
        else{
//            delay();
//            Intent i = new Intent(this,RecommendedActivity.class);
//            startActivity(i);
//            finish();
            //tokenAuth(username,token);
            profileStatus(username);
        }
    }


    private void tokenAuth(String username, String token){

        final TokenAuth ta = new TokenAuth(username,token);
        final Context mContext = this;

        Retrofit retrofit = getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        Call<TokenAuth> call = apiInterface.tokenAuth(ta);
        call.enqueue(new Callback<TokenAuth>() {
            @Override
            public void onResponse(Call<TokenAuth> call, Response<TokenAuth> response) {
                if(response.body() != null){

                    int responseCode = response.body().getResponsecode();
                    String responseMessage = response.body().getResponseMessage();
                    int profileStatus = response.body().getProfileStatus();

                    if(responseCode == Constants.TOKEN_VERIFIED){
                        if(profileStatus == Constants.PROFILE_COMPLETE){
                            Intent i = new Intent(mContext,RecommendedActivity.class);
                            startActivity(i);
                            finish();
                        }else if(profileStatus == Constants.PROFILE_RATE_MOVIES){
                            Intent i = new Intent(mContext,RateMovies.class);
                            startActivity(i);
                            finish();
                        }else if(profileStatus == Constants.PROFILE_NULL){
                            Intent i = new Intent(mContext,SetGenres.class);
                            startActivity(i);
                            finish();
                        }
                    }else if(responseCode == Constants.TOKEN_NOT_VERIFIED){
                        Toast.makeText(mContext,responseMessage,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(mContext,SignInActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<TokenAuth> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void profileStatus(String username){

        final ProfileStatus ps = new ProfileStatus(username);
        final Context mContext = this;

        Retrofit retrofit = getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        Call<ProfileStatus> call = apiInterface.profileSatus(ps);
        call.enqueue(new Callback<ProfileStatus>() {
            @Override
            public void onResponse(Call<ProfileStatus> call, Response<ProfileStatus> response) {
                if(response.body() != null){

                    //int responseCode = response.body().getResponsecode();
                    //String responseMessage = response.body().getResponsemessage();
                    int profileStatus = response.body().getProfilestatus();

                    if(profileStatus == Constants.PROFILE_COMPLETE){
                        Intent i = new Intent(mContext,RecommendedActivity.class);
                        startActivity(i);
                        finish();
                    }else if(profileStatus == Constants.PROFILE_RATE_MOVIES){
                        Intent i = new Intent(mContext,RateMovies.class);
                        startActivity(i);
                        finish();
                    }else if(profileStatus == Constants.PROFILE_NULL){
                        Intent i = new Intent(mContext,SetGenres.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
            @Override
            public void onFailure(Call<ProfileStatus> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void delay(){
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            //handle
//        }
    }

}
