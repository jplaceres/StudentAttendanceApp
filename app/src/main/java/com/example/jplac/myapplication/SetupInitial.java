//Robert Hoover

package com.example.jplac.myapplication;


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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SetupInitial extends AppCompatActivity {
    //In this setup, the application will be taking input from the user from two layouts.
    //As such we declare the necessary components of our setup: First and last name, email, student ID, and the password.
    //In order to take the input from the user, we must use editTexts, these edit texts will be matched to the corresponding edit text
    //Then read from.
    private FirebaseAuth mAuth;
    private String firstName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String lastName;
    private String studentID;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextStudentID;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;

    //This is the oncreate, which will set the contentView, then obtain the edit texts by id, and match them to the corresponding edit text.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.user_setup1);
        //nextButton = (Button) findViewById(R.id.button);
        editTextFirstName = (EditText) findViewById(R.id.editText);
        editTextLastName = (EditText) findViewById(R.id.editText2);
        editTextStudentID = (EditText) findViewById(R.id.editText3);

    }


    //This is the method for obtaining the values from user_setup1 layout.
    //It takes them from the edit text, then it changes the content view to user_setup2
    //After that it changes the contentView to the second part of the setup.
    //Since the buttons have the method declared in the XML file, no onClickListner is needed.
    public void saveNameId(View v){
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        studentID = editTextStudentID.getText().toString();

        setContentView(R.layout.user_setup2);
        editTextEmail = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);
        editTextPasswordConfirm = (EditText) findViewById(R.id.editText3);

    }


    //This is the method for obtaining the values from the user_setup2 layout.
    //It tests the passwords to make sure they match and are of a suitable length
    //If they are the program creates the course search. If not it returns an error message.
    //Since the button has this method declared in it's XML file, no Listener is needed.
    public void saveEmailPass(View v){
        final String TAG = "EmailPassword";
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        passwordConfirm = editTextPasswordConfirm.getText().toString();

        if (password == passwordConfirm && password.length() > 4){


            mAuth.createUserWithEmailAndPassword(email, password);


            //Intent intentApp = new Intent(SetupInitial.this,);
            //SetupInitial.this.startActivity(intentApp);
        }
        if(password != passwordConfirm){
            Toast.makeText(this,"Passwords do not match, please check again",Toast.LENGTH_LONG).show();

        }
        if(password.length() < 4){
            Toast.makeText(this, "Password is too short",Toast.LENGTH_LONG).show();
        }
    }

}
