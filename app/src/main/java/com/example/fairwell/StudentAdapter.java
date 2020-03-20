package com.example.fairwell;


import android.app.Activity;
import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private Activity context;
    private List<Student> studentList;


    public StudentAdapter(Activity context,List<Student> studentList){

        super(context,R.layout.list_layout,studentList);

        this.context = context;
        this.studentList = studentList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView);
        TextView textViewDetail = (TextView) listViewItem.findViewById(R.id.textView2);


        Student student = studentList.get(position);

        textViewName.setText(student.getStudentName());
        textViewDetail.setText("Email ID : "+student.getStudentEmail()+ "\n" + "Enrollment Number : "+ student.getStudentEnroll() + "\n" + "Current Year : "+ student.getStudentYear()+"\n"+"Mobile Number : " +student.getStudentMobile()+"\n"+"Registration Fees Paid : "+student.getStudentFees());
        Linkify.addLinks(textViewName, Linkify.WEB_URLS|Linkify.EMAIL_ADDRESSES);

        return  listViewItem;

    }
}
