/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oops;   

/**
 *
 * @author Rashmit Mhatre
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertImage {

    public static void main(String[] args) throws IOException {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "root";

        // Image file path
        String imagePath = "C:\\Users\\Rashmit Mhatre\\Downloads\\viratDB.jpg";

        // SQL query to insert image
        String sql = "INSERT INTO images (project_id, pic, name, description, raised_amount, target) VALUES (?, ?, 'Blood Donation', 'This is just for trial purpose', 0, 100)";

        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
                FileInputStream fis = new FileInputStream(imagePath);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set project_id parameter
            pstmt.setInt(1, 1); // Assuming project_id is an integer and set to 1

            // Set pic parameter (image data)
            pstmt.setBlob(2, fis);

            // Execute the query
            pstmt.executeUpdate();
            System.out.println("Image inserted successfully.");

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
