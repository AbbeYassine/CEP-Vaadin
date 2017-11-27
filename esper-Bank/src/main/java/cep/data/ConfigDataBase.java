package cep.data;


import cep.event.LoanEvent;

import java.sql.*;

/**
 * Created by Abbes on 27/11/2017.
 */
public class ConfigDataBase {
    static Connection connectionData = null;
    static PreparedStatement connectionPrepareData = null;

    /*public static void main(String[] argv) {

        try {
            log("-------- Simple Crunchify Tutorial on how to make JDBC connection to MySQL DB locally on macOS ------------");
            makeJDBCConnection();

            log("\n---------- Adding company 'Crunchify LLC' to DB ----------");
            addDataToDB("Crunchify, LLC.", "NYC, US", 5, "http://crunchify.com");
            addDataToDB("Google Inc.", "Mountain View, CA, US", 50000, "https://google.com");
            addDataToDB("Apple Inc.", "Cupertino, CA, US", 30000, "http://apple.com");

            log("\n---------- Let's get Data from DB ----------");
            getDataFromDB();

            crunchifyPrepareStat.close();
            crunchifyConn.close(); // connection close

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }*/

    public static Connection getConnection() {
        if (connectionData == null) {
            makeJDBCConnection();
        }
        return connectionData;
    }

    public static void closeConnection() {
        if (connectionData != null) {
            try {
                connectionData.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void makeJDBCConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            log("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            connectionData = DriverManager.getConnection("jdbc:mysql://localhost:3306/CepDB", "root", "");
            if (connectionData != null) {
                log("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    public static void addDataToDB(LoanEvent event) {

        try {
            String insertQueryStatement = "INSERT  INTO  loan_event (applicant,loan_sum,time_of_reading)  VALUES  (?,?,?)";

            java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(event.getTimeOfReading().getTime());
            connectionPrepareData = connectionData.prepareStatement(insertQueryStatement);
            connectionPrepareData.setString(1, event.getApplicant());
            connectionPrepareData.setDouble(2, event.getLoanSum());
            connectionPrepareData.setTimestamp(3, sqlDateTime);


            // execute insert SQL statement
            connectionPrepareData.executeUpdate();
            log("event added successfully");
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

   /* private static void getDataFromDB() {

        try {
            // MySQL Select Query Tutorial
            String getQueryStatement = "SELECT * FROM employee";

            crunchifyPrepareStat = crunchifyConn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = crunchifyPrepareStat.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int employeeCount = rs.getInt("EmployeeCount");
                String website = rs.getString("Website");

                // Simply Print the results
                System.out.format("%s, %s, %s, %s\n", name, address, employeeCount, website);
            }

        } catch (

                SQLException e) {
            e.printStackTrace();
        }

    }*/

    // Simple log utility
    private static void log(String string) {
        System.out.println(string);

    }
}
