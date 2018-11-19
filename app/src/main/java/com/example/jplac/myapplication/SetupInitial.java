//Robert Hoover

package com.example.jplac.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SetupInitial extends AppCompatActivity {

    private String firstName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String lastName;
    private String studentID;
    private Button nextButton;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextStudentID;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_setup1);
        nextButton = (Button) findViewById(R.id.button);
        editTextFirstName = (EditText) findViewById(R.id.editText);
        editTextLastName = (EditText) findViewById(R.id.editText2);
        editTextStudentID = (EditText) findViewById(R.id.editText3);

    }

    public void saveNameId(View v){
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        studentID = editTextStudentID.getText().toString();

        setContentView(R.layout.user_setup2);
        editTextEmail = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);
        editTextPasswordConfirm = (EditText) findViewById(R.id.editText3);
    }

    public void saveEmailPass(View v){
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        passwordConfirm = editTextPasswordConfirm.getText().toString();

        if (password == passwordConfirm){

        }
        if(password != passwordConfirm){
            Toast.makeText(this,"Passwords do not match, please check again",Toast.LENGTH_LONG).show();
        }
    }

}
