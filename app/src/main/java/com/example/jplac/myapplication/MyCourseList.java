package com.example.jplac.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyCourseList extends AppCompatActivity {
    private Button addCourse;
    private String UID;
    private String coursePrefix;
    private String courseCode;
    private String courseSection;
    private Query queryCourse;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courses = db.collection("Course");
    private List<String> courseTexts;
    private ArrayList<Course> coursesTaking;
    private ArrayList<String> courseNames;
    private Student stud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coursesTaking = new ArrayList<Course>();
        courseNames = new ArrayList<String>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // UID specific to the provider
                UID = profile.getUid();
            }
        } else {
            Toast.makeText(getBaseContext(), "Null user", Toast.LENGTH_SHORT).show();
        }

        DocumentReference studentDoc = db.collection("Student").document(UID);
        studentDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Student loggedStudent = documentSnapshot.toObject(Student.class);
                courseTexts = loggedStudent.getCourses();
                if (courseTexts != null) {
                    for (String shortC : courseTexts) {
                        coursePrefix = shortC.substring(0, 3);
                        courseCode = shortC.substring(3, 7);
                        courseSection = shortC.substring(7, 9);
                        queryCourse = courses.whereEqualTo("Prefix", coursePrefix)
                                .whereEqualTo("Code", courseCode)
                                .whereEqualTo("Section", courseSection);

                        queryCourse.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Course searchresult = document.toObject(Course.class);
                                        coursesTaking.add(searchresult);
                                    }
                                }
                            }
                        });
                    }
                    for (Course c : coursesTaking) {
                        courseNames.add(c.getName());
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Courses not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ListView list = (ListView) findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list,courseNames);
        list.setAdapter(adapter);

        addCourse = (Button) findViewById(R.id.button);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCourseList.this, CourseSearch.class);
                startActivity(intent);
            }
        });
    }

    public void update(String id){
        //Intent intent = new Intent(MyCourseList.this, editActivity.class);
        //intent.putExtra("id", id);
        //startActivity(intent);
    }
    public void remove(String id){
        //myDb.deleteData(id);
        //Cursor res = myDb.getAllData();
        //adapter.changeCursor(res);
    }
}


class Student {

    private String firstName;
    private String lastName;
    private String imageRef;
    private String userID;
    private List<String> Courses;
    private String authenticationID;
    private String email;

    public Student() {

    }

    public Student(List<String> Courses, String firstName, String userID, String imageRef, String lastName, String email, String authenticationID) {

        firstName = this.firstName;
        lastName = this.lastName;
        imageRef = this.imageRef;
        userID = this.userID;
        Courses = this.Courses;
        authenticationID = this.authenticationID;
        email = this.email;


    }

    public String getfName() {

        return firstName;
    }

    public String getlName() {

        return lastName;
    }

    public String getImageRef() {

        return imageRef;
    }

    public String getID() {

        return userID;
    }

    public List<String> getCourses() {

        return Courses;
    }

    public String getAuthID() {

        return authenticationID;
    }

    public String getEmail() {

        return email;
    }
}