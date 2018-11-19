package com.example.jplac.myapplication;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class CourseSearch extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courses = db.collection("Course");
    private EditText courseDeptField;
    private EditText courseNumberField;
    private EditText courseSectionField;
    private String courseDept;
    private String courseNumber;
    private String courseSelection;
    private Query queryCourse;
    private Button nextButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_courses);
        courseDeptField = (EditText) findViewById(R.id.editText4);
        courseNumberField = (EditText) findViewById(R.id.editText6);
        courseSectionField = (EditText) findViewById(R.id.editText7);
        nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    public void search(){
        courseDept = courseDeptField.getText().toString();
        courseNumber = courseNumberField.getText().toString();
        courseSelection = courseSectionField.getText().toString();

        queryCourse = courses.whereEqualTo("Prefix", courseDept)
                .whereEqualTo("Code", courseNumber)
                .whereEqualTo("Section", courseSelection);


        queryCourse.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document: task.getResult())
                    {
                        Course result = document.toObject(Course.class);
                    }

                } else {

                }
            }
        });




    }






}

class Course {
    private String Prefix;
    private String Code;
    private Date EndDate;
    private Date StartDate;
    private String Location;
    private String Professor;
    private String Section;
    private String StartTime;
    private String EndTime;
    private String Name;

    public Course(String prefix, String code, Date endDate, Date startDate, String location, String professor, String section, String startTime, String endTime, String name){
        this.Code = code;
        this.Prefix = prefix;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Location = location;
        this.Professor = professor;
        this.Section = section;
        this.StartTime = startTime;
        this.EndTime = endTime;
        this.Name = name;

    }

    public Course(){

    }

    public String getCode()
    {
        return Code;
    }

    public String getPrefix()
    {
        return Prefix;
    }

    public Date getStartDate()
    {
        return StartDate;
    }

    public Date getEndDate(){
        return EndDate;
    }

    public String getLocation(){
        return Location;
    }

    public String getProfessor()
    {
        return Professor;
    }

    public String getSection()
    {
        return Section;
    }

    public String getStartTime()
    {
        return StartTime;
    }

    public String getEndTime()
    {
        return EndTime;
    }

    public String getName(){
        return Name;
    }


}