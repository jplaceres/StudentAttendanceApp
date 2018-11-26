package com.example.jplac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CheckIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in_layout);
    }


    public void takePicQr(View view){

    }
    public void takePicFace(View view){

    }
    public void changeCourse(View view){

    }
    public void returnMessage(boolean error){
        if (error){
            Toast.makeText(this,"Error with your photo try again",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Confirmed", Toast.LENGTH_SHORT).show();
        }
    }
}
