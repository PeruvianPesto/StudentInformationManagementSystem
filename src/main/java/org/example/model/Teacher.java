package org.example.model;

public class Teacher extends Person{
    public static int maxCourses = 5;
    private Course [] courses;
    private int coursesTaught = 0;

    public Teacher(int id, String name) {
        super(id, name);
        courses = new Course[maxCourses];
    }

    public static int getMaxCourses() {
        return maxCourses;
    }

    public static void setMaxCourses(int max) {
        maxCourses = max;
    }

    public int getCoursesTaught() {
        return coursesTaught;
    }

    public void teachCourse(Course course) {
        if(coursesTaught == maxCourses) {
            System.out.println("Cannot teach course, you are already teaching the max amount of courses");
            return;
        }
        courses[coursesTaught] = course;
        course.changeInstructor(this);
        coursesTaught++;
    }

    public void removeCourse(int crn) {
        int i;
        for(i = 0; i < coursesTaught; i++) {
            if(courses[i].getCrn() == crn) 
                break;
        }
        if(i == coursesTaught) {
            System.out.println("Course is already not taught by the instructor");
            return;
        }
        courses[i].resetInstructor();
        for(int j = i; j < coursesTaught - 1; j++) {
            courses[j] = courses[j+1];
        }
        coursesTaught--;
    }

    public void printCourses() {
        for(int i = 0; i < coursesTaught; i++) {
            System.out.println("Course Name: " + courses[i].getCourseName() + " CRN: " + courses[i].getCrn() + " Students: " + courses[i].getCourseSize());
        }
    }
}
