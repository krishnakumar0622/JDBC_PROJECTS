package Employee_leave_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Transcation_Management {
	
	
	Connection con;
	Scanner sc;
	
	public Transcation_Management(Connection con,Scanner sc){
		this.con=con;
		this.sc=sc;
	}
	
	public void approveleave() throws Exception
	{
		
		System.out.println("enter the leave id to approve");
		int leave_id=sc.nextInt();
		sc.nextLine();
		
		try { 
			con.setAutoCommit(false);
			
			String sql=   "SELECT employee_id, from_date, to_date, status " +
                    "FROM leave_requests " +
                    "WHERE leave_id = ?";
			
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1,leave_id);
			
			ResultSet rs=ps.executeQuery();
			
			if(!rs.next()) {
				System.out.println("leave request not found");
				con.rollback();
				return;
			}
			
			int employeeid=rs.getInt("employee_id");
			
			java.sql.Date fromdate=rs.getDate("from_date");
			java.sql.Date todate=rs.getDate("to_date");
			
			String status=rs.getString("status");
			
			  String daysSql =
	                    "SELECT DATEDIFF(?, ?) + 1";


	            PreparedStatement daysPs =
	                    con.prepareStatement(daysSql);


	            daysPs.setDate(1, todate);
	            daysPs.setDate(2, fromdate);


	            ResultSet daysRs =
	                    daysPs.executeQuery();


	            daysRs.next();


	            int leaveDays =
	                    daysRs.getInt(1);


	            System.out.println(
	                    "Leave Days: " + leaveDays
	            );


	            // Get employee leave balance
	            String balanceSql =
	                    "SELECT remaining_leaves " +
	                    "FROM leave_balance " +
	                    "WHERE employee_id = ?";


	            PreparedStatement balancePs =
	                    con.prepareStatement(balanceSql);


	            balancePs.setInt(1, employeeid);


	            ResultSet balanceRs =
	                    balancePs.executeQuery();


	            if (!balanceRs.next()) {

	                System.out.println(
	                        "Leave balance not found!"
	                );

	                con.rollback();

	                return;
	            }


	            int remainingLeaves =
	                    balanceRs.getInt("remaining_leaves");


	            System.out.println(
	                    "Remaining Leaves: " + remainingLeaves
	            );


	            // Check leave balance
	            if (remainingLeaves < leaveDays) {

	                System.out.println(
	                        "Insufficient leave balance!"
	                );

	                con.rollback();

	                return;
	            }


	            // Update leave status
	            String updateLeave =
	                    "UPDATE leave_requests " +
	                    "SET status = 'APPROVED' " +
	                    "WHERE leave_id = ?";


	            PreparedStatement updateLeavePs =
	                    con.prepareStatement(updateLeave);


	            updateLeavePs.setInt(1, leave_id);


	            updateLeavePs.executeUpdate();


	            // Update leave balance
	            String updateBalance =
	                    "UPDATE leave_balance " +
	                    "SET used_leaves = used_leaves + ?, " +
	                    "remaining_leaves = remaining_leaves - ? " +
	                    "WHERE employee_id = ?";


	            PreparedStatement updateBalancePs =
	                    con.prepareStatement(updateBalance);


	            updateBalancePs.setInt(1, leaveDays);
	            updateBalancePs.setInt(2, leaveDays);
	            updateBalancePs.setInt(3, employeeid);


	            updateBalancePs.executeUpdate();


	            // Both updates successful
	            con.commit();


	            System.out.println(
	                    "Leave approved successfully!"
	            );

	            System.out.println(
	                    "Leave balance updated successfully!"
	            );

			
			
			
			
		}
		catch(Exception e) {
			con.rollback();
			
			   System.out.println(
	                    "Transaction failed!"
	            );

	            System.out.println(
	                    "All changes have been rolled back."
	            );
	            
			e.printStackTrace();
			
		}
		finally {
			
			con.setAutoCommit(true);
		}
		
	}
	
	
	public void rejectleave() throws Exception {

	    System.out.println("Enter the leave id to reject");

	    int leave_id = sc.nextInt();
	    sc.nextLine();

	    try {

	      
	        con.setAutoCommit(false);

	      
	        String sql =
	                "SELECT status " +
	                "FROM leave_requests " +
	                "WHERE leave_id = ?";

	        PreparedStatement ps =
	                con.prepareStatement(sql);

	        ps.setInt(1, leave_id);

	        ResultSet rs =
	                ps.executeQuery();

	      
	        if (!rs.next()) {

	            System.out.println("Leave request not found");

	            con.rollback();

	            return;
	        }

	     
	        String status =
	                rs.getString("status");

	       
	        if (!status.equals("PENDING")) {

	            System.out.println(
	                    "Leave request is already " + status
	            );

	            con.rollback();

	            return;
	        }

	       
	        String updateSql =
	                "UPDATE leave_requests " +
	                "SET status = 'REJECTED' " +
	                "WHERE leave_id = ?";

	        PreparedStatement updatePs =
	                con.prepareStatement(updateSql);

	        updatePs.setInt(1, leave_id);

	        updatePs.executeUpdate();

	       
	        con.commit();

	        System.out.println(
	                "Leave rejected successfully!"
	        );

	        System.out.println(
	                "Leave balance was not changed."
	        );

	    }
	    catch (Exception e) {

	        con.rollback();

	        System.out.println(
	                "Transaction failed!"
	        );

	        System.out.println(
	                "All changes have been rolled back."
	        );

	        e.printStackTrace();
	    }
	    finally {


	        con.setAutoCommit(true);
	    }
	}

}
