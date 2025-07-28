import java.sql.*;
import java.util.*;

public class StudentInformationSystem {
    static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true";
    static final String USER = "root"; // your DB username
    static final String PASS = "Ramya@1121"; // your DB password

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to database!");

            while (true) {
                System.out.println("\n===== Student Information System =====");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewStudents();
                        break;
                    case 3:
                        conn.close();
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection failed:");
            e.printStackTrace();
        }
    }

    public static void addStudent() {
        try {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter department: ");
            String dept = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            System.out.print("Enter mobile number: ");
            String mobile = sc.nextLine();

            System.out.print("Enter gender: ");
            String gender = sc.nextLine();

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO students(name, age, department, email, mobile, gender, address) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, dept);
            ps.setString(4, email);
            ps.setString(5, mobile);
            ps.setString(6, gender);
            ps.setString(7, address);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding student:");
            e.printStackTrace();
        }
    }

    public static void viewStudents() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.println("ID        : " + rs.getInt("id"));
                System.out.println("Name      : " + rs.getString("name"));
                System.out.println("Age       : " + rs.getInt("age"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Email     : " + rs.getString("email"));
                System.out.println("Mobile    : " + rs.getString("mobile"));
                System.out.println("Gender    : " + rs.getString("gender"));
                System.out.println("Address   : " + rs.getString("address"));
                System.out.println("------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching students:");
            e.printStackTrace();
        }
    }
}
