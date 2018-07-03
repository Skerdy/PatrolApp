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

import com.barbarakoduzi.patrolapp.Activities.PolicActivity;
import com.barbarakoduzi.patrolapp.Models.Perdorues;
import com.barbarakoduzi.patrolapp.Models.Polic;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class HomePolic extends Fragment {

    private CardView shkoTekShofer, shkoTekGjobat;
    private TextView emer_mbiemer, titull, grada;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference perdorues, polic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        perdorues = database.getReference(CodesUtil.REFERENCE_PERDORUES);
        polic = database.getReference(CodesUtil.REFERENCE_POLIC);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_polic_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shkoTekShofer = view.findViewById(R.id.card_shofer);
        shkoTekGjobat = view.findViewById(R.id.card_gjoba);
        emer_mbiemer = view.findViewById(R.id.emer_mbiemer);
        titull = view.findViewById(R.id.titulli);
        grada = view.findViewById(R.id.grada);
        shkoTekGjobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PolicActivity)getActivity()).shikoGjobatEVena();
            }
        });

        shkoTekShofer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PolicActivity)getActivity()).initGjejShoferFragment();
            }
        });

        perdorues.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Perdorues perdorues = dataSnapshot.getValue(Perdorues.class);
                emer_mbiemer.setText(perdorues.getEmer()+ "  " + perdorues.getMbiemer());
                polic.child(perdorues.getIdProfil()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Polic polic = dataSnapshot.getValue(Polic.class);
                        titull.setText(polic.getTitulli());
                        grada.setText(polic.getGrada());
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
