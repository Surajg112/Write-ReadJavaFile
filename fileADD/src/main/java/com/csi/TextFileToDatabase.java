package com.csi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TextFileToDatabase {

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fileadd";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    // Method to read data from the text file and insert into the database
    public static void insertDataFromFile() {
        String filePath = "C:\\Users\\DELL\\eclipse-workspace\\fileADD\\src\\main\\java\\com\\csi\\data.txt";
        List<String[]> dataRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                dataRecords.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String insertQuery = "INSERT INTO abc (name, age, country) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            for (String[] record : dataRecords) {
                pstmt.setString(1, record[0]);
                pstmt.setInt(2, Integer.parseInt(record[1]));
                pstmt.setString(3, record[2]);
                pstmt.executeUpdate();
            }
            System.out.println("Data inserted into the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to read data from the database and display it
    public static void readDataFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String selectQuery = "SELECT * FROM abc";
            PreparedStatement pstmt = conn.prepareStatement(selectQuery);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String country = resultSet.getString("country");

                System.out.println(name + ", " + age + ", " + country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Insert data from the text file into the database
        insertDataFromFile();

        // Read and display data from the database
        readDataFromDatabase();
    }
}
