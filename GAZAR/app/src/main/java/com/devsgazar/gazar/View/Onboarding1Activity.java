package com.devsgazar.gazar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devsgazar.gazar.R;

public class Onboarding1Activity extends AppCompatActivity {

    private Button BtnNext, BtnSkip;
    private Intent Into;
    private Context Con = Onboarding1Activity.this;

    public void Init() {
        BtnNext = findViewById(R.id.BtnNext);
        BtnSkip = findViewById(R.id.BtnSkip);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding1);

        Init();
        ClickEvents();
    }

    public void ClickEvents() {
        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, Onboarding2Activity.class);
                startActivity(Into);
            }
        });

        BtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, LoginActivity.class);
                finish();
                startActivity(Into);
            }
        });
    }


}