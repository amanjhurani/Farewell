package com.example.fairwell;


public class Student {
    String studentName;
    String studentEmail;
    String studentEnroll;String studentFees;String studentYear;String studentMobile;
    String key;


    public Student(){}

    public Student(String key,String studentName, String studentEmail,String studentEnroll,String studentFees,String studentYear,String studentMobile) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentEnroll = studentEnroll;
        this.studentYear = studentYear;
        this.studentMobile = studentMobile;
        this.studentFees = studentFees;
        this.key = key;

    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentEnroll() {
        return studentEnroll;
    }

    public String getStudentFees() {
        return studentFees;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public String getKey(){return key;}
}
