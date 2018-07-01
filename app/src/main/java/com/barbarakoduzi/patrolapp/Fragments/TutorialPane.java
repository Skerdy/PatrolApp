package com.barbarakoduzi.patrolapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.barbarakoduzi.patrolapp.Activities.TutorialActivity;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class TutorialPane extends Fragment{

    final static String LAYOUT_ID = "layoutid";
    final static String INDEX = "index";
    private int index;
    private EditText input_emer, input_mbiemer;
    private NiceSpinner titullSpinner, gradaSpinner;
    private FirebaseDatabase database;
    private DatabaseReference titullRef, gradaRef;
    private List<String> titujt;
    private List<String> gradat;

    public static TutorialPane newInstance(int layoutId, int tutorialIndex ) {
        TutorialPane pane = new TutorialPane();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        args.putInt(INDEX, tutorialIndex);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        index = getArguments().getInt(INDEX);
        switch (index){
            case 0:
                //eshte mireseardhje mos bej asgje
                break;
            case 1:
                loginLogicForEmerMbiemer(view);
                break;
            case 2:
                //fut titull dhe grade
                loginLogicForTitullGrade(view);
                break;
        }
    }

    private void loginLogicForTitullGrade(View view){
        titujt = new ArrayList<>();
        gradat = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        titullSpinner = view.findViewById(R.id.spinner_titull);
        gradaSpinner = view.findViewById(R.id.spinner_grada);

        titullRef = database.getReference(CodesUtil.REFERENCE_TITULL);
        gradaRef = database.getReference(CodesUtil.REFERENCE_GRADA);

        titullRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titujt.clear();
                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    titujt.add(imageSnapshot.getValue(String.class));
                }
                titullSpinner.attachDataSource(titujt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        gradaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gradat.clear();
                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    gradat.add(imageSnapshot.getValue(String.class));
                }
                gradaSpinner.attachDataSource(gradat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        titullSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((TutorialActivity)getActivity()).setTitulli(titullSpinner.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        gradaSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((TutorialActivity)getActivity()).setGrada(gradaSpinner.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void loginLogicForEmerMbiemer(View view){
        input_emer = view.findViewById(R.id.input_emer);
        input_mbiemer = view.findViewById(R.id.input_mbiemer);
        input_emer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ((TutorialActivity)getActivity()).setEmer(input_emer.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_mbiemer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ((TutorialActivity)getActivity()).setMbiemer(input_mbiemer.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

