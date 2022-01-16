package com.lianglliu.rxjava.operatpr;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class MapDemo {
    public static void main(String[] args) {
        StudentModel.init();
        test2();
    }

    private static void test1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Student> students = StudentModel.getStudents();
                for (Student student : students) {
                    List<Student.Course> courses = student.getCourses();
                    for (Student.Course cours : courses) {
                        System.out.println(cours);
                    }
                }
            }
        }).start();
    }

    /**
     * map 操作符
     */
    private static void test2() {
        Observable.fromIterable(StudentModel.getStudents())
                .map(Student::getCourses)
                .flatMap(Observable::fromIterable)
                .subscribe((Consumer<Object>) System.out::println);
    }


}
