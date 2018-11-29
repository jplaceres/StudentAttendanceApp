package com.example.jplac.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class CustomAdapter extends AppCompatActivity {
    TextView courseTitle, section, professor, absences;
    private Button present, remove;

//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        return inflater.inflate(R.layout.individual_course, parent, false);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_course);

        courseTitle = findViewById(R.id.courseTitle);
        section = findViewById(R.id.Section);
        professor = findViewById(R.id.Professor);
        absences = findViewById(R.id.Absences);
        present = findViewById(R.id.Present);
        remove = findViewById(R.id.Remove);



        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomAdapter.this, CourseSearch.class);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPass = remove.getTag().toString();
                MyCourseList.remove(idPass);
                //
            }
        });
    }
}

