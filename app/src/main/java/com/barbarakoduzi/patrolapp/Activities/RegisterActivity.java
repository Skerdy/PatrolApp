package com.barbarakoduzi.patrolapp.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barbarakoduzi.patrolapp.Models.Perdorues;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.barbarakoduzi.patrolapp.Utils.MySharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private NiceSpinner niceSpinner;
    private EditText emailText, passwordText, reEnterPasswordText;
    private Button signupButton;
    private TextView loginLink;
    private String TAG = "Register";
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private List<String> rolet;
    private DatabaseReference roletFirebase;
    private DatabaseReference perdoruesFirebase;
    private FirebaseUser user;
    private MySharedPref mySharedPref;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mySharedPref = new MySharedPref(this);
        rolet = new ArrayList<>();
        setupFirebaseReferences();
        setupViews();



       signupButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signup();
        }
    });

        loginLink.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(getApplicationContext(),Login.class);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            startActivity(intent);
            finish();
        }
    });
   }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void setupFirebaseReferences(){
        database =FirebaseDatabase.getInstance();
        roletFirebase = database.getReference(CodesUtil.REFERENCE_ROLET);
    }

    private void setupViews(){
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        signupButton = findViewById(R.id.btn_signup);
        loginLink = findViewById(R.id.link_login);

        roletFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rolet.clear();
                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    rolet.add(imageSnapshot.getValue(String.class));
                }
                niceSpinner.attachDataSource(rolet);
                Log.i("MyTag", rolet.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Ju po rregjistroheni!");
        progressDialog.show();

        final String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                             user = mAuth.getCurrentUser();
                             onSignupSuccess(email);
                        } else {
                            // If sign in fails, display a message to the user.
                             Log.w(TAG, "createUserWithEmail:failure", task.getException());
                             Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    public void onSignupSuccess(String email) {
        signupButton.setEnabled(true);
        mySharedPref.saveStringInSharedPref(CodesUtil.PERDORUES_EMAIL, email);
        //marrim te gjtiha te dhenat e mundshme dhe i ruajme ne firebase ne referencen " perdorues"
        Toast.makeText(getBaseContext(), "Rregjistrimi u krye me sukses!", Toast.LENGTH_LONG).show();
        Perdorues perdorues = new Perdorues(getRoleIdFromRole(niceSpinner.getText().toString()), email);
        perdoruesFirebase = database.getReference(CodesUtil.REFERENCE_PERDORUES);
        perdoruesFirebase.child(user.getUid()).setValue(perdorues);

        onBackPressed();
    }

    private int getRoleIdFromRole(String role){
        if(role.equals("Shofer")){
            return 1;
        }
        else {
            return 2;
        }
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Rregjistrimi Deshtoi!", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Fusni një email të saktë");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Fjalëkalimi midis 4 dhe 10 karaktere");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Fjalëkalimet nuk përputhen");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }
        return valid;
    }
}
