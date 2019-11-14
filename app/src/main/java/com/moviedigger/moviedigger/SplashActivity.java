package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.moviedigger.moviedigger.retrofit.ApiInterface;
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
    }

    private void verify_token(){
        SharedPreferences sp = getSharedPreferences("authDetails", Context.MODE_PRIVATE);
        String username = sp.getString("username",null);
        String token = sp.getString("token",null);

        if(username == null || token == null){
            delay();
            Intent i = new Intent(this,SignInActivity.class);
            startActivity(i);
            finish();
        }
        else{
            tokenAuth(username,token);
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
                int responseCode = response.body().getResponsecode();
                String responseMessage = response.body().getResponseMessage();
                if(responseCode == 1){
                    Intent i = new Intent(mContext,RecommendedActivity.class);
                    startActivity(i);
                    finish();
                }else if(responseCode == 0){
                    Toast.makeText(mContext,responseMessage,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(mContext,SignInActivity.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<TokenAuth> call, Throwable t) {
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

    private void delay(){}

}
