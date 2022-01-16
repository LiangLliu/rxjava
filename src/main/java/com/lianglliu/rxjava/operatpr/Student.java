package com.lianglliu.rxjava.operatpr;

import java.util.List;

public class Student {
    String name;
    List<Course> courses;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class Course {
        String name;

        public Course() {
        }

        public Course(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


}
