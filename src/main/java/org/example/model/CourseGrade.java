package org.example.model;

public class CourseGrade {
    private Course course;
    private double grade = 0.0;
    private double attendance = 0.0;

    public CourseGrade(Course course) {
        this.course = course;
        grade = 0.0;
    }

    public Course getCourse() {
        return course;
    }

    public void updateGrade(double grade) {
        if(grade < 0.0 || grade > 100.0) {
            System.out.println("Invalid Grade.");
            return;
        }
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public void updateAttendance(double attendance) {
         if(attendance < 0.0 || attendance > 100.0) {
            System.out.println("Invalid Attendance.");
            return;
        }
        this.attendance = attendance;
    }

    public double getAttendance() {
        return attendance;
    }
}
