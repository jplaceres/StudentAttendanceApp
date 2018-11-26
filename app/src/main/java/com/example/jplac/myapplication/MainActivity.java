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

    private static final String TAG = "TEST";

    private TextView title;
    private TextView prompt;

    private EditText emailET;
    private EditText passwordET;

    private Button signIn;
    private TextView createUser;
    private Boolean confirmSignIn;

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

        setConfirmsSignIn(false);

        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailET.getText().toString(), passwordET.getText().toString());

                if (confirmSignIn) {
                    Log.println(Log.ASSERT, TAG, "BEFORE NEW ACTIVITY:" + confirmSignIn.toString());
                    Intent intent = new Intent(MainActivity.this, MyCourseList.class);
                    startActivity(intent);
                }else {
                    Log.println(Log.ASSERT, TAG, "Please sign in again: " + confirmSignIn.toString());
                }
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetupInitial.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(String email, String password) {
        Log.println(Log.ASSERT, TAG, "Button pressed:" + confirmSignIn.toString());

        //Goes through the Firebase Authentication to sign in
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.println(Log.ASSERT, TAG, "SUCCESS!!");
                    setConfirmsSignIn(true);
                    Log.println(Log.ASSERT, TAG, "TEST 2+ - after button & confirmed:" + confirmSignIn.toString());
                    FirebaseUser user = mAuth.getCurrentUser();
                    }
            };
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            //set fields to clear....
            emailET.setVisibility(View.GONE);
            passwordET.setVisibility(View.GONE);

            //set prompt to restart sign in process again
            Log.println(Log.ASSERT, TAG, "RETRY SIGN IN!!");

        } else {
            emailET.setVisibility(View.VISIBLE);
            passwordET.setVisibility(View.VISIBLE);
        }
    }

    public Boolean getConfirmSignIn() {
        return confirmSignIn;
    }

    public void setConfirmsSignIn(Boolean tf){
        confirmSignIn = tf;
    }
}
