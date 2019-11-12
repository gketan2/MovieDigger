package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.LoginData;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class SignInActivity extends AppCompatActivity {
    RelativeLayout rl;
    TextInputLayout password_wrapper,username_wrapper;
    TextInputEditText signin_username,signin_password;
    Button signin_button;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        rl = findViewById(R.id.signin_content);
        password_wrapper = findViewById(R.id.password_wrapper);
        username_wrapper = findViewById(R.id.username_wrapper);
        signin_username = findViewById(R.id.signin_username);
        signin_password = findViewById(R.id.signin_password);
        signin_button = findViewById(R.id.signin_button);


        /////////Setting Layout
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        int w = dm.widthPixels;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.9),(int)Math.ceil(h*0.75));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        rl.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.81), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0,(int)Math.ceil(h*0.125),0,0);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        username_wrapper.setLayoutParams(layoutParams1);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.81), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0,(int)Math.ceil(h*0.3),0,0);
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        password_wrapper.setLayoutParams(layoutParams2);

//        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(500, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams3.addRule(RelativeLayout.BELOW,R.id.password_wrapper);
//        layoutParams3.setMargins(0,100,0,0);
//        layoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
//
//        signin_button.setLayoutParams(layoutParams3);
        signin_button.setBackgroundResource(R.drawable.curved_button);

        ///// Layout Set<<<--------


    }

    public void toSignUp(View v){
        Intent i = new Intent(this,SignUpActivity.class);
        startActivity(i);
    }

    public void signIn(View v){

        if(signin_username.getText() == null || signin_password.getText() == null){
            Toast.makeText(this, "Please Enter Username and Password", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = signin_username.getText().toString();
        String password = signin_password.getText().toString();

        if(!checkPassword(password) && !checkUsername(username)){
            return;
        }
        Toast.makeText(this,username+" "+password,Toast.LENGTH_SHORT).show();

        final LoginData ld = new LoginData(username,password);

        Retrofit retrofit = getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        Call<LoginData> call = apiInterface.signup(ld);
        call.enqueue(new Callback<LoginData>() {

            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {

            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

                Toast.makeText(getBaseContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }


    public boolean checkUsername(String userString){
        boolean status = true;
        if(userString.length() == 0){
            Toast.makeText(this,"Please Enter Username",Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }

    public boolean checkPassword(String passString){
        boolean status = true;
        if(passString.length() == 0){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }







}
