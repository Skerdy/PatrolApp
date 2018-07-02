package com.barbarakoduzi.patrolapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.barbarakoduzi.patrolapp.Activities.TutorialActivityShofer;
import com.barbarakoduzi.patrolapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TutorialPaneShofer extends Fragment {

    final static String LAYOUT_ID = "layoutid";
    final static String INDEX = "index";
    private int index;
    private EditText input_emer, input_mbiemer, input_targa;
    private FirebaseDatabase database;



    public static TutorialPaneShofer newInstance(int layoutId, int tutorialIndex ) {
        TutorialPaneShofer pane = new TutorialPaneShofer();
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
        database =FirebaseDatabase.getInstance();
        index = getArguments().getInt(INDEX);
        switch (index){
            case 0:
                //eshte mireseardhje mos bej asgje
                break;
            case 1:
                loginLogicForEmerMbiemer(view);
                break;
            case 2:
                //fut targen e makines
                loginLogicForTarga(view);
                break;
        }
    }

    private void loginLogicForTarga(View view) {
        input_targa = view.findViewById(R.id.targa);
        input_targa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((TutorialActivityShofer)getActivity()).setTarga(input_targa.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

                ((TutorialActivityShofer)getActivity()).setEmer(input_emer.getText().toString());
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

                ((TutorialActivityShofer)getActivity()).setMbiemer(input_mbiemer.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}