package com.devsgazar.gazar.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devsgazar.gazar.Adapter.ImageAdapter;
import com.devsgazar.gazar.Model.Items;
import com.devsgazar.gazar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity implements ImageAdapter.CountOfImagesWhenRemoved {

    private Items Item;
    private FirebaseAuth BaseAuth;
    private FirebaseUser BaseUser;
    private FirebaseFirestore BaseStore;
    private DocumentReference DocReference;
    private Intent Into;
    private ActivityResultLauncher<Intent> GetImg;
    private RecyclerView ImgRecycler;
    private ArrayList<Uri> ImgList;
    private ImageAdapter ImgAdapter;
    private TextView ImgsCaption;
    private MaterialButton BtnAddImg;
    private TextInputLayout InputTitle, InputDes, InputPrice;
    private EditText EdtTxtTitle, EdtTxtDes, EdtTxtPrice;
    private String TxtTitle, TxtDes, TxtPrice;
    private Button BtnPrevious, BtnAddItm;
    private ProgressDialog Progress;
    private Context Con = AddItemActivity.this;
    private static final int PermissionDenied = 0;

    public void Init() {
        BaseAuth = FirebaseAuth.getInstance();
        BaseStore = FirebaseFirestore.getInstance();
        ImgRecycler = findViewById(R.id.RecyclerImage);
        ImgsCaption = findViewById(R.id.ImgsCaption);
        ImgList = new ArrayList<>();
        InputTitle = findViewById(R.id.InputTitle);
        InputDes = findViewById(R.id.InputDescription);
        InputPrice = findViewById(R.id.InputPrice);
        EdtTxtTitle = findViewById(R.id.EdtTxtTitle);
        EdtTxtDes = findViewById(R.id.EdtTxtDescription);
        EdtTxtPrice = findViewById(R.id.EdtTxtPrice);
        BtnPrevious = findViewById(R.id.BtnPrevious);
        BtnAddImg = findViewById(R.id.BtnAddImg);
        BtnAddItm = findViewById(R.id.BtnAddItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Init();
        PickImages();
        ClickEvents();
    }

//    public void CurrencyFormat() {
//        EdtTxtPrice.addTextChangedListener(new TextWatcher() {
//
//            private String SetEditText = EdtTxtPrice.getText().toString().trim();
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().equals(SetEditText)) {
//                    EdtTxtPrice.removeTextChangedListener(this);
//                    String Changed = charSequence.toString().replaceAll("[₺]", "");
//                    if (!Changed.isEmpty())
//                        SetEditText = TurkishLiraFormat(Double.parseDouble(Changed));
//                    else
//                        SetEditText = "";
//                    EdtTxtPrice.setText(SetEditText);
//                    EdtTxtPrice.setSelection(SetEditText.length());
//                    EdtTxtPrice.addTextChangedListener(this);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//
//    public String TurkishLiraFormat(Double Number) {
//        Locale LocaleID = new Locale("tr", "TR");
//        NumberFormat NumFormat = NumberFormat.getCurrencyInstance(LocaleID);
//        String FormatTL = NumFormat.format(Number);
//        String[] Split = FormatTL.split(",");
//        int Length = Split[0].length();
//        return Split[0].substring(0, 1) + "" + Split[0].substring(1, Length);
//    }

    public void ClickEvents() {
        BtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(Con, HomeWithAccountActivity.class);
                finish();
                startActivity(Into);
            }
        });

        BtnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Con, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_DENIED) {
                    GetImg();
                } else
                    ActivityCompat.requestPermissions(AddItemActivity.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionDenied);
            }
        });

        BtnAddItm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItm();
            }
        });
    }

    public void GetImg() {
        GetImg.launch(new Intent()
                .setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                .setAction(Intent.ACTION_GET_CONTENT)
        );

        ImgAdapter = new ImageAdapter(ImgList, getApplicationContext(), this);
        ImgRecycler.setLayoutManager(new GridLayoutManager(Con, 4));
        ImgRecycler.setAdapter(ImgAdapter);
    }

    public void PickImages() {
        GetImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        int CountOfImg = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < CountOfImg; i++)
                            if (ImgList.size() < 10) {
                                ImgList.add(result.getData().getClipData().getItemAt(i).getUri());
                            } else
                                Toast.makeText(Con, "En fazla 10 adet resim ekleyebilirsiniz!", Toast.LENGTH_SHORT).show();
                        ImgAdapter.notifyDataSetChanged();
                        ImgsCaption.setText("Fotoğraflar (" + ImgList.size() + ")");
                    } else {
                        if (ImgList.size() < 10) {
                            ImgList.add(result.getData().getData());
                        } else
                            Toast.makeText(Con, "En fazla 10 adet resim ekleyebilirsiniz!", Toast.LENGTH_SHORT).show();
                    }
                    ImgAdapter.notifyDataSetChanged();
                    ImgsCaption.setText("Fotoğraflar (" + ImgList.size() + ")");
                } else
                    Toast.makeText(Con, "Herhangi bir fotoğraf seçilmedi.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionDenied) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                GetImg();
        }
    }

    @Override
    public void clicked(int GetSize) {
        ImgsCaption.setText("Fotoğraflar (" + ImgList.size() + ")");
    }

    public void AddItm() {
        TxtTitle = EdtTxtTitle.getText().toString();
        TxtDes = EdtTxtDes.getText().toString();
        TxtPrice = EdtTxtPrice.getText().toString();

        if (!(ImgList.size() < 1))
            if (!TxtTitle.isEmpty())
                if (!TxtDes.isEmpty())
                    if (!TxtPrice.isEmpty()) {
                        Loading();
                        BaseUser = BaseAuth.getCurrentUser();
                        Item = new Items(BaseUser.getUid(), TxtTitle, TxtDes, TxtPrice, ImgList);
                        Add();

//                        DocReference = BaseStore.collection("Eklenenler").document(BaseUser.getUid())
//                                .collection("Eşya").document("" + ItemID);
//                        DocReference.get()
//                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        if (documentSnapshot.exists()) {
//                                            ItemID++;
//                                            Add();
//                                        } else {
//                                            Add();
//                                        }
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//
//                                    }
//                                });

                    } else
                        InputPrice.setError("Fiyat Bilgisi Boş Bırakılamaz!");
                else
                    InputDes.setError("Açıklama Boş Bırakılamaz!");
            else
                InputTitle.setError("Başlık Boş Bırakılamaz!");
        else
            Toast.makeText(Con, "Eşya Ekleyebilmek İçin En Az 1 Fotoğraf Yüklenmelidir!", Toast.LENGTH_LONG).show();
    }

    public void Add() {
        BaseStore.collection("Eklenenler").document(BaseUser.getUid())
                .collection("Eşya")
                .add(Item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        FinishLoading();
                        Into = new Intent(Con, HomeWithAccountActivity.class);
                        finish();
                        startActivity(Into);
                        Toast.makeText(Con, "Eşyanız Başarıyla Eklendi.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FinishLoading();
                        Toast.makeText(Con, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void Loading() {
        Progress = new ProgressDialog(Con);
        Progress.setTitle("Eşya Ekleniyor...");
        Progress.show();
    }

    public void FinishLoading() {
        if (Progress.isShowing())
            Progress.dismiss();
    }
}