package org;

import com.mysql.jdbc.Driver;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Tour {

    // Tạo thành phần kết nối cơ sở dữ liệu
    public String connect = "jdbc:mysql://localhost:3306/tourmgr";
    public String username = "root";
    public String password = "";
    // Tạo thuộc tính kết nối
    public Connection connection;
    // Kiểm tra và thực hiện kết nối
    public Tour() {
        try {
            connection = DriverManager.getConnection(connect, username, password);
            Statement statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Thực hiện truy vấn thêm dữ liệu
    // Thực hiện truy vấn
    public void insertTour(String tour_code, String tour_name, String location, double price, int numdaystour, String start_date, String end_date, int status) {
        String sql = "INSERT INTO tours VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tour_code);
            statement.setString(2, tour_name);
            statement.setString(3, location);
            statement.setDouble(4, price);
            statement.setInt(5, numdaystour);
            statement.setString(6, start_date);
            statement.setString(7, end_date);
            statement.setInt(8, status);
            statement.executeUpdate();
            System.out.println("Add new tour successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Thêm bản ghi
    public void infoTour() {
        Scanner s = new Scanner(System.in);
        System.out.println("Input number of tour: ");
        int n = s.nextInt();
        s.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("Input tour number " + (i + 1) + ": ");
            System.out.print("Input tour code: ");
            String tour_code = s.nextLine();
            System.out.print("Input tour name: ");
            String tour_name = s.nextLine();
            System.out.print("Input tour location: ");
            String location = s.nextLine();
            System.out.print("Input tour price: ");
            double price = s.nextDouble();
            System.out.print("Input number of days in tour: ");
            int numdaystour = s.nextInt();
            s.nextLine();
            System.out.print("Input tour start date (yyyy-mm-dd): ");
            String start_date = s.nextLine();
            LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end_date = startDate.plusDays(numdaystour - 1);
            System.out.print("Input tour status: ");
            int status = s.nextInt();
            insertTour(tour_code, tour_name, location, price, numdaystour, startDate.toString(), end_date.toString(), status);
        }
    }

    public void editTour() {

    }

    public void deleteTour() {
        System.out.println("");
    }

    public void showTour() {
        try {
            connection = DriverManager.getConnection(connect, username, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tours");
            while (rs.next()) {
                System.out.println(
                        "ID"
                        + " " + "Tour name"
                        + " " + "Location"
                        + " " + "Price"
                        + " " + "Tour days"
                        + " " + "Start date"
                        + " " + "End date"
                        + " " + "Status"
                );
                System.out.println(
                        rs.getString(1)
                        + " " + rs.getString(2)
                        + " " + rs.getString(3)
                        + " " + rs.getDouble(4)
                        + " " + rs.getInt(5)
                        + " " + rs.getDate(6)
                        + " " + rs.getDate(7)
                        + " " + rs.getInt(8)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tourManagement() {
        int choice;
        do {
            Scanner s = new Scanner(System.in);
            System.out.println("===== Tour Management =====");
            System.out.println("1. Add a new tour");
            System.out.println("2. Update information");
            System.out.println("3. Delete tour");
            System.out.println("4. Show tour list");
            System.out.println("0. Return to main menu");
            System.out.print("Please choose: ");
            choice = s.nextInt();
            switch(choice) {
                case 1:
                    infoTour();
                case 2:
                    editTour();
                case 3:
                    deleteTour();
                case 4:
                    showTour();

            }
        } while(choice != 0);
    }
}
