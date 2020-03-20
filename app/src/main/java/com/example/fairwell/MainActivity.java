package com.example.fairwell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText email,enrollment,mobile,year,fees;
    Button submit;
    TextView data;


    DatabaseReference databaseStudent;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseStudent = FirebaseDatabase.getInstance().getReference("student");

        data = findViewById(R.id.entries);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        submit = findViewById(R.id.submit);
        enrollment = findViewById(R.id.enrollment);
        mobile = findViewById(R.id.mobile);
        year = findViewById(R.id.year);
        fees = findViewById(R.id.fees);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkConnected()) {
                    addArtist();
                }
                else{
                    showNetworkStatus();
                }
            }
        });





    }



    private void addArtist(){
        String studentName = name.getText().toString().trim();
        String studentEmail = email.getText().toString().trim();
        String studentEnroll = enrollment.getText().toString().trim();
        String studentYear = year.getText().toString().trim();
        String studentMobile = mobile.getText().toString().trim();
        String studentFees = fees.getText().toString().trim();




        if(!TextUtils.isEmpty(studentName)){

            String id = databaseStudent.push().getKey();
            Student student = new Student(id,studentName,studentEmail,studentEnroll,studentFees,studentYear,studentMobile);

            databaseStudent.child(id).setValue(student);


            Toast.makeText(this,"Artist Added", Toast.LENGTH_SHORT).show();



            Intent menuIntent = new Intent(this, StudentList.class);
            startActivity(menuIntent);


            name.setText(null);
            email.setText(null);
            enrollment.setText(null);
            year.setText(null);
            mobile.setText(null);
            fees.setText(null);
        }
        else{
            Toast.makeText(this,"You should enter name", Toast.LENGTH_SHORT).show();
        }
    }

    public void showNetworkStatus(){
        Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_SHORT).show();
    }

    public void showList(View view) {
        Intent menuIntent = new Intent(this, StudentList.class);
        startActivity(menuIntent);
    }
}
