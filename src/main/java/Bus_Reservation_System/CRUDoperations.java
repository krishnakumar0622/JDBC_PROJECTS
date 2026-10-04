package Bus_Reservation_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CRUDoperations {
	
	Connection con;
	Scanner sc;
    CRUDoperations(Connection con,Scanner sc){
    	this.con=con;
    	this.sc=sc;
    }
    
    
    public void SearchBuses() throws Exception {

        System.out.println("Enter the source");
        String source = sc.next();

        System.out.println("Enter the destination");
        String destination = sc.next();

        String sql = "SELECT * FROM buses "
                + "WHERE source = ? AND destination = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, source);
        ps.setString(2, destination);

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.println("------------------------------");
            System.out.println("Bus ID: "
                    + rs.getInt("bus_id"));

            System.out.println("Bus Name: "
                    + rs.getString("bus_name"));

            System.out.println("Source: "
                    + rs.getString("source"));

            System.out.println("Destination: "
                    + rs.getString("destination"));

            System.out.println("Total Seats: "
                    + rs.getInt("total_seats"));

            System.out.println("Available Seats: "
                    + rs.getInt("available_seats"));

            System.out.println("------------------------------");
        }

        if (!found) {
            System.out.println("No buses found!");
        }
    }
    
    
    public void registerPassenger() throws Exception {

        System.out.println("Enter passenger name");
        String passengerName = sc.nextLine();

        System.out.println("Enter passenger age");
        int passengerAge = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter passenger phone");
        String passengerPhone = sc.nextLine();

        String sql = "INSERT INTO passengers "
                + "(passenger_name, passenger_age, passenger_phone) "
                + "VALUES (?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, passengerName);
        ps.setInt(2, passengerAge);
        ps.setString(3, passengerPhone);

        ps.executeUpdate();

        System.out.println("Passenger registered successfully!");
    }
    
    
    
    public void displayPassengers() throws Exception {

        String sql = "SELECT * FROM passengers";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.println("------------------------------");

            System.out.println("Passenger ID: "
                    + rs.getInt("passenger_id"));

            System.out.println("Passenger Name: "
                    + rs.getString("passenger_name"));

            System.out.println("Passenger Age: "
                    + rs.getInt("passenger_age"));

            System.out.println("Passenger Phone: "
                    + rs.getString("passenger_phone"));

            System.out.println("------------------------------");
        }

        if (!found) {
            System.out.println("No passengers found!");
        }
    }
    
    
    public void displayBookings() throws Exception {

        String sql = "SELECT b.booking_id, "
                + "p.passenger_name, "
                + "b.bus_id, "
                + "bs.bus_name, "
                + "bs.source, "
                + "bs.destination, "
                + "b.seat_number, "
                + "b.booking_date, "
                + "b.status "
                + "FROM bookings b "
                + "JOIN passengers p "
                + "ON b.passenger_id = p.passenger_id "
                + "JOIN buses bs "
                + "ON b.bus_id = bs.bus_id";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        boolean found = false;

        while (rs.next()) {

            found = true;

            System.out.println("--------------------------------");

            System.out.println("Booking ID: "
                    + rs.getInt("booking_id"));

            System.out.println("Passenger Name: "
                    + rs.getString("passenger_name"));

            System.out.println("Bus ID: "
                    + rs.getInt("bus_id"));

            System.out.println("Bus Name: "
                    + rs.getString("bus_name"));

            System.out.println("Source: "
                    + rs.getString("source"));

            System.out.println("Destination: "
                    + rs.getString("destination"));

            System.out.println("Seat Number: "
                    + rs.getInt("seat_number"));

            System.out.println("Booking Date: "
                    + rs.getDate("booking_date"));

            System.out.println("Status: "
                    + rs.getString("status"));

            System.out.println("--------------------------------");
        }

        if (!found) {
            System.out.println("No bookings found!");
        }
    }
	

}
