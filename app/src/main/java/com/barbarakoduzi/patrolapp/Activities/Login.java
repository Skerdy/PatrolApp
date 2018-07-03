package com.barbarakoduzi.patrolapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText emailText;
    private EditText passwordText;
    private TextView signupLink;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private MySharedPref mySharedPref;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference perdoruesi_loguar;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mySharedPref = new MySharedPref(this);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        signupLink = findViewById(R.id.link_signup);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Duke u log-uar ...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            onLoginSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Dështoi!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the PolicActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        perdoruesi_loguar = database.getReference(CodesUtil.REFERENCE_PERDORUES).child(mAuth.getCurrentUser().getUid());

        perdoruesi_loguar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Class T;
                progressDialog.dismiss();

                Log.d("SkerdiPath", "jemi ne data change");

                if(dataSnapshot.child("rol").getValue().toString().equals("2")){
                    //nese eshte shofer dhe nuk eshte incializuar ende shkojme tek
                    Log.d("SkerdiPath", "jemi Polic");
                    T = PolicActivity.class;
                }
                else {
                    Log.d("SkerdiPath", "jemi shofer");
                    T = ShoferActivity.class;
                }

                if (dataSnapshot.child("emer").getValue() == null || (dataSnapshot.child("emer").getValue()!=null && dataSnapshot.child("emer").getValue().toString().length() == 0)) {
                    Log.d("SkerdiPath", "Profili nuk ekziston");
                    if(T.equals(PolicActivity.class)){
                        Log.d("SkerdiPath", "jemi ne Tutorial per polic");
                        T=TutorialActivity.class;
                    }
                    else{
                        Log.d("SkerdiPath", "jemi ne Tutorial per shofer");
                        T=TutorialActivityShofer.class;
                    }

                    loginButton.setEnabled(true);
                    Intent intent;
                    intent = new Intent(Login.this, T);
                    Log.d("SkerdiPath", "Start Activity");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Login.this, T);
                    Log.d("SkerdiPath", "Start Activity else");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}