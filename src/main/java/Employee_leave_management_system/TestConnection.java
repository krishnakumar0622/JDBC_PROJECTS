package Employee_leave_management_system;

import java.sql.Connection;

public class TestConnection {
	
	
	public static void main(String[] args) {
	    try {

            Connection con = ConnectionClass.getConnection();

            if (con != null) {
                System.out.println("Database connected successfully!");
            }
            
            CreateTable table=new CreateTable(con);
            table.createEmployeeTable();
            table.createLeaveRequestTable();
            table.createLeaveBalanceTable();
           
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
	    
	    
	 
	    
    }
	}


