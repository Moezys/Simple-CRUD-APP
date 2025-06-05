package com.mycompany.contacts;

import java.sql.*;
import java.util.Scanner;

public class ContactManager {
    private static final String JDBC_URL  = "jdbc:mysql://localhost:3306/qa_contacts";
    private static final String DB_USER   = "you_db_user";
    private static final String DB_PASS   = "your_db_password";
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            runMenu();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    private static void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Contact Manager ===");
            System.out.println("1) List all contacts");
            System.out.println("2) Add new contact");
            System.out.println("3) Update contact");
            System.out.println("4) Delete contact");
            System.out.println("5) Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    listContacts();
                    break;
                case 2:
                    addContact(scanner);
                    break;
                case 3:
                    updateContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    conn.close();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void listContacts() throws SQLException {
        String sql = "SELECT * FROM contacts";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("\nID | Name               | Phone         | Email");
        System.out.println("------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-3d| %-18s| %-13s| %s%n",
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("email")
            );
        }
        rs.close();
    }

    private static void addContact(Scanner scanner) throws SQLException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        String sql = "INSERT INTO contacts (name, phone, email) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, phone);
        ps.setString(3, email);
        ps.executeUpdate();
        System.out.println("Contact added.");
    }

    private static void updateContact(Scanner scanner) throws SQLException {
        System.out.print("Enter contact ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New phone (leave blank to skip): ");
        String phone = scanner.nextLine();
        System.out.print("New email (leave blank to skip): ");
        String email = scanner.nextLine();

        if (!phone.isEmpty()) {
            String sql = "UPDATE contacts SET phone = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Phone updated.");
        }
        if (!email.isEmpty()) {
            String sql = "UPDATE contacts SET email = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Email updated.");
        }
    }

    private static void deleteContact(Scanner scanner) throws SQLException {
        System.out.print("Enter contact ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        String sql = "DELETE FROM contacts WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Contact deleted.");
    }
}
