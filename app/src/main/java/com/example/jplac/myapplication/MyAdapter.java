package com.example.jplac.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

//    String[] names;
//    int[] flags;
    Context mContext;
    //private TextView ct, cn, s, p, a, cnTV, sTV, pTV, aTV;
    //private Button present, remove;
    ArrayList<String> myCourseTitle, myCourseNumCode, mySection, myProfessor, myAbsences;



    public MyAdapter(Context c,
                     ArrayList<String> courseTitle,
                     ArrayList<String> courseNumCode,
                     ArrayList<String> section,
                     ArrayList<String> professor,
                     ArrayList<String> absences) {
        super(c, R.layout.individual_course);

        this.mContext = c;
        this.myCourseTitle = courseTitle;
        this.myCourseNumCode = courseNumCode;
        this.mySection = section;
        this.myProfessor = professor;
        this.myAbsences = absences;
    }

//    @Override
//    public int getCount() {
//        return names.length;
//    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.individual_course, parent, false);

            mViewHolder.ct = (TextView) convertView.findViewById(R.id.courseTitle);
            mViewHolder.cn = (TextView) convertView.findViewById(R.id.Course);
            mViewHolder.s = (TextView) convertView.findViewById(R.id.Section);
            mViewHolder.p = (TextView) convertView.findViewById(R.id.Professor);
            mViewHolder.a = (TextView) convertView.findViewById(R.id.Absences);
            mViewHolder.cnTV = (TextView) convertView.findViewById(R.id.CourseTV);
            mViewHolder.sTV = (TextView) convertView.findViewById(R.id.SectionTV);
            mViewHolder.pTV = (TextView) convertView.findViewById(R.id.ProfessorTV);
            mViewHolder.aTV = (TextView) convertView.findViewById(R.id.AbsencesTV);
            //mViewHolder.present = (Button) convertView.findViewById(R.id.Present);
            //mViewHolder.remove = (Button) convertView.findViewById(R.id.Remove);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        mViewHolder.ct.setText(myCourseTitle.get(position));
        mViewHolder.cn.setText(myCourseNumCode.get(position));
        mViewHolder.s.setText(mySection.get(position));
        mViewHolder.p.setText(myProfessor.get(position));
        mViewHolder.a.setText(myAbsences.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView ct;
        TextView cn;
        TextView s;
        TextView p;
        TextView a;
        TextView cnTV;
        TextView sTV;
        TextView pTV;
        TextView aTV;
        //Button present;
        //Button remove;
    }
}
