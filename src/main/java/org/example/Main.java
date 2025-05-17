package org.example;
import org.example.model.*;

public class Main {
    public static void main(String[] args) {

        //COURSE FUNCTIONALITY TEST
        /* 
        Student [] arr = new Student[20];
        for(int i = 0; i < 20; i++) {
            arr[i] = new Student(i, "Student #" + i);
        }

        Course a = new Course("Math", 2000, 19);

        for(int i = 0; i < 20; i++) {
            a.enrollStudent(arr[i]);
        }

        a.removeStudent(200000);
        a.removeStudent(0);
        a.removeStudent(19);
        a.removeStudent(10);
        a.printStudents(); 
        */

        //TEACHER FUNCTIONALITY TEST
        /* 
        Course [] arr = new Course[5];
        arr[0] = new Course("Math", 1, 20, 4.0f);
        arr[1] = new Course("Science", 2, 20, 4.0f);
        arr[2] = new Course("Art", 3, 20, 4.0f);
        arr[3] = new Course("English", 4, 20, 4.0f);
        arr[4] = new Course("CS", 5, 20, 4.0f);

        Teacher a = new Teacher(1, "Teacher");

        for(int i = 0; i < 5; i++) {
            a.teachCourse(arr[i]);
        }

        a.removeCourse(6);
        a.removeCourse(1);
        a.removeCourse(5);
        a.printCourses();
        */

        //Student Add and Remove Courses, Grades Test
        /*
        Course [] arr = new Course[5];
        Student s1 = new Student(1234, "John");
        arr[0] = new Course("Math", 1, 20, 4.0f);
        arr[1] = new Course("Science", 2, 20, 4.0f);
        arr[2] = new Course("Art", 3, 20, 4.0f);
        arr[3] = new Course("English", 4, 20, 4.0f);
        arr[4] = new Course("CS", 5, 20, 10.0f);
        for(int i = 0; i < 5; i++) {
            s1.addCourse(arr[i]);
        }
        s1.printCurrentCourses();
        s1.printCompletedCourses();

        s1.removeCourse(5);
        s1.removeCourse(1);
        s1.completeCourse(4);

        s1.printCurrentCourses();
        s1.printCompletedCourses();
        */

        //Teacher Updating Student Grade and Attendance
        /* 
        Student s1 = new Student(1234, "John");
        Teacher t1 = new Teacher(2000, "Bob");
        Course [] arr = new Course[2];
        arr[0] = new Course("Math", 1, 5, 4.0f);
        arr[1] = new Course("Science", 2, 5, 4.0f);

        s1.addCourse(arr[1]);

        s1.printCurrentCourses();

        t1.teachCourse(arr[0]);
        t1.teachCourse(arr[1]);

        t1.changeGrade(arr[0].getCrn(), s1.getId(), 60);
        t1.changeGrade(arr[1].getCrn(), s1.getId(), -60);
        t1.changeGrade(arr[1].getCrn(), s1.getId(), 100);

        s1.printCurrentCourses();

        t1.changeAttendance(arr[0].getCrn(), s1.getId(), 60);
        t1.changeAttendance(arr[1].getCrn(), s1.getId(), -60);
        t1.changeAttendance(arr[1].getCrn(), s1.getId(), 100);

        System.out.println("\nAttendance: " + s1.getCourseGrade(arr[1].getCrn()).getAttendance());
        */

    }

}