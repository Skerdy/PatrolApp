package com.barbarakoduzi.patrolapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barbarakoduzi.patrolapp.Adapters.GjobatAdapter;
import com.barbarakoduzi.patrolapp.Models.Gjobe;
import com.barbarakoduzi.patrolapp.Models.PerdoruesPolic;
import com.barbarakoduzi.patrolapp.Models.PerdoruesShofer;
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

import java.util.ArrayList;
import java.util.List;


public class GjobatFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference gjobatRef, policRef, perdoruesRef;
    private PerdoruesPolic perdoruesPolic;
    private List<Gjobe> gjobat;
    private RecyclerView recyclerView;
    private GjobatAdapter gjobatAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gjobat = new ArrayList<>();
        gjobatAdapter = new GjobatAdapter(getActivity(), new ArrayList<Gjobe>());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        gjobatRef = database.getReference(CodesUtil.REFERENCE_GJOBAT);
        policRef = database.getReference(CodesUtil.REFERENCE_POLIC);
        perdoruesRef = database.getReference(CodesUtil.REFERENCE_PERDORUES).child(auth.getCurrentUser().getUid());


        perdoruesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String emer = dataSnapshot.child("emer").getValue().toString();
                final String mbiemer = dataSnapshot.child("mbiemer").getValue().toString();
                final Integer rol = Integer.parseInt(dataSnapshot.child("rol").getValue().toString());
                final String profileId = dataSnapshot.child("idProfil").getValue().toString();
                final String email = dataSnapshot.child("email").getValue().toString();

                policRef = database.getReference(CodesUtil.REFERENCE_POLIC).child(profileId);

                //gjejme gjobat

                gjobatRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        gjobat.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Gjobe gjobe = snapshot.getValue(Gjobe.class);
                            if(gjobe.getIdPolic().equals(profileId))
                            gjobat.add(gjobe);
                        }
                        //ketu bene dhe notify ne setGjobat
                        gjobatAdapter.setGjobat(gjobat);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //plotesojme profilin e policit
                policRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String grada = dataSnapshot.child("grada").toString();
                        String titulli = dataSnapshot.child("titulli").toString();
                        Polic polic = new Polic(titulli, grada);
                        perdoruesPolic = new PerdoruesPolic(emer,mbiemer,rol,profileId,email,polic);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_polic_gjobat,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(gjobatAdapter);
    }
}
