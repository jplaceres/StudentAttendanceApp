package com.example.jplac.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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




        DocumentReference studentDoc = db.collection("Student").document(UID);
        studentDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Student loggedStudent = documentSnapshot.toObject(Student.class);
                courseTexts = loggedStudent.getCourses();
                if (courseTexts != null) {
                    for (String shortC : courseTexts) {

                        //course
                        coursePrefix = shortC.substring(0, 3);
                        courseCode = shortC.substring(3, 7);
                        //section
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

        list = (ListView) findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter(this, courseTitle, courseNumCode, section, professor, absences, present, remove);
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

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> myCourseTitle, myCourseNumCode, mySection, myProfessor, myAbsences;
        Button myPresent, myRemove;

        MyAdapter(Context c,
                  ArrayList<String> courseTitle,
                  ArrayList<String> courseNumCode,
                  ArrayList<String> section,
                  ArrayList<String> professor,
                  ArrayList<String> absences,
                  Button present,
                  Button remove) {
            super(c, R.layout.individual_course, R.id.indCourse);
            this.context = c;
            this.myCourseTitle = courseTitle;
            this.myCourseNumCode = courseNumCode;
            this.mySection = section;
            this.myProfessor = professor;
            this.myAbsences = absences;
            this.myPresent = present;
            this.myRemove = remove;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.individual_course, parent, false);
            TextView ct = (TextView) findViewById(R.id.courseTitle);
            TextView cn = (TextView) findViewById(R.id.Course);
            TextView s = (TextView) findViewById(R.id.Section);
            TextView p = (TextView) findViewById(R.id.Professor);
            TextView a = (TextView) findViewById(R.id.Absences);
            TextView cnTV = (TextView) findViewById(R.id.CourseTV);
            TextView sTV = (TextView) findViewById(R.id.SectionTV);
            TextView pTV = (TextView) findViewById(R.id.ProfessorTV);
            TextView aTV = (TextView) findViewById(R.id.AbsencesTV);
            final Button present = (Button) findViewById(R.id.Present);
            final Button remove = (Button) findViewById(R.id.Remove);

            ct.setText(myCourseTitle.get(position));
            cn.setText(myCourseNumCode.get(position));
            s.setText(mySection.get(position));
            p.setText(myProfessor.get(position));
            a.setText(myAbsences.get(position));

            present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyCourseList.this, CheckIn.class);
                    startActivity(intent);
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                }
            });

            return row;
        }
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