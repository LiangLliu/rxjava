package com.lianglliu.rxjava.operatpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentModel {
    static List<Student> students;

    public static void init() {
        students = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Student student = new Student();

            student.setCourses(Arrays.asList(
                    new Student.Course("Course 1"),
                    new Student.Course("Course 2"),
                    new Student.Course("Course 3"),
                    new Student.Course("Course ")
            ));
            students.add(student);
        }
    }

    public static List<Student> getStudents() {
        return students;
    }
}
