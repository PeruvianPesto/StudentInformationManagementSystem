package org.example.model;

import java.util.HashSet;
import java.util.Set;

public class Administration {
    private Set<Student> students;
    private Set<Teacher> teachers;
    private Set<Course> courses;
    
    public Administration() {
        students = new HashSet<>();
        teachers = new HashSet<>();
        courses = new HashSet<>();
    }
    
    public Set<Student> getStudents() {
        return students;
    }
    
    public Set<Teacher> getTeachers() {
        return teachers;
    }
    
    public Set<Course> getCourses() {
        return courses;
    }
    
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    
    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
    
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
    
    public void addCourse(Course course) {
        courses.add(course);
    }
    
    public Student findStudent(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public Teacher findTeacher(int id) {
        return teachers.stream()
                .filter(teacher -> teacher.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public Course findCourse(int crn) {
        return courses.stream()
                .filter(course -> course.getCrn() == crn)
                .findFirst()
                .orElse(null);
    }
    
    public void assignTeacherToCourse(int teacherId, int courseCrn) {
        Teacher teacher = findTeacher(teacherId);
        Course course = findCourse(courseCrn);
        
        if (teacher != null && course != null) {
            teacher.teachCourse(course);
        } else {
            System.out.println("Teacher or course not found");
        }
    }
    
    public void enrollStudentInCourse(int studentId, int courseCrn) {
        Student student = findStudent(studentId);
        Course course = findCourse(courseCrn);
        
        if (student != null && course != null) {
            student.addCourse(course);
        } else {
            System.out.println("Student or course not found");
        }
    }
    
    public void printAllStudents() {
        System.out.println("\nAll Students:");
        students.forEach(student -> 
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName())
        );
    }
    
    public void printAllTeachers() {
        System.out.println("\nAll Teachers:");
        teachers.forEach(teacher -> 
            System.out.println("ID: " + teacher.getId() + ", Name: " + teacher.getName())
        );
    }
    
    public void printAllCourses() {
        System.out.println("\nAll Courses:");
        courses.forEach(course -> 
            System.out.println("CRN: " + course.getCrn() + 
                             ", Name: " + course.getCourseName() + 
                             ", Credits: " + course.getCredits() + 
                             ", Instructor: " + (course.getInstructor() != null ? course.getInstructor().getName() : "None"))
        );
    }
    
    public void printStudentDetails(int studentId) {
        Student student = findStudent(studentId);
        if (student != null) {
            System.out.println("\nStudent Details:");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            student.printCurrentCourses();
            student.printCompletedCourses();
        } else {
            System.out.println("Student not found");
        }
    }
    
    public void printTeacherDetails(int teacherId) {
        Teacher teacher = findTeacher(teacherId);
        if (teacher != null) {
            System.out.println("\nTeacher Details:");
            System.out.println("ID: " + teacher.getId());
            System.out.println("Name: " + teacher.getName());
            teacher.printCourses();
        } else {
            System.out.println("Teacher not found");
        }
    }
}
