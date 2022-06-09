package com.devsgazar.gazar.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devsgazar.gazar.Adapter.ItemAdapter;
import com.devsgazar.gazar.Model.Items;
import com.devsgazar.gazar.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View V;
    private FirebaseUser BaseUser;
    private FirebaseFirestore BaseStore;
    private Query UsersQuery, ItemsQuery;
    private RecyclerView ItmRecycler;
    private ItemAdapter ItmAdapter;
    private ArrayList<Items> ItmList;
    private Items Itm;
    private MaterialButton BtnAdd;
    private Intent Into;

    public void Init() {
        BaseUser = FirebaseAuth.getInstance().getCurrentUser();
        BaseStore = FirebaseFirestore.getInstance();
        ItmList = new ArrayList<>();
        BtnAdd = V.findViewById(R.id.BtnAddItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_home, container, false);

        ItmRecycler = V.findViewById(R.id.RecyclerNewAdded);
        ItmRecycler.setHasFixedSize(true);
        ItmRecycler.setLayoutManager(new GridLayoutManager(V.getContext(), 2, LinearLayoutManager.VERTICAL, false));

        Init();
        ClickEvents();

        return V;
    }

    public void ClickEvents() {
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Into = new Intent(V.getContext(), AddItemActivity.class);
                startActivity(Into);
            }
        });
    }

    public void ShowNewAdded() {
        UsersQuery = BaseStore.collection("Kullanıcılar");
        UsersQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot User : queryDocumentSnapshots.getDocuments()) {
                    ItemsQuery = BaseStore.collection("Eklenenler")
                            .document(String.valueOf(User.get("userID"))).collection("Eşya");
                    ItemsQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ItmList.clear();
                            for (DocumentSnapshot Item : queryDocumentSnapshots.getDocuments()) {
                                Itm = Item.toObject(Items.class);
                                assert Itm != null;
                                if (!Itm.getItemID().equals(BaseUser.getUid()))
                                    ItmList.add(Itm);
                            }
                            ItmAdapter = new ItemAdapter(ItmList, V.getContext());
                            ItmRecycler.setAdapter(ItmAdapter);
                        }
                    });
                }
            }
        });
    }


}