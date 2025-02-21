package org;

import com.mysql.cj.jdbc.StatementWrapper;
import com.mysql.jdbc.Driver;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Tour {

    Scanner s = new Scanner(System.in);

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
    public void insertTour(int tour_code, String tour_name, String location, double price, int numdaystour, String start_date, String end_date, int status) {
        String sql = "INSERT INTO tours VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tour_code);
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
    public void addTour() {
        System.out.print("Input number of tour: ");
        int n = s.nextInt();
        s.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("=== Input tour number " + (i + 1) + " ===");
            System.out.print("Input tour code: ");
            int tour_code = s.nextInt();
            s.nextLine();
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
            LocalDate endDate = startDate.plusDays(numdaystour - 1);
            System.out.print("Input tour status: ");
            int status = s.nextInt();
            insertTour(tour_code, tour_name, location, price, numdaystour, startDate.toString(), endDate.toString(), status);
        }
    }

    // Sửa
    public void editTour() {
        System.out.println("===== UPDATE TOUR =====");
        try {
            // Thiết lập kết nối đến database
            connection = DriverManager.getConnection(connect, username, password);

            // Hiển thị danh sách
            showTour();

            // Chọn id muốn sửa
            System.out.print("Enter tour id you want to edit: ");
            int edit_tour = s.nextInt();
            s.nextLine();

            // Nhập thông tin mới
            System.out.print("Input tour name: ");
            String new_tour_name = s.nextLine();
            System.out.print("Input tour location: ");
            String new_location = s.nextLine();
            System.out.print("Input tour price: ");
            double new_price = s.nextDouble();
            System.out.print("Input number of days in tour: ");
            int new_numdaystour = s.nextInt();
            s.nextLine();
            System.out.print("Input tour start date (yyyy-mm-dd): ");
            String new_start_date = s.nextLine();
            LocalDate new_startDate = LocalDate.parse(new_start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate new_endDate = new_startDate.plusDays(new_numdaystour - 1);
            System.out.print("Input tour status: ");
            int new_status = s.nextInt();

            // Câu lệnh SQL
            String sql = "UPDATE tours SET tour_name = ?, location = ?, price = ?, numdaystour = ?, start_date = ?, end_date = ?, status = ? WHERE tour_code = " + edit_tour;

            // Cập nhật dữ liệu trong database
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, new_tour_name);
            statement.setString(2, new_location);
            statement.setDouble(3, new_price);
            statement.setInt(4, new_numdaystour);
            statement.setString(5, new_startDate.toString());
            statement.setString(6, new_endDate.toString());
            statement.setInt(7, new_status);

            // Thực thi UPDATE
            int rows_affected = statement.executeUpdate();

            // Kiểm tra kết quả
            if (rows_affected > 0) {
                System.out.println("Update completed!");
            } else {
                System.out.println("Cannot find any tour with id: " + edit_tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Xoá
    public void deleteTour() {
        System.out.println("===== DELETE TOUR =====");
        showTour();
        try {
            connection = DriverManager.getConnection(connect, username, password);
            PreparedStatement statement = null;

            System.out.print("Enter tour id you want to delete: ");
            int del_tour = s.nextInt();
            String sql = "DELETE FROM tours WHERE tour_code = ?";
            statement = connection.prepareCall(sql);
            statement.setInt(1, del_tour);
            statement.execute();
            System.out.println("Tour deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Hiển thị
    public void showTour() {
        System.out.println("===== TOUR LIST =====");
        try {
            connection = DriverManager.getConnection(connect, username, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tours");
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1)
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

    // Menu
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
                    addTour();
                    break;
                case 2:
                    editTour();
                    break;
                case 3:
                    deleteTour();
                    break;
                case 4:
                    showTour();
                    break;

            }
        } while(choice != 0);
    }
}
