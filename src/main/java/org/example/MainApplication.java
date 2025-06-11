package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.model.*;
import org.example.model.FileManager;

public class MainApplication extends Application {

    private Stage primaryStage;
    private Administration administration;
    private User currentUser;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        administration = new Administration();

        // Load data from files
        if (FileManager.dataFilesExist()) {
            FileManager.loadAllData(administration);
        } else {
            FileManager.createSampleData();
            FileManager.loadAllData(administration);
        }

        // Create and load user credentials
        UserManager.createSampleUsers();

        primaryStage.setTitle("School Administration System");
        showLoginPage();
        primaryStage.show();
    }

    private void showLoginPage() {
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(50));
        loginBox.setStyle("-fx-background-color: #f0f8ff;");

        // Title
        Label titleLabel = new Label("School Administration System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c5aa0;");

        // Login form
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(350);
        formBox.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-background-radius: 10;");

        Label loginLabel = new Label("Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Username field
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 14px;");

        // Password field
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-font-size: 14px;");

        // User type selection
        Label userTypeLabel = new Label("Login as:");
        ComboBox<String> userTypeCombo = new ComboBox<>();
        userTypeCombo.getItems().addAll("Administrator", "Teacher", "Student");
        userTypeCombo.setValue("Administrator");
        userTypeCombo.setStyle("-fx-font-size: 14px;");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), userTypeCombo.getValue()));

        // Status label for error messages
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: red;");

        // Sample credentials info
        VBox credentialsInfo = new VBox(5);
        credentialsInfo.setStyle("-fx-background-color: #e8f4fd; -fx-padding: 15; -fx-background-radius: 5;");
        Label credentialsTitle = new Label("Sample Login Credentials:");
        credentialsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label adminCred = new Label("Admin: admin / admin123");
        Label teacherCred = new Label("Teacher: teacher / teacher123");
        Label studentCred = new Label("Student: student / student123");
        credentialsInfo.getChildren().addAll(credentialsTitle, adminCred, teacherCred, studentCred);

        formBox.getChildren().addAll(
                loginLabel, usernameLabel, usernameField, passwordLabel, passwordField,
                userTypeLabel, userTypeCombo, loginButton, statusLabel
        );

        loginBox.getChildren().addAll(titleLabel, formBox, credentialsInfo);

        Scene scene = new Scene(new ScrollPane(loginBox), 800, 600);
        primaryStage.setScene(scene);

        // Store status label reference for error handling
        loginButton.setUserData(statusLabel);

        // Handle Enter key press
        passwordField.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), userTypeCombo.getValue()));
    }

    private void handleLogin(String username, String password, String userType) {
        Button loginButton = (Button) primaryStage.getScene().lookup(".button");
        Label statusLabel = (Label) loginButton.getUserData();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password");
            return;
        }

        User user = UserManager.authenticateUser(username, password, userType);
        if (user != null) {
            currentUser = user;
            statusLabel.setText("");
            showDashboard();
        } else {
            statusLabel.setText("Invalid credentials or user type");
        }
    }

    private void showDashboard() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Top menu bar
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Welcome panel
        VBox welcomePanel = new VBox(20);
        welcomePanel.setAlignment(Pos.CENTER);
        welcomePanel.setPadding(new Insets(50));

        Label welcomeLabel = new Label("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        welcomeLabel.setStyle("-fx-text-fill: #2c5aa0;");

        Label roleLabel = new Label("Role: " + currentUser.getRole());
        roleLabel.setFont(Font.font("Arial", 18));
        roleLabel.setStyle("-fx-text-fill: #666;");

        // Action buttons based on user role
        VBox actionButtons = createActionButtons();

        welcomePanel.getChildren().addAll(welcomeLabel, roleLabel, actionButtons);
        root.setCenter(welcomePanel);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> showLoginPage());
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            FileManager.saveAllData(administration);
            primaryStage.close();
        });
        fileMenu.getItems().addAll(logoutItem, new SeparatorMenuItem(), exitItem);

        // Data menu (Admin only)
        if ("Administrator".equals(currentUser.getRole())) {
            Menu dataMenu = new Menu("Data");
            MenuItem saveDataItem = new MenuItem("Save Data");
            saveDataItem.setOnAction(e -> {
                FileManager.saveAllData(administration);
                showAlert("Success", "Data saved successfully!");
            });
            MenuItem loadDataItem = new MenuItem("Load Data");
            loadDataItem.setOnAction(e -> {
                FileManager.loadAllData(administration);
                showAlert("Success", "Data loaded successfully!");
            });
            dataMenu.getItems().addAll(saveDataItem, loadDataItem);
            menuBar.getMenus().addAll(fileMenu, dataMenu);
        } else {
            menuBar.getMenus().add(fileMenu);
        }

        return menuBar;
    }

    private VBox createActionButtons() {
        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMaxWidth(400);

        String buttonStyle = "-fx-font-size: 14px; -fx-padding: 15 25; -fx-background-radius: 5;";

        if ("Administrator".equals(currentUser.getRole())) {
            Button viewStudentsBtn = new Button("View All Students");
            viewStudentsBtn.setStyle(buttonStyle + "-fx-background-color: #4CAF50; -fx-text-fill: white;");
            viewStudentsBtn.setOnAction(e -> administration.printAllStudents());

            Button viewTeachersBtn = new Button("View All Teachers");
            viewTeachersBtn.setStyle(buttonStyle + "-fx-background-color: #2196F3; -fx-text-fill: white;");
            viewTeachersBtn.setOnAction(e -> administration.printAllTeachers());

            Button viewCoursesBtn = new Button("View All Courses");
            viewCoursesBtn.setStyle(buttonStyle + "-fx-background-color: #FF9800; -fx-text-fill: white;");
            viewCoursesBtn.setOnAction(e -> administration.printAllCourses());

            buttonBox.getChildren().addAll(viewStudentsBtn, viewTeachersBtn, viewCoursesBtn);

        } else if ("Teacher".equals(currentUser.getRole())) {
            Button viewMyCoursesBtn = new Button("View My Courses");
            viewMyCoursesBtn.setStyle(buttonStyle + "-fx-background-color: #4CAF50; -fx-text-fill: white;");
            viewMyCoursesBtn.setOnAction(e -> {
                Teacher teacher = administration.findTeacher(currentUser.getId());
                if (teacher != null) {
                    teacher.printCourses();
                }
            });

            Button manageGradesBtn = new Button("Manage Grades");
            manageGradesBtn.setStyle(buttonStyle + "-fx-background-color: #2196F3; -fx-text-fill: white;");
            manageGradesBtn.setOnAction(e -> showAlert("Info", "Grade management feature coming soon!"));

            buttonBox.getChildren().addAll(viewMyCoursesBtn, manageGradesBtn);

        } else if ("Student".equals(currentUser.getRole())) {
            Button viewMyCoursesBtn = new Button("View My Courses");
            viewMyCoursesBtn.setStyle(buttonStyle + "-fx-background-color: #4CAF50; -fx-text-fill: white;");
            viewMyCoursesBtn.setOnAction(e -> {
                Student student = administration.findStudent(currentUser.getId());
                if (student != null) {
                    student.printCurrentCourses();
                    student.printCompletedCourses();
                }
            });

            Button enrollInCourseBtn = new Button("Enroll in Course");
            enrollInCourseBtn.setStyle(buttonStyle + "-fx-background-color: #2196F3; -fx-text-fill: white;");
            enrollInCourseBtn.setOnAction(e -> showAlert("Info", "Course enrollment feature coming soon!"));

            buttonBox.getChildren().addAll(viewMyCoursesBtn, enrollInCourseBtn);
        }

        return buttonBox;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}

// User class for authentication
class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String name;

    public User(int id, String username, String password, String role, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getName() { return name; }
}

// User management class
class UserManager {
    private static User[] users;

    public static void createSampleUsers() {
        users = new User[] {
                new User(9001, "admin", "admin123", "Administrator", "System Administrator"),
                new User(2001, "teacher", "teacher123", "Teacher", "Dr. Robert Smith"),
                new User(1001, "student", "student123", "Student", "Emily Johnson"),
                // Add more sample users as needed
                new User(2002, "teacher2", "teacher123", "Teacher", "Prof. Lisa Johnson"),
                new User(1002, "student2", "student123", "Student", "Michael Chen")
        };
    }

    public static User authenticateUser(String username, String password, String role) {
        if (users == null) createSampleUsers();

        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password) &&
                    user.getRole().equals(role)) {
                return user;
            }
        }
        return null;
    }
}