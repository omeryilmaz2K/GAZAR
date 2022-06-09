package com.devsgazar.gazar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devsgazar.gazar.R;

public class LoginActivity extends AppCompatActivity {

    private Button BtnSignUp, BtnSignIn, BtnWoutAcc ,BtnHelp;
    private Intent Into;
    private Context Con = LoginActivity.this;

    public void Init() {
        BtnSignUp = findViewById(R.id.BtnSignUpEmail);
        BtnSignIn = findViewById(R.id.BtnSignInEmail);
        BtnWoutAcc = findViewById(R.id.BtnWithoutAccount);
        BtnHelp = findViewById(R.id.BtnHelp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();
        ClickEvents();
    }

    public void ClickEvents() {
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, SignUpActivity.class);
                startActivity(Into);
            }
        });

        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, SignInActivity.class);
                startActivity(Into);
            }
        });

        BtnWoutAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, HomeWithAccountActivity.class);
                finish();
                startActivity(Into);
            }
        });

        BtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, Onboarding1Activity.class);
                finish();
                startActivity(Into);
            }
        });
    }
}