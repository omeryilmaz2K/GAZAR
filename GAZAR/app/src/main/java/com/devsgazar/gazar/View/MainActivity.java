package com.devsgazar.gazar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.devsgazar.gazar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    
    private FirebaseAuth BaseAuth;
    private FirebaseUser BaseUser;
    private Handler Handle;
    private Intent Into;
    private Context Con = MainActivity.this;

    public void Init() {
        BaseAuth = FirebaseAuth.getInstance();
        Handle = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        FullScreen();
        SplashEvent();
    }

    public void FullScreen() {
        View DecorView = getWindow().getDecorView();
        DecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    public void SplashEvent() {
        if (AccessControl()) {
            Handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Into = new Intent(Con, HomeWithAccountActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(Into);
                }
            }, 3000);
        } else {
            Handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Into = new Intent(Con, Onboarding1Activity.class);
                    finish();
                    startActivity(Into);
                }
            }, 3000);
        }
    }

    public boolean AccessControl() {
        BaseUser = BaseAuth.getCurrentUser();
        if (BaseUser != null) {
            return true;
        }
        return false;
    }
}