package com.devsgazar.gazar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devsgazar.gazar.R;

public class Onboarding2Activity extends AppCompatActivity {

    private Button BtnPrevious, BtnStart;
    private Intent Into;
    private Context Con = Onboarding2Activity.this;

    public void Init() {
        BtnPrevious = findViewById(R.id.BtnPrevious);
        BtnStart = findViewById(R.id.BtnStart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);

        Init();
        ClickEvents();
    }

    public void ClickEvents() {
        BtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, Onboarding1Activity.class);
                finish();
                startActivity(Into);
            }
        });

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, LoginActivity.class);
                finish();
                startActivity(Into);
            }
        });
    }
}