package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.annotations.SerializedName;
import com.moviedigger.moviedigger.retrofit.ApiInterface;
import com.moviedigger.moviedigger.retrofit.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moviedigger.moviedigger.retrofit.ApiInterface.BASE_URL;

public class SignUpActivity extends AppCompatActivity {
    RelativeLayout rl;
    TextInputLayout password_wrapper,username_wrapper,cnf_password_wrapper;
    TextInputEditText signup_username,signup_password,signup_cnf_password;
    Button signup_button;
    ImageView signup_icon_image;
    LoadingDots signup_loadingdots;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rl = findViewById(R.id.signup_content);
        password_wrapper = findViewById(R.id.signup_password_wrapper);
        username_wrapper = findViewById(R.id.signup_username_wrapper);
        cnf_password_wrapper = findViewById(R.id.signup_cnf_password_wrapper);

        signup_button = findViewById(R.id.signup_button);
        signup_username = findViewById(R.id.signup_username);
        signup_password = findViewById(R.id.signup_password);
        signup_cnf_password = findViewById(R.id.signup_cnf_password);

        signup_icon_image = findViewById(R.id.signup_icon_image);

        signup_loadingdots = findViewById(R.id.signup_loadingdots);

        signup_loadingdots.setVisibility(View.INVISIBLE);


        DisplayMetrics dm = getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        int w = dm.widthPixels;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.9),(int)Math.ceil(h*0.75));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        rl.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.81), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0,(int)Math.ceil(h*0.75/7),0,0);
        layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        username_wrapper.setLayoutParams(layoutParams1);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.81), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0,(int)Math.ceil(h*1.5/7),0,0);
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        password_wrapper.setLayoutParams(layoutParams2);

        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((int)Math.ceil(w*0.81), RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams3.setMargins(0,(int)Math.ceil(h*2.25/7),0,0);
        layoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        cnf_password_wrapper.setLayoutParams(layoutParams3);

        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,(int)Math.ceil(h*0.2));
        layoutParams4.setMargins(0,(int)Math.ceil(h*0.025),0,(int)Math.ceil(h*0.025));
        layoutParams4.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        signup_icon_image.setLayoutParams(layoutParams4);

    }

    public void signUp(View v){
        final Context mContext = this;
        signup_loadingdots.setVisibility(View.VISIBLE);

        if(signup_username.getText() == null || signup_password.getText() == null || signup_cnf_password.getText() == null){
            Toast.makeText(this, "Please Enter Username and Password", Toast.LENGTH_SHORT).show();
            signup_loadingdots.setVisibility(View.INVISIBLE);
            return;
        }

        String username = signup_username.getText().toString();
        String password = signup_password.getText().toString();
        String cnf_password = signup_cnf_password.getText().toString();

        if(!checkPassword(password,cnf_password) || !checkUsername(username)){
            signup_loadingdots.setVisibility(View.INVISIBLE);
            return;
        }
        //Toast.makeText(this,username+" "+password,Toast.LENGTH_SHORT).show();

        final LoginData ld = new LoginData(username,password);

        Retrofit retrofit = getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        Call<LoginData> call = apiInterface.signup(ld);
        call.enqueue(new Callback<LoginData>() {

            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if(response.body() != null){
                    signup_loadingdots.setVisibility(View.INVISIBLE);
                    int responseCode = response.body().getresponsecode();
                    //String responseMessage = response.body().getresponsemessage();
                    //String token = response.body().getToken();
                    String user = response.body().getUsername();

                    if(responseCode == Constants.USER_VERIFIED){

                        SharedPreferences sp = getSharedPreferences("authDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        //editor.putString("token", token);
                        editor.putString("username", user);
                        editor.apply();
                        Intent i = new Intent(mContext, RateMovies.class);
                        startActivity(i);
                        finish();


                    }else if(responseCode  == Constants.USER_EXIST){
                        Toast.makeText(mContext, "User Already Exist!", Toast.LENGTH_SHORT).show();
                        signup_password.setText("");
                        signup_cnf_password.setText("");
                        signup_username.setText("");
                    }
                }else{
                    Toast.makeText(mContext,"Something went Wrong...",Toast.LENGTH_SHORT).show();
                }
                signup_loadingdots.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                signup_loadingdots.setVisibility(View.INVISIBLE);
            }
        });
    }

    public Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public boolean checkUsername(String userString){
        boolean status = true;
        if(userString == null || userString.length() == 0){
            Toast.makeText(this,"Please Enter Username",Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }
    public boolean checkPassword(String passString, String cnfpassString){
        boolean status = true;

        if(passString == null || passString.length() == 0){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            status = false;
        }else if(cnfpassString == null || cnfpassString.length() == 0){
            Toast.makeText(this,"Please Confirm Password",Toast.LENGTH_SHORT).show();
            status = false;
        }else if(!passString.equals(cnfpassString)){
            Toast.makeText(this,"Enter Same Passwords",Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }

}
