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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class SetupInitial extends AppCompatActivity {
    //In this setup, the application will be taking input from the user from two layouts.
    //As such we declare the necessary components of our setup: First and last name, email, student ID, and the password.
    //In order to take the input from the user, we must use editTexts, these edit texts will be matched to the corresponding edit text
    //Then read from.
    FirebaseAuth mAuth;
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
    DatabaseReference mDatabase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference students = db.collection("Student");

    //This is the oncreate, which will set the contentView, then obtain the edit texts by id, and match them to the corresponding edit text.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.user_setup1);
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
        Toast.makeText(this,firstName + " " + lastName + " " + studentID, Toast.LENGTH_LONG).show();
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

        if (password.equals(passwordConfirm) && password.length() >= 6){

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String authenticationID = mAuth.getCurrentUser().getUid();
                                Toast.makeText(SetupInitial.this,authenticationID, Toast.LENGTH_LONG);
                                addUserToDatabase(email,firstName,lastName,studentID,authenticationID);
                                Toast.makeText(SetupInitial.this,user.getEmail(),Toast.LENGTH_LONG).show();
                                //updateUI(user);
                                User user2 = new User(email,firstName,lastName,studentID,authenticationID);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SetupInitial.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
            //String authenticationID = mAuth.getCurrentUser().getUid();
            //Toast.makeText(SetupInitial.this,authenticationID, Toast.LENGTH_LONG);
            //addUserToDatabase(email,firstName,lastName,studentID,authenticationID);

            if(mAuth.getCurrentUser().getEmail().equals(email)){
            Intent intentApp = new Intent(SetupInitial.this,CourseSearch.class);
            SetupInitial.this.startActivity(intentApp);}
        }
        if(!password.equals(passwordConfirm)){
            Toast.makeText(this,"Passwords do not match, please check again",Toast.LENGTH_LONG).show();

        }
        if(password.length() < 4){
            Toast.makeText(this, "Password is too short",Toast.LENGTH_LONG).show();
        }
    }

    public void addUserToDatabase(String userEmail, String fName, String lName, String userID, String authenticateID){
        User user = new User(fName, userEmail, lName,authenticateID,userID);

        db.collection("Student").document(userEmail).set(user);
        //db.collection("Student").document(email).set(user);
    }
}
