package com.example.jplac.myapplication;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MarkAttended {

    private static String UID;

    public static void markAttended(Course course, String attending) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // UID specific to the provider
                UID = profile.getUid();

            }

        }
        String courseID = course.getPrefix() + course.getCode() + course.getSection();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference studentDoc = db.collection(courseID).document(UID);


        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        switch (attending)
        {
            case "present":
                studentDoc.update("datesPresent", FieldValue.arrayUnion(date));
                break;

            case "absent":
                studentDoc.update("datesAbsent", FieldValue.arrayUnion(date));
                break;

            case "late":
                studentDoc.update("datesLate", FieldValue.arrayUnion(date));
                break;
        }




    }
}

