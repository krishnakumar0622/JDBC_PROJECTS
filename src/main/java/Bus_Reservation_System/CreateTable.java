package Bus_Reservation_System;


import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
	Connection con;
	
	CreateTable(Connection con){
		this.con=con;
		
	}
	
	
	public void createBusTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS buses ("
	            + " bus_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " bus_name VARCHAR(50) NOT NULL,"
	            + " source VARCHAR(50) NOT NULL,"
	            + " destination VARCHAR(50) NOT NULL,"
	            + " total_seats INT NOT NULL,"
	            + " available_seats INT NOT NULL"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Bus table created successfully!");
	}
	
	public void createPassengerTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS  passengers ("
	            + " passenger_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " passenger_name VARCHAR(50) NOT NULL,"
	            + " passenger_age INT NOT NULL,"
	            + " passenger_phone VARCHAR(15) NOT NULL"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Passenger table created successfully!");
	}
	
	public void createBookingTable() throws Exception {

	    String sql = "CREATE TABLE IF NOT EXISTS  bookings ("
	            + " booking_id INT PRIMARY KEY AUTO_INCREMENT,"
	            + " bus_id INT NOT NULL,"
	            + " passenger_id INT NOT NULL,"
	            + " seat_number INT NOT NULL,"
	            + " booking_date DATE NOT NULL,"
	            + " status VARCHAR(20) DEFAULT 'BOOKED',"
	            + " FOREIGN KEY (bus_id) REFERENCES buses(bus_id),"
	            + " FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id)"
	            + ")";

	    Statement std = con.createStatement();

	    std.executeUpdate(sql);

	    System.out.println("Booking table created successfully!");
	}
	

}
