package com.studentinfosys.studentinformationmanagementsystem;



public class EnrolledStudent {
    private int student_id;
    private String student_name;

    EnrolledStudent(int student_id, String student_name)
    {
        this.student_id = student_id;
        this.student_name = student_name;
    }


    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
