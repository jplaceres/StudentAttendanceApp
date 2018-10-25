package com.example.jplac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CheckIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in_layout);
    }

    void returnMessage(boolean error){
        if (error){

        }
        else{
            Toast.makeText(this, "You are ", Toast.LENGTH_SHORT).show();
        }
    }
}
