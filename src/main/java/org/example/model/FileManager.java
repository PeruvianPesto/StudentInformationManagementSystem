package org.example.model;
import java.io.*;
import java.util.Scanner;

public class FileManager {
    private static final String STUDENTS_FILE = "students.txt";
    private static final String TEACHERS_FILE = "teachers.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";
    private static final String ASSIGNMENTS_FILE = "assignments.txt";

    // Save all data to files
    public static void saveAllData(Administration admin) {
        saveStudents(admin);
        saveTeachers(admin);
        saveCourses(admin);
        saveEnrollments(admin);
        saveAssignments(admin);
        System.out.println("All data saved successfully!");
    }

    // Load all data from files
    public static void loadAllData(Administration admin) {
        loadStudents(admin);
        loadTeachers(admin);
        loadCourses(admin);
        loadEnrollments(admin);
        loadAssignments(admin);
        System.out.println("All data loaded successfully!");
    }

    // Save students to file
    private static void saveStudents(Administration admin) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : admin.getStudents()) {
                writer.println("STUDENT:" + student.getId() + ":" + student.getName());
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    // Save teachers to file
    private static void saveTeachers(Administration admin) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEACHERS_FILE))) {
            for (Teacher teacher : admin.getTeachers()) {
                writer.println("TEACHER:" + teacher.getId() + ":" + teacher.getName());
            }
        } catch (IOException e) {
            System.err.println("Error saving teachers: " + e.getMessage());
        }
    }

    // Save courses to file
    private static void saveCourses(Administration admin) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : admin.getCourses()) {
                String instructorId = course.getInstructor() != null ?
                        String.valueOf(course.getInstructor().getId()) : "NULL";
                writer.println("COURSE:" + course.getCrn() + ":" + course.getCourseName() +
                        ":" + course.getCourseSize() + ":" + course.getCredits() +
                        ":" + instructorId);
            }
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    // Save student enrollments to file
    private static void saveEnrollments(Administration admin) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ENROLLMENTS_FILE))) {
            for (Student student : admin.getStudents()) {
                // You'll need to add a method to get current courses from Student
                // For now, this is a placeholder structure
                writer.println("ENROLLMENT:" + student.getId() + ":COURSES_PLACEHOLDER");
            }
        } catch (IOException e) {
            System.err.println("Error saving enrollments: " + e.getMessage());
        }
    }

    // Save teacher-course assignments to file
    private static void saveAssignments(Administration admin) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ASSIGNMENTS_FILE))) {
            for (Course course : admin.getCourses()) {
                if (course.getInstructor() != null) {
                    writer.println("ASSIGNMENT:" + course.getInstructor().getId() +
                            ":" + course.getCrn());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving assignments: " + e.getMessage());
        }
    }

    // Load students from file
    private static void loadStudents(Administration admin) {
        try (Scanner scanner = new Scanner(new File(STUDENTS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("STUDENT:")) {
                    String[] parts = line.split(":");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        admin.addStudent(new Student(id, name));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Students file not found. Starting with empty student list.");
        } catch (Exception e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
    }

    // Load teachers from file
    private static void loadTeachers(Administration admin) {
        try (Scanner scanner = new Scanner(new File(TEACHERS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("TEACHER:")) {
                    String[] parts = line.split(":");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        admin.addTeacher(new Teacher(id, name));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Teachers file not found. Starting with empty teacher list.");
        } catch (Exception e) {
            System.err.println("Error loading teachers: " + e.getMessage());
        }
    }

    // Load courses from file
    private static void loadCourses(Administration admin) {
        try (Scanner scanner = new Scanner(new File(COURSES_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("COURSE:")) {
                    String[] parts = line.split(":");
                    if (parts.length >= 6) {
                        int crn = Integer.parseInt(parts[1]);
                        String courseName = parts[2];
                        int courseSize = Integer.parseInt(parts[3]);
                        float credits = Float.parseFloat(parts[4]);

                        Course course = new Course(courseName, crn, courseSize, credits);
                        admin.addCourse(course);

                        // Set instructor if not NULL
                        if (!parts[5].equals("NULL")) {
                            int instructorId = Integer.parseInt(parts[5]);
                            Teacher instructor = admin.findTeacher(instructorId);
                            if (instructor != null) {
                                course.changeInstructor(instructor);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Courses file not found. Starting with empty course list.");
        } catch (Exception e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
    }

    // Load enrollments from file
    private static void loadEnrollments(Administration admin) {
        try (Scanner scanner = new Scanner(new File(ENROLLMENTS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("ENROLLMENT:")) {
                    // Implementation depends on how you want to store enrollment data
                    // This is a placeholder for now
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Enrollments file not found. Starting with empty enrollments.");
        } catch (Exception e) {
            System.err.println("Error loading enrollments: " + e.getMessage());
        }
    }

    // Load assignments from file
    private static void loadAssignments(Administration admin) {
        try (Scanner scanner = new Scanner(new File(ASSIGNMENTS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("ASSIGNMENT:")) {
                    String[] parts = line.split(":");
                    if (parts.length >= 3) {
                        int teacherId = Integer.parseInt(parts[1]);
                        int courseCrn = Integer.parseInt(parts[2]);
                        admin.assignTeacherToCourse(teacherId, courseCrn);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Assignments file not found. Starting with empty assignments.");
        } catch (Exception e) {
            System.err.println("Error loading assignments: " + e.getMessage());
        }
    }

    // Utility method to check if data files exist
    public static boolean dataFilesExist() {
        File studentsFile = new File(STUDENTS_FILE);
        File teachersFile = new File(TEACHERS_FILE);
        File coursesFile = new File(COURSES_FILE);

        return studentsFile.exists() || teachersFile.exists() || coursesFile.exists();
    }

    // Create sample data files for testing
    public static void createSampleData() {
        try {
            // Create sample students
            PrintWriter studentWriter = new PrintWriter(new FileWriter(STUDENTS_FILE));
            studentWriter.println("STUDENT:1001:John Smith");
            studentWriter.println("STUDENT:1002:Jane Doe");
            studentWriter.println("STUDENT:1003:Bob Johnson");
            studentWriter.close();

            // Create sample teachers
            PrintWriter teacherWriter = new PrintWriter(new FileWriter(TEACHERS_FILE));
            teacherWriter.println("TEACHER:2001:Dr. Wilson");
            teacherWriter.println("TEACHER:2002:Prof. Davis");
            teacherWriter.println("TEACHER:2003:Dr. Martinez");
            teacherWriter.close();

            // Create sample courses
            PrintWriter courseWriter = new PrintWriter(new FileWriter(COURSES_FILE));
            courseWriter.println("COURSE:3001:Computer Science 101:30:3.0:2001");
            courseWriter.println("COURSE:3002:Mathematics 201:25:4.0:2002");
            courseWriter.println("COURSE:3003:Physics 101:20:3.0:NULL");
            courseWriter.close();

            // Create sample assignments
            PrintWriter assignmentWriter = new PrintWriter(new FileWriter(ASSIGNMENTS_FILE));
            assignmentWriter.println("ASSIGNMENT:2001:3001");
            assignmentWriter.println("ASSIGNMENT:2002:3002");
            assignmentWriter.close();

            System.out.println("Sample data files created successfully!");

        } catch (IOException e) {
            System.err.println("Error creating sample data: " + e.getMessage());
        }
    }
}
