package com.barbarakoduzi.patrolapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.barbarakoduzi.patrolapp.R;
import com.github.florent37.viewanimator.ViewAnimator;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo;
    private TextView text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.app_logo);
        text = findViewById(R.id.patrol_app_text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                ViewAnimator
                        .animate(logo)
                        .translationY(-1000, 0)
                        .alpha(0, 1)
                        .andAnimate(text)
                        .dp().translationX(-20, 0)
                        .decelerate()
                        .duration(2000)
                        .thenAnimate(logo)
                        .scale(1f, 0.5f, 1f)
                        .accelerate()
                        .duration(1000)
                        .start();
            }
        }, 1000);


        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, 3000);
    }
}
