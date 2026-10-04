package Employee_leave_management_system;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
	
	
	
	Connection con;
	
	
	public CreateTable(Connection con) {
		this.con=con;
	}
	
	
	public void createEmployeeTable() throws Exception{
		String sql="CREATE TABLE IF NOT EXISTS employee("+"employee_id int primary key auto_increment,"
	                +"employee_name varchar(50) not null,"+"employee_email varchar(50)  not null,"+
				    "employee_dept varchar(40) not null)";
				    		Statement std=con.createStatement();
				    		
				    		std.executeUpdate(sql);
				    		
				    		System.out.println("employee table created successfully");
				    		
	} 
	
    public void createLeaveRequestTable() throws Exception {

        String sql = "CREATE TABLE IF NOT EXISTS leave_requests (" +
                "leave_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "employee_id INT NOT NULL, " +
                "leave_type VARCHAR(30) NOT NULL, " +
                "from_date DATE NOT NULL, " +
                "to_date DATE NOT NULL, " +
                "reason VARCHAR(200), " +
                "status VARCHAR(20) DEFAULT 'PENDING', " +
                "FOREIGN KEY (employee_id) REFERENCES employee(employee_id)" +
                ")";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);

        System.out.println("Leave requests table created");
    }
    
    
    
    public void createLeaveBalanceTable() throws Exception {

    	 String sql = "CREATE TABLE IF NOT EXISTS leave_balance (" +
    	            "balance_id INT PRIMARY KEY AUTO_INCREMENT, " +
    	            "employee_id INT NOT NULL, " +
    	            "total_leaves INT NOT NULL DEFAULT 20, " +
    	            "used_leaves INT NOT NULL DEFAULT 0, " +
    	            "remaining_leaves INT NOT NULL DEFAULT 20, " +
    	            "FOREIGN KEY (employee_id) REFERENCES employee(employee_id)" +
    	            ")";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);

        System.out.println("Leave balance table created");
    }
	
	

	
	
	
	
	
}
