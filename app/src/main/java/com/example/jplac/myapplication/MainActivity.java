package com.example.jplac.myapplication;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
//import com.google.firebase.quickstart.auth.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private TextView title;
    private TextView prompt;

    private EditText emailET;
    private EditText passwordET;

    private Button signIn;
    private TextView createUser;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //get variables from XML
        title = findViewById(R.id.textView1);
        prompt = findViewById(R.id.textView3);
        emailET = (EditText) findViewById(R.id.editText);
        passwordET = (EditText) findViewById(R.id.editText2);
        signIn = (Button) findViewById(R.id.button1);
        createUser = findViewById(R.id.textView2);

        mAuth = FirebaseAuth.getInstance();

        //String email = emailET.getText().toString();
        //final String password = passwordET.getText().toString();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "SignIn pressed", Toast.LENGTH_LONG).show();

                signIn(emailET.getText().toString(), passwordET.getText().toString());

                Intent intent = new Intent(MainActivity.this, MyCourseList.class);
                startActivity(intent);
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "createNew pressed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SetupInitial.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
//                        // [START_EXCLUDE]
//
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//
//                        }
//
//                        hideProgressDialog();
//
//                        // [END_EXCLUDE]                    }
                            };
                        });
    }


                        //checks if user is already signed in

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }


        public void updateUI (FirebaseUser user){
            Toast.makeText(MainActivity.this, "updateUI", Toast.LENGTH_LONG).show();
        }

//    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
//            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
//            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
//
//        } else {
//
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
//            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
//
//        }
//    }
    }
