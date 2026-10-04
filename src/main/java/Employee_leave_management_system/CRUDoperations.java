package Employee_leave_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class CRUDoperations {
	
	
	
	
	

	

	

	    Connection con;
	    Scanner sc;

	    public CRUDoperations(Connection con, Scanner sc) {
	        this.con = con;
	        this.sc = sc;
	    }

	    // 1. Register Employee
	    public void registerEmployee() throws Exception {

	        System.out.print("Enter employee name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter employee email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter employee department: ");
	        String department = sc.nextLine();

	        String employeesql = "INSERT INTO employee " +
	                "(employee_name, employee_email, employee_dept) " +
	                "VALUES (?, ?, ?)";

	        PreparedStatement ps = con.prepareStatement(employeesql,Statement.RETURN_GENERATED_KEYS);
	        

	        ps.setString(1, name);
	        ps.setString(2, email);
	        ps.setString(3, department);
	        ps.executeUpdate();
	        
	       ResultSet rs=ps.getGeneratedKeys();
	       
	       if (rs.next()) {

	           int employeeId = rs.getInt(1);

	           // Create leave balance
	           String balanceSql =
	                   "INSERT INTO leave_balance (employee_id) " +
	                   "VALUES (?)";

	           PreparedStatement balancePs =
	                   con.prepareStatement(balanceSql);

	           balancePs.setInt(1, employeeId);

	           balancePs.executeUpdate();

	           System.out.println("Employee registered successfully!");
	           System.out.println("Leave balance created successfully!");
	           System.out.println("Total Leaves: 20");
	           System.out.println("Used Leaves: 0");
	           System.out.println("Remaining Leaves: 20");
	       }
	       
	       
	       

	  

	      
	    }


	    // 2. Display Employees
	    public void displayEmployees() throws Exception {

	        String sql = "SELECT * FROM employee";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        System.out.println("\n------ Employee Details ------");

	        while (rs.next()) {

	            System.out.println(
	                    "ID: " + rs.getInt("employee_id") +
	                    " | Name: " + rs.getString("employee_name") +
	                    " | Email: " + rs.getString("employee_email") +
	                    " | Department: " + rs.getString("employee_dept")
	            );
	        }
	    }


	    // 3. Update Employee
	    public void updateEmployee() throws Exception {

	        System.out.print("Enter employee ID to update: ");
	        int id = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter new email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter new department: ");
	        String department = sc.nextLine();

	        String sql = "UPDATE employee " +
	                "SET employee_name = ?, " +
	                "employee_email = ?, " +
	                "employee_dept = ? " +
	                "WHERE employee_id = ?";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, name);
	        ps.setString(2, email);
	        ps.setString(3, department);
	        ps.setInt(4, id);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Employee updated successfully!");
	        } else {
	            System.out.println("Employee ID not found!");
	        }
	    }


	    // 4. Delete Employee
	    public void deleteEmployee() throws Exception {

	        System.out.print("Enter employee ID to delete: ");
	        int id = sc.nextInt();
	        sc.nextLine();

	        String sql = "DELETE FROM employee WHERE employee_id = ?";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, id);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Employee deleted successfully!");
	        } else {
	            System.out.println("Employee ID not found!");
	        }
	    }


	    // 5. Apply Leave
	    public void applyLeave() throws Exception {

	        System.out.print("Enter employee ID: ");
	        int employeeId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter leave type: ");
	        String leaveType = sc.nextLine();

	        System.out.print("Enter from date (YYYY-MM-DD): ");
	        String fromDate = sc.nextLine();

	        System.out.print("Enter to date (YYYY-MM-DD): ");
	        String toDate = sc.nextLine();

	        System.out.print("Enter reason: ");
	        String reason = sc.nextLine();

	        String sql = "INSERT INTO leave_requests " +
	                "(employee_id, leave_type, from_date, to_date, reason) " +
	                "VALUES (?, ?, ?, ?, ?)";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, employeeId);
	        ps.setString(2, leaveType);
	        ps.setString(3, fromDate);
	        ps.setString(4, toDate);
	        ps.setString(5, reason);

	        ps.executeUpdate();

	        System.out.println("Leave applied successfully!");
	    }


	    // 8. Check Leave Balance
	    public void checkLeaveBalance() throws Exception {

	        System.out.print("Enter employee ID: ");
	        int employeeId = sc.nextInt();
	        sc.nextLine();

	        String sql = "SELECT * FROM leave_balance " +
	                "WHERE employee_id = ?";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, employeeId);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            System.out.println("\n------ Leave Balance ------");

	            System.out.println("Employee ID: "
	                    + rs.getInt("employee_id"));

	            System.out.println("Total Leaves: "
	                    + rs.getInt("total_leaves"));

	            System.out.println("Used Leaves: "
	                    + rs.getInt("used_leaves"));

	            System.out.println("Remaining Leaves: "
	                    + rs.getInt("remaining_leaves"));

	        } else {

	            System.out.println("Leave balance not found!");
	        }
	    }
	    
	    
	    
	    public void viewMyLeaveRequests() throws Exception {

	        System.out.print("Enter employee ID: ");
	        int employeeId = sc.nextInt();
	        sc.nextLine();

	        String sql = "SELECT lr.*, e.employee_name "
	        		+ " FROM leave_requests lr "
	        		+ " JOIN employee e "
	        		+ " ON lr.employee_id = e.employee_id "
	        		+ " WHERE lr.employee_id = ? ";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, employeeId);

	        ResultSet rs = ps.executeQuery();

	        System.out.println("\n========== MY LEAVE REQUESTS ==========");

	        boolean found = false;

	        while (rs.next()) {

	            found = true;
                System.out.println("Employee name :"+rs.getString("employee_name"));
                System.out.println("Employee ID :"+rs.getInt("employee_id"));
	            System.out.println("Leave ID: " + rs.getInt("leave_id"));
	            System.out.println("Leave Type: " + rs.getString("leave_type"));
	            System.out.println("From Date: " + rs.getDate("from_date"));
	            System.out.println("To Date: " + rs.getDate("to_date"));
	            System.out.println("Reason: " + rs.getString("reason"));
	            System.out.println("Status: " + rs.getString("status"));

	            System.out.println("--------------------------------------");
	        }

	        if (!found) {
	            System.out.println("No leave requests found!");
	        }
	    }
	    
	    
	    public void viewPendingLeaveRequests() throws Exception {

	        String sql = "SELECT * FROM leave_requests WHERE status = 'PENDING'";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        System.out.println("\n========== PENDING LEAVE REQUESTS ==========");

	        boolean found = false;

	        while (rs.next()) {

	            found = true;

	            System.out.println("Leave ID: " + rs.getInt("leave_id"));
	            System.out.println("Employee ID: " + rs.getInt("employee_id"));
	            System.out.println("Leave Type: " + rs.getString("leave_type"));
	            System.out.println("From Date: " + rs.getDate("from_date"));
	            System.out.println("To Date: " + rs.getDate("to_date"));
	            System.out.println("Reason: " + rs.getString("reason"));
	            System.out.println("Status: " + rs.getString("status"));

	            System.out.println("--------------------------------------");
	        }

	        if (!found) {
	            System.out.println("No pending leave requests!");
	        }
	    }
	}


