package org.example.model;
import java.util.Vector;

public class Student extends Person{
    
    private static float maxUnits = 24.0f;
    private Vector<CourseGrade> currentCourses = new Vector<CourseGrade>(4);;
    private Vector<CourseGrade> completedCourses = new Vector<>(4);

    public Student(int id, String name) {
        super(id, name);
    }

    public CourseGrade getCourseGrade(int crn) {
        int i;
        for(i = 0; i < currentCourses.size(); i++) {
            if(currentCourses.get(i).getCourse().getCrn() == crn)
                break;
        }
        if(i == currentCourses.size()) {
            System.out.println("Course Not Found");
            return null;
        }
        return currentCourses.get(i);
    }

    public float getCurrUnits() {
        float sum = 0.0f;
        for(int i = 0; i < currentCourses.size(); i++) {
            sum += currentCourses.get(i).getCourse().getCredits();
        }
        return sum;
    }

    public float getTotalUnits() {
        float sum = 0.0f;
        for(int i = 0; i < completedCourses.size(); i++) {
            sum += completedCourses.get(i).getCourse().getCredits();
        }
        return sum;
    }

    public void addCourse(Course course) {
        if(getCurrUnits() + course.getCredits() > maxUnits) {
            System.out.println("Max course credits reached, cannot add this course.");
            return;
        }
        course.enrollStudent(this);
        CourseGrade c = new CourseGrade(course);
        currentCourses.add(c);
    }

    public void removeCourse(int crn) {
        int i;
        for(i = 0; i < currentCourses.size(); i++) {
            if(currentCourses.get(i).getCourse().getCrn() == crn)
                break;
        }
        if(i == currentCourses.size()) {
            System.out.println("Course not found, cannot remove.");
            return;
        }
        currentCourses.get(i).getCourse().removeStudent(getId());
        for(int j = i; j < currentCourses.size() - 1; j++) {
            currentCourses.set(j, currentCourses.get(j+1));
        }
        currentCourses.remove(currentCourses.size()-1);
    }

    public void completeCourse(int crn) {
        int i;
        for(i = 0; i < currentCourses.size(); i++) {
            if(currentCourses.get(i).getCourse().getCrn() == crn)
                break;
        }
        if(i == currentCourses.size()) {
            System.out.println("Course not found, cannot complete.");
            return;
        }
        completedCourses.add(currentCourses.get(i));
        currentCourses.get(i).getCourse().removeStudent(getId());
        for(int j = i; j < currentCourses.size() - 1; j++) {
            currentCourses.set(j, currentCourses.get(j+1));
        }
        currentCourses.remove(currentCourses.size()-1);
    }

    public void printCurrentCourses() {
        System.out.println("Current Courses: ");
        System.out.println("Credits: " + getCurrUnits() + "\n");
        for(int i = 0; i < currentCourses.size(); i++) {
            System.out.println("Course: " + currentCourses.get(i).getCourse().getCourseName() + "\nGrade: " + currentCourses.get(i).getGrade() + "\nCRN: " + currentCourses.get(i).getCourse().getCrn() + "\nCredits: " + currentCourses.get(i).getCourse().getCredits() + "\n");
        }
    }

    public void printCompletedCourses() {
        System.out.println("Completed Courses:");
        System.out.println("Credits: " + getTotalUnits() + "\n");
        for(int i = 0; i < completedCourses.size(); i++) {
            System.out.println("Course: " + completedCourses.get(i).getCourse().getCourseName() + "\nGrade: " + completedCourses.get(i).getGrade() + "\nCredits: " + currentCourses.get(i).getCourse().getCredits() + "\n");
        }
    }

}
