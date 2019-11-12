package com.moviedigger.moviedigger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    RelativeLayout rl;
    TextInputLayout password_wrapper,username_wrapper,cnf_password_wrapper;
    Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rl = findViewById(R.id.signup_content);
        password_wrapper = findViewById(R.id.signup_password_wrapper);
        username_wrapper = findViewById(R.id.signup_username_wrapper);
        cnf_password_wrapper = findViewById(R.id.signup_cnf_password_wrapper);
        signup_button = findViewById(R.id.signup_button);

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


    }

}
