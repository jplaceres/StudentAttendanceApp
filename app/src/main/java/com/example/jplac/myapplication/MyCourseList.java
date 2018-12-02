package com.example.jplac.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    public static Course courseClass;

    private Button addCourse, present, remove;
    private String UID;
    private String coursePrefix;
    private String courseCode;
    private String courseSection;
    private Query queryCourse;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courses = db.collection("Course");

    private List<String> courseTexts;
    private ArrayList<Course> coursesTaking;
    private ArrayList<String> courseTitle, courseNumCode, section, professor, absences;
    private ListView list;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesTaking = new ArrayList<Course>();
        courseTitle = new ArrayList<String>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // UID specific to the provider
                UID = profile.getUid();
            }
        }

        list = (ListView) findViewById(R.id.list);
        ArrayList<String> courseList;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String classCourse = list.getItemAtPosition(position).toString();

                Intent intentApp = new Intent(MyCourseList.this,CheckIn.class);
                intentApp.putExtra("COURSE_TITLE", classCourse);
                MyCourseList.this.startActivity(intentApp);

            }
        });


        DocumentReference documentReference = db.collection("Student").document(user.getEmail());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user2 = documentSnapshot.toObject(User.class);
                Toast.makeText(getApplicationContext(),user2.getEmail(),Toast.LENGTH_LONG).show();
                ArrayAdapter adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,user2.getCourses());
                list.setAdapter(adapter2);

            }
        });
       // Toast.makeText(this,courseTitle.get(0),Toast.LENGTH_LONG);

        addCourse = (Button) findViewById(R.id.button);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCourseList.this, CourseSearch.class);
                startActivity(intent);
            }
        });
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

    public Student() { }

    public Student(List<String> Courses, String firstName, String userID, String imageRef, String lastName, String email, String authenticationID) {
        firstName = this.firstName;
        lastName = this.lastName;
        imageRef = this.imageRef;
        userID = this.userID;
        Courses = this.Courses;
        authenticationID = this.authenticationID;
        email = this.email;
    }

    public String getfName() { return firstName; }

    public String getlName() { return lastName; }

    public String getImageRef() { return imageRef; }

    public String getID() { return userID; }

    public List<String> getCourses() { return Courses; }

    public String getAuthID() { return authenticationID; }

    public String getEmail() { return email; }
}