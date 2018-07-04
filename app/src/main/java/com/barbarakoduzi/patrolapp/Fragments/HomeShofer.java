package com.barbarakoduzi.patrolapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barbarakoduzi.patrolapp.Models.Perdorues;
import com.barbarakoduzi.patrolapp.Models.Polic;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeShofer extends Fragment {

    private CardView shkoTekGjobatPaguar, shkoTekGjobatPaPaguar;
    private TextView emer_mbiemer, piketPatente, targa, vleraPara;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference perdorues, shofer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        perdorues = database.getReference(CodesUtil.REFERENCE_PERDORUES);
        shofer = database.getReference(CodesUtil.REFERENCE_SHOFER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shofer_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        shkoTekGjobatPaguar = view.findViewById(R.id.card_gjoba_paguar);
        shkoTekGjobatPaPaguar = view.findViewById(R.id.card_gjoba_jo_paguar);
        emer_mbiemer = view.findViewById(R.id.emer_mbiemer);
        piketPatente = view.findViewById(R.id.piket_patente);
        vleraPara = view.findViewById(R.id.vleraPara);
        targa = view.findViewById(R.id.targa);


        perdorues.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Perdorues perdorues = dataSnapshot.getValue(Perdorues.class);
                emer_mbiemer.setText(perdorues.getEmer()+ "  " + perdorues.getMbiemer());
                shofer.child(perdorues.getIdProfil()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Shofer shofer = dataSnapshot.getValue(Shofer.class);
                        targa.setText(shofer.getTarga());
                        piketPatente.setText(shofer.getPikePatente());
                        vleraPara.setText(shofer.getVleraPara()+ " ALL");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
