package com.example.fairwell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StudentList extends AppCompatActivity {

    DatabaseReference databaseStudent;

    ListView listViewStudent;
    Bundle extras;
    List<Student> studentList;

    String newString;
    Button delButton;
    ProgressBar progressBarUD;


    ArrayList<String> EmailArray = new ArrayList<>();
    ArrayList<String> MobileArray = new ArrayList<>();
    String[] emails;
    String[] mnumbers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);



        listViewStudent = (ListView) findViewById(R.id.listViewStudent);
        studentList = new ArrayList<>();
        databaseStudent = FirebaseDatabase.getInstance().getReference("student");

        listViewStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Student student = studentList.get(position);
                showUpdateDialogue(student.getStudentName(),student.getKey());
                return false;
            }
        });

    }


    public void mailAll(View view){
        String Null="";
        if(!EmailArray.isEmpty()) {
            Intent in = new Intent(Intent.ACTION_SEND_MULTIPLE);
            in.putExtra(Intent.EXTRA_EMAIL, emails);
            in.putExtra(Intent.EXTRA_SUBJECT,"Regarding Farewell party");
            in.putExtra(Intent.EXTRA_TEXT,"Hi Alieans, Get ready for show!!!");
            in.setType("text/plain");
            startActivity(in);
        }
        else {
            Toast.makeText(this,"Receiver not available",Toast.LENGTH_SHORT).show();
        }

    }
    String numbers="";
    public void smsAll(View view){

        for(int i=0;i<mnumbers.length;i++){
            numbers = numbers.concat(mnumbers[i]+';');
        }
        if(numbers!= "") {
            Intent in = new Intent(Intent.ACTION_VIEW);
            in.putExtra("address", numbers);
            in.putExtra("sms_body", "Hello my friends, Info regarding farewell party!");
            in.setType("vnd.android-dir/mms-sms");
            startActivity(in);
        }
        else {
            Toast.makeText(this,"Receiver not available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MobileArray.clear();
        EmailArray.clear();
        databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentList.clear();
                for(DataSnapshot studentSnapshot: dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);



                    studentList.add(student);

                    MobileArray.add(student.getStudentMobile());
                    EmailArray.add(student.getStudentEmail());



                }
                emails = new String[EmailArray.size()];
                emails = EmailArray.toArray(emails);

                mnumbers = new String[MobileArray.size()];
                mnumbers = MobileArray.toArray(mnumbers);

                StudentAdapter adapter = new StudentAdapter(StudentList.this,studentList);
                listViewStudent.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void showUpdateDialogue(String studentName, final String studentId){
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();


        final View dialogview = inflater.inflate(R.layout.update_layout,null);
        dialogbuilder.setView(dialogview);

        final EditText editTextName = (EditText) dialogview.findViewById(R.id.Upname);
        final EditText editTextEnroll = (EditText) dialogview.findViewById(R.id.Upenrollment);
        final EditText editTextEmail = (EditText) dialogview.findViewById(R.id.Upemail);
        final EditText editTextMobile = (EditText) dialogview.findViewById(R.id.Upmobile);
        final EditText editTextYear = (EditText) dialogview.findViewById(R.id.Upyear);
        final EditText editTextFees = (EditText) dialogview.findViewById(R.id.Upfees);
        delButton = dialogview.findViewById(R.id.delete);
        final Button update = dialogview.findViewById(R.id.update);




        dialogbuilder.setTitle("Updating Student : " + studentName);



        final AlertDialog alertDialog = dialogbuilder.create();




        alertDialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String studentName = editTextName.getText().toString().trim();
                String studentEmail = editTextEmail.getText().toString().trim();
                String studentEnroll = editTextEnroll.getText().toString().trim();
                String studentYear = editTextYear.getText().toString().trim();
                String studentMobile = editTextMobile.getText().toString().trim();
                String studentFees = editTextFees.getText().toString().trim();

                if(TextUtils.isEmpty(studentName)){
                    editTextName.setError("Name Required");
                }
                else {
                    UpdateStudent(studentId,studentName,studentEmail,studentEnroll,studentFees,studentYear,studentMobile);
                    alertDialog.dismiss();
                }


            }
        });


        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteStudent(studentId);
                alertDialog.dismiss();
            }
        });


    }

    private void deleteStudent(String studentId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("student").child(studentId);


        databaseReference.removeValue();



            Toast.makeText(this,"Student Removed!!!",Toast.LENGTH_SHORT).show();






    }

    private boolean UpdateStudent(String id,String studentName, String studentEmail,String studentEnroll,String studentFees,String studentYear,String studentMobile){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("student").child(id);

        Student student = new Student(id,studentName,studentEmail,studentEnroll,studentFees,studentYear,studentMobile);
        databaseReference.setValue(student);

        Toast.makeText(this,"Artist Updated",Toast.LENGTH_SHORT).show();



        return true;
    }


}
