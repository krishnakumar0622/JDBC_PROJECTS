package Employee_leave_management_system;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
	

	static final String URL="jdbc:mysql://localhost:3306/Employee_leave_management";
	 static final String USERNAME="root";
	  static final String PASSWORD="YOUR_PASSWORD";
	
	public static Connection getConnection() throws Exception{
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		
		
		return con;
		
	}

}
