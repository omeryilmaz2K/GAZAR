package com.devsgazar.gazar.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devsgazar.gazar.Model.Users;
import com.devsgazar.gazar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private Users User;
    private FirebaseAuth BaseAuth;
    private FirebaseUser BaseUser;
    private FirebaseFirestore BaseStore;
    private TextInputLayout InputEmail, InputPass;
    private EditText EdtTxtEmail, EdtTxtPass;
    private String TxtEmail, TxtPass;
    private Button BtnPrevious, BtnSign, BtnHaveAcc, BtnHelp;
    private ProgressDialog Progress;
    private Intent Into;
    private Context Con = SignUpActivity.this;

    public void Init() {
        BaseAuth = FirebaseAuth.getInstance();
        BaseStore = FirebaseFirestore.getInstance();
        InputEmail = findViewById(R.id.InputEmail);
        InputPass = findViewById(R.id.InputPass);
        EdtTxtEmail = findViewById(R.id.EdtTxtEmail);
        EdtTxtPass = findViewById(R.id.EdtTxtPass);
        BtnPrevious = findViewById(R.id.BtnPrevious);
        BtnSign = findViewById(R.id.BtnSignUp);
        BtnHaveAcc = findViewById(R.id.BtnHaveAcc);
        BtnHelp = findViewById(R.id.BtnHelp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Init();
        SignUp();
        ClickEvents();
    }

    public void SignUp() {
        BtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxtEmail = EdtTxtEmail.getText().toString();
                TxtPass = EdtTxtPass.getText().toString();

                if (!TxtEmail.isEmpty())
                    if (!TxtPass.isEmpty()) {
                        Loading();
                        BaseAuth.createUserWithEmailAndPassword(TxtEmail, TxtPass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        BaseUser = BaseAuth.getCurrentUser();
                                        User = new Users(BaseUser.getUid(), "default", TxtEmail);
                                        BaseStore.collection("Kullanıcılar").document(BaseUser.getUid())
                                                .set(User)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        FinishLoading();
                                                        Into = new Intent(Con, HomeWithAccountActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        finish();
                                                        startActivity(Into);
                                                        Toast.makeText(Con, "Kayıt İşlemi Başarılı. Hoşgeldiniz.", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        FinishLoading();
                                                        Toast.makeText(Con, "Kayıt İşlemi Gerçekleştirilemedi!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        FinishLoading();
                                        Toast.makeText(Con, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        Progress.setTitle("Kayıt İşlemi Gerçekleştiriliyor...");
        Progress.show();
    }

    public void FinishLoading() {
        if (Progress.isShowing())
            Progress.dismiss();
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

        BtnHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, SignInActivity.class);
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