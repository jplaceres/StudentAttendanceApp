package com.example.jplac.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

    private static final String TAG = "TEST";

    private Button addCourse;
    private String UID;
    private String coursePrefix;
    private String courseCode;
    private String courseSection;
    private Query queryCourse;

    FirebaseFirestore db;
    CollectionReference courses = db.collection("Course");
    FirebaseUser user;

    private List<String> courseTexts;
    private ArrayList<Course> coursesTaking;
    private ArrayList<String> courseTitle, courseNumCode, section, professor, absences;
    private ListView listRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesTaking = new ArrayList<Course>();
        courseTitle = new ArrayList<String>();
        courseNumCode = new ArrayList<String>();
        section = new ArrayList<String>();
        professor = new ArrayList<String>();
        absences = new ArrayList<String>();

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        listRow = findViewById(R.id.list);

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // UID specific to the provider
                UID = profile.getUid();
            }
        } else {
            Toast.makeText(MyCourseList.this, "UID = null", Toast.LENGTH_LONG).show();
        }

        DocumentReference studentDoc = db.collection("Student").document(UID);
        inquire(studentDoc);

        MyAdapter adapter = new MyAdapter(this, courseTitle, courseNumCode, section, professor, absences);
        listRow.setAdapter(adapter);

        listRow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.println(Log.ASSERT, TAG, "clicked on: " + i);

//                Intent mIntent = new Intent(MyCourseList.this, CheckIn.class);
////          mIntent.putExtra("countryName", countryNames[i]);
////          mIntent.putExtra("countryFlag", countryFlags[i]);
//                startActivity(mIntent);
            }
        });

        addCourse = (Button) findViewById(R.id.button);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCourseList.this, CourseSearch.class);
                startActivity(intent);
            }
        });
    }


    public void inquire(DocumentReference studentDoc) {
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
                        courseTitle.add(c.getName());
                        courseNumCode.add(c.getPrefix() + c.getCode());
                        section.add(c.getSection());
                        professor.add(c.getProfessor());

                        //ADD ABSENCES ARRAY
                        absences.add("0");

                    }
                } else {
                    Toast.makeText(getBaseContext(), "Courses not found!", Toast.LENGTH_SHORT).show();
                }
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

    public Student() {
    }

    public Student(List<String> Courses, String firstName, String userID, String imageRef, String lastName, String email, String authenticationID) {
//        firstName = this.firstName;
//        lastName = this.lastName;
//        imageRef = this.imageRef;
//        userID = this.userID;
//        Courses = this.Courses;
//        authenticationID = this.authenticationID;
//        email = this.email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageRef = imageRef;
        this.userID = userID;
        this.Courses = Courses;
        this.authenticationID = authenticationID;
        this.email = email;
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