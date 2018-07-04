package com.barbarakoduzi.patrolapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barbarakoduzi.patrolapp.Adapters.GjobatAdapter;
import com.barbarakoduzi.patrolapp.Adapters.ShoferAdapter;
import com.barbarakoduzi.patrolapp.Models.Gjobe;
import com.barbarakoduzi.patrolapp.Models.Perdorues;
import com.barbarakoduzi.patrolapp.Models.PerdoruesPolic;
import com.barbarakoduzi.patrolapp.Models.PerdoruesShofer;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.barbarakoduzi.patrolapp.Utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaShoferFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference shoferRef, perdoruesRef;
    private PerdoruesShofer perdoruesShofer;
    private List<Shofer> shoferet;
    private RecyclerView recyclerView;
    private ShoferAdapter shoferAdapter;
    private Map<String, Shofer> shoferDheKey;
    private List<Perdorues> perdoruesShoferList;

    private String idPolic;

    private static final String ARG_PROFIL_ID ="asdfafdsafd";

    public static ListaShoferFragment newInstance(String idProfil) {
        ListaShoferFragment listaShoferFragment = new ListaShoferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROFIL_ID, idProfil);
        listaShoferFragment.setArguments(args);
        return listaShoferFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth =FirebaseAuth.getInstance();
        idPolic = getArguments().getString(ARG_PROFIL_ID);
        perdoruesShoferList = new ArrayList<>();
        shoferet = new ArrayList<>();
        shoferDheKey = new HashMap<>();
        shoferAdapter = new ShoferAdapter(getActivity(),new ArrayList<PerdoruesShofer>(), idPolic);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        shoferRef = database.getReference(CodesUtil.REFERENCE_SHOFER);

        perdoruesRef = database.getReference(CodesUtil.REFERENCE_PERDORUES);

        //marrim te gjithe shoferet

        shoferRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shoferet.clear();
                shoferDheKey.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shofer shofer = snapshot.getValue(Shofer.class);

                        //per cdo objekt shofer mbushim edhe nje objekt perdorues

                        shoferDheKey.put(snapshot.getKey(), shofer);
                        shoferet.add(shofer);
                }
                //ketu bene dhe notify ne

                shoferAdapter.setShoferMap(shoferDheKey);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        perdoruesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                perdoruesShoferList.clear();

                for(DataSnapshot perdorues : dataSnapshot.getChildren()){
                    if(perdorues.child("idProfil").getValue()!=null ){
                        //do te thote qe jemi tek personi me idProfil te njejte pra ndertojme nje perdorues te ri;
                        Log.d("idProfil" ,  perdorues.child("idProfil").getValue().toString());
                        final String emer = perdorues.child("emer").getValue().toString();
                        final String mbiemer = perdorues.child("mbiemer").getValue().toString();
                        final Integer rol = Integer.parseInt(perdorues.child("rol").getValue().toString());
                        final String profileId = perdorues.child("idProfil").getValue().toString();
                        final String email = perdorues.child("email").getValue().toString();
                        Perdorues perdorues1 = new Perdorues(emer,mbiemer,rol,profileId,email);
                        perdoruesShoferList.add(perdorues1);
                    }
                }

                shoferAdapter.setShoferList(FirebaseUtils.ktheShoferetNgaListaPerdoruesDheShofer(perdoruesShoferList,shoferDheKey));




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_shofer, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(shoferAdapter);
    }


}
