package com.devsgazar.gazar.View;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devsgazar.gazar.R;
import com.google.android.material.button.MaterialButton;

public class HomeWithoutAccountFragment extends Fragment {

    private View V;
    private MaterialButton BtnAddPsv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_home_without_account, container, false);

        Init();
        ClickEvents();
        return V;
    }

    private void Init() {
        BtnAddPsv = V.findViewById(R.id.BtnAddItemPsv);
    }

    private void ClickEvents() {
        BtnAddPsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(V.getContext(), "Eşya Ekleyebilmek İçin Öncelikle Giriş Yapmalısınız!", Toast.LENGTH_LONG).show();
            }
        });
    }
}