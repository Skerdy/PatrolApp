package com.barbarakoduzi.patrolapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barbarakoduzi.patrolapp.Adapters.GjobatAdapter;
import com.barbarakoduzi.patrolapp.Adapters.GjobatShoferAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static android.view.View.VISIBLE;

public class GjobatShoferFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference gjobatRef, shoferRef, perdoruesRef;
    private PerdoruesShofer perdoruesShofer;

    private List<Gjobe> gjobat;
    private RecyclerView recyclerView;
    private GjobatShoferAdapter gjobatShoferAdapter;

    private Map<String,Gjobe> gjobeMap;

    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private boolean paguar;

    private static String ARG_PAGUAR = "paguar";


    public static GjobatShoferFragment newInstance(boolean paguar){
        GjobatShoferFragment gjobatShoferFragment = new GjobatShoferFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PAGUAR,paguar);
        gjobatShoferFragment.setArguments(args);
        return gjobatShoferFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gjobat = new ArrayList<>();
        gjobeMap = new HashMap<>();
        this.paguar = getArguments().getBoolean(ARG_PAGUAR,true);
        gjobatShoferAdapter = new GjobatShoferAdapter(getActivity(), new ArrayList<Gjobe>(), new HashMap<String, Gjobe>());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        gjobatRef = database.getReference(CodesUtil.REFERENCE_GJOBAT);
        shoferRef = database.getReference(CodesUtil.REFERENCE_SHOFER);
        perdoruesRef = database.getReference(CodesUtil.REFERENCE_PERDORUES).child(auth.getCurrentUser().getUid());


        perdoruesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String emer = dataSnapshot.child("emer").getValue().toString();
                final String mbiemer = dataSnapshot.child("mbiemer").getValue().toString();
                final Integer rol = Integer.parseInt(dataSnapshot.child("rol").getValue().toString());
                final String profileId = dataSnapshot.child("idProfil").getValue().toString();
                final String email = dataSnapshot.child("email").getValue().toString();
                shoferRef = database.getReference(CodesUtil.REFERENCE_SHOFER).child(profileId);
                //gjejme gjobat
                gjobatRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        gjobat.clear();
                        gjobeMap.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Gjobe gjobe = snapshot.getValue(Gjobe.class);
                            if(gjobe.getIdShofer().equals(profileId)) {
                                if(paguar){
                                    if(gjobe.isPaguar()){
                                        gjobeMap.put(snapshot.getKey(), gjobe);
                                        gjobat.add(gjobe);
                                    }
                                }
                                else{
                                    if(!gjobe.isPaguar()){
                                        gjobeMap.put(snapshot.getKey(), gjobe);
                                        gjobat.add(gjobe);
                                    }

                                }

                            }
                        }
                        //ketu bene dhe notify ne setGjobat bashke me mapin me gjobat dhe cdo key per cdop gjobe
                        gjobatShoferAdapter.setGjobat(gjobat,gjobeMap);
                        gjobatShoferAdapter.setShoferID(profileId);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //plotesojme profilin e policit
                shoferRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Shofer shofer = dataSnapshot.getValue(Shofer.class);
                        perdoruesShofer = new PerdoruesShofer(emer,mbiemer,rol,profileId,email,shofer);
                        gjobatShoferAdapter.setShofer(shofer);
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
        return inflater.inflate(R.layout.fragment_shofer_gjobat,container,false );

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(gjobatShoferAdapter);mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.primary));
        int[] colors = new int[]{R.color.white, R.color.white};
        mWaveSwipeRefreshLayout.setColorSchemeResources(colors);


        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                new Task().execute();
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });


    }

    private class Task extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(VISIBLE);
                }
            }, 1500);

            super.onPostExecute(result);
        }
    }
}
