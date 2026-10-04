package Bus_Reservation_System;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
	
	static final String URL="jdbc:mysql://localhost:3306/Bus_Reservation_System";
	static final String USERNAME="root";
	static final String PASSWORD="YOUR_PASSWORD";
	
	public static Connection getconnection() throws Exception{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		
		return con;
	}
	

}
