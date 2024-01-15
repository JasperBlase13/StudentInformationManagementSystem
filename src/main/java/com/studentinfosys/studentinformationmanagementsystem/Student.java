package com.studentinfosys.studentinformationmanagementsystem;

public class Student {

    private String student_ID;
    private String student_Name;
    private String student_Address;
    private String student_Guardian;
    private String student_PhnNumber;

    private String student_courseID;

    public String getStudent_courseID() {
        return student_courseID;
    }

    public void setStudent_courseID(String student_courseID) {
        this.student_courseID = student_courseID;
    }

    public Student(String student_ID, String student_Name, String student_Address, String student_Guardian, String student_PhnNumber, String student_courseID)
    {
        this.student_ID = student_ID;
        this.student_Name = student_Name;
        this.student_Address = student_Address;
        this.student_Guardian = student_Guardian;
        this.student_PhnNumber = student_PhnNumber;
        this.student_courseID = student_courseID;
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

    public String getStudent_Name() {
        return student_Name;
    }

    public void setStudent_Name(String student_Name) {
        this.student_Name = student_Name;
    }

    public String getStudent_Address() {
        return student_Address;
    }

    public void setStudent_Address(String student_Address) {
        this.student_Address = student_Address;
    }

    public String getStudent_Guardian() {
        return student_Guardian;
    }

    public void setStudent_Guardian(String student_Guardian) {
        this.student_Guardian = student_Guardian;
    }

    public String getStudent_PhnNumber() {
        return student_PhnNumber;
    }

    public void setStudent_PhnNumber(String student_PhnNumber) {
        this.student_PhnNumber = student_PhnNumber;
    }
}
