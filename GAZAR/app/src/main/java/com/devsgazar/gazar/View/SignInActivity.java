package com.devsgazar.gazar.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devsgazar.gazar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth BaseAuth;
    private TextInputLayout InputEmail, InputPass;
    private EditText EdtTxtEmail, EdtTxtPass;
    private String TxtEmail, TxtPass;
    private Button BtnPrevious, BtnSign, BtnDontAcc, BtnHelp;
    private ProgressDialog Progress;
    private Intent Into;
    private Context Con = SignInActivity.this;

    public void Init() {
        BaseAuth = FirebaseAuth.getInstance();
        InputEmail = findViewById(R.id.InputEmail);
        InputPass = findViewById(R.id.InputPass);
        EdtTxtEmail = findViewById(R.id.EdtTxtEmail);
        EdtTxtPass = findViewById(R.id.EdtTxtPass);
        BtnPrevious = findViewById(R.id.BtnPrevious);
        BtnSign = findViewById(R.id.BtnSignIn);
        BtnDontAcc = findViewById(R.id.BtnDontHaveAcc);
        BtnHelp = findViewById(R.id.BtnHelp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Init();
        SignIn();
        ClickEvents();
    }

    private void SignIn() {
        BtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxtEmail = EdtTxtEmail.getText().toString();
                TxtPass = EdtTxtPass.getText().toString();

                if (!TxtEmail.isEmpty())
                    if (!TxtPass.isEmpty()) {
                        BaseAuth.signInWithEmailAndPassword(TxtEmail, TxtPass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Loading();
                                        Into = new Intent(Con, HomeWithAccountActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        finish();
                                        startActivity(Into);
                                        Toast.makeText(Con, "Giriş İşlemi Başarılı. Hoşgeldiniz.", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Con, "Giriş İşlemi Başarısız!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else
                        InputPass.setError("Şifre Alanı Boş Bırakılamaz!");
                else
                    InputEmail.setError("Eposta Alanı Boş Bırakılamaz!");

            }
        });
    }

    public void Loading() {
        Progress = new ProgressDialog(Con);
        Progress.setTitle("Giriş Yapılıyor...");
        Progress.show();
    }

    public void ClickEvents() {
        BtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, LoginActivity.class);
                finish();
                startActivity(Into);
            }
        });

        BtnDontAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, SignUpActivity.class);
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