package org.example.model;

public class Course {

    private String courseName;
    private int crn;
    private Teacher instructor;
    private int courseSize;
    private Student[] enrolledStudents;
    private int numStudents;
    private float credits;
    
    public Course(String courseName, int crn, int courseSize, float credits) {
        this.courseName = courseName;
        this.crn = crn;
        this.courseSize = courseSize;
        this.credits = credits;
        enrolledStudents = new Student[courseSize];
        numStudents = 0;
    }

    public float getCredits() {
        return credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCrn() {
        return crn;
    }

    public void changeInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    public Teacher getInstructor () {
        return instructor;
    }

    public void resetInstructor() {
        instructor = null;
    }

    public int getCourseSize() {
        return courseSize;
    }

    public void enrollStudent(Student stud) {
        if(numStudents == courseSize) {
            System.out.println("No space left in course, cannot add " + stud.getName());
            return;
        }
        enrolledStudents[numStudents] = stud;
        numStudents++;
    }

    public void removeStudent(int sid) {
        int i;
        for(i = 0; i < numStudents; i++ ){
            if(enrolledStudents[i].getId() == sid) 
                break;
        }
        if(i == numStudents) {
            System.out.println("Student Not Found, cannot remove.");
            return;
        }
        for(int j = i; j < numStudents - 1; j++) {
            enrolledStudents[j] = enrolledStudents[j+1];
        }
        numStudents--;
    }

    public void printStudents() {
        for(int i = 0; i < numStudents; i++) {
            System.out.println("Name: " + enrolledStudents[i].getName() + " ID: " + enrolledStudents[i].getId());
        }
    }

    public Student getEnrolled(int id) {
        int i;
        for (i = 0; i < numStudents; i++) {
            if(enrolledStudents[i].getId() == id) 
                break;
        }
        if(i == numStudents)
            return null;
        return enrolledStudents[i];
    }

}
