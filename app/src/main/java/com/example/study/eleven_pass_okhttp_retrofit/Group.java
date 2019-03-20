package com.example.study.eleven_pass_okhttp_retrofit;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private List<Student> students;

    public Group(int id) {
        this.id = id;
        students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String name = "Group" + id + "_" + i;
            Student student = new Student(name);
            students.add(student);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
