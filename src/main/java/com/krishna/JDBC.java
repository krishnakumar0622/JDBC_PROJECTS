package com.krishna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBC {
	
	
	
	

	    static final String url = "jdbc:mysql://localhost:3306/college";
	    static final String user = "root";
	    static final String pass = "Krishna@123";

	    static Connection con;

	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);
	        int ch;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            con = DriverManager.getConnection(url, user, pass);

	            do {

	                System.out.println("1.Add");
	                System.out.println("2.Read");
	                System.out.println("3.Update");
	                System.out.println("4.Delete");
	                System.out.println("5.Exit");
	                System.out.println("6.Fetch student by email");
	                System.out.println("Enter the Choice");

	                ch = sc.nextInt();

	                switch (ch) {

	                case 1:
	                    addrecord(sc);
	                    break;

	                case 2:
	                    readrecord();
	                    break;

	                case 3:
	                    updaterecord(sc);
	                    break;

	                case 4:
	                    deleterecord(sc);
	                    break;

	                case 5:
	                    System.out.println("Exit...");
	                    break;

	                case 6:
	                    fetchByEmail(sc);
	                    break;

	                default:
	                    System.out.println("Invalid choice");
	                }

	            } while (ch != 5);

	        } catch (Exception e) {

	            e.printStackTrace();

	        }

	    }


	    // ADD RECORD
	    public static void addrecord(Scanner sc) throws SQLException {

	        sc.nextLine();

	        System.out.println("Enter the name:");
	        String sname = sc.nextLine();

	        System.out.println("Enter the email:");
	        String semail = sc.nextLine();

	        String insert = "insert into Student(sname,semail) values (?,?)";

	        PreparedStatement ps = con.prepareStatement(insert);

	        ps.setString(1, sname);
	        ps.setString(2, semail);

	        ps.executeUpdate();

	        System.out.println("Record Added Successfully");

	    }


	    // READ RECORDS
	    public static void readrecord() throws SQLException {

	        String fetch = "select * from Student";

	        Statement st = con.createStatement();

	        ResultSet rs = st.executeQuery(fetch);

	        System.out.println("Student Records:");

	        while (rs.next()) {

	            System.out.println(
	                    rs.getInt("sid") + " | "
	                    + rs.getString("sname") + " | "
	                    + rs.getString("semail")
	            );
	        }

	    }


	    // UPDATE RECORD
	    public static void updaterecord(Scanner sc) throws SQLException {

	        System.out.println("Enter the student id to update:");

	        int sid = sc.nextInt();

	        sc.nextLine();

	        System.out.println("Enter the new Email:");

	        String semail = sc.nextLine();

	        String update = "update Student set semail=? where id=?";

	        PreparedStatement ps = con.prepareStatement(update);

	        ps.setString(1, semail);
	        ps.setInt(2, sid);

	        int row = ps.executeUpdate();

	        if (row > 0) {

	            System.out.println("Student record updated");

	        } else {

	            System.out.println("Record not found");

	        }

	    }


	    // DELETE RECORD
	    public static void deleterecord(Scanner sc) throws SQLException {

	        System.out.println("Enter the id to delete:");

	        int sid = sc.nextInt();

	        String delete = "delete from Student where sid=?";

	        PreparedStatement ps = con.prepareStatement(delete);

	        ps.setInt(1, sid);

	        int row = ps.executeUpdate();

	        if (row > 0) {

	            System.out.println("Record deleted successfully");

	        } else {

	            System.out.println("No record found");

	        }

	    }

	    public static void fetchByEmail(Scanner sc) throws SQLException {

	        sc.nextLine();

	        System.out.println("Enter the student email:");

	        String semail = sc.nextLine();

	        String fetch = "select * from Student where semail=?";

	        PreparedStatement ps = con.prepareStatement(fetch);

	        ps.setString(1, semail);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            System.out.println(
	                    rs.getInt("sid") + " | "
	                    + rs.getString("sname") + " | "
	                    + rs.getString("semail")
	            );

	        } else {

	            System.out.println("Record not found");

	        }

	    }

	}


