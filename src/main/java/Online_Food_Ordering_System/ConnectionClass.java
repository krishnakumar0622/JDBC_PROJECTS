package Online_Food_Ordering_System;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionClass {
	
     final static String URL="jdbc:mysql://localhost:3306/Online_Food_Ordering_System";
     final static String USERNAME="root";
     final static String PASSWORD="YOUR_PASSWORD";
     
     
     
     public static Connection getConnection() throws  Exception{
    	 
    	 
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	 
    	 Connection con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
    	 
    	 return con;
     }

}
