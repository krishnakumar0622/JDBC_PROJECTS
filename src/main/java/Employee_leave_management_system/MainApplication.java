package Employee_leave_management_system;

import java.sql.Connection;
import java.util.Scanner;

public class MainApplication {
	
	
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
	            
	            
	            Scanner sc=new Scanner(System.in);
	            Transcation_Management TM =
	                    new Transcation_Management(con, sc);
	           
	            
	            Menu m=new Menu();
	            int choice;
	            
	            CRUDoperations CO=new CRUDoperations(con,sc);
	            
	            do {
	            	m.mainMenu();
	            	choice=sc.nextInt();
	            	sc.nextLine();
	            
	            	
	            	
	            	switch(choice) {
	            	case 1:
	            		
	            		int employeechoice;
	            		
	            		do {
	            			
	            			m.employeeMenu();
	            			
	            			employeechoice=sc.nextInt();
	            			sc.nextLine();
	            			
	            			switch(employeechoice) {
	            			case 1:
	            				CO.registerEmployee();
	            				break;
	            				
	            			case 2:
	            				CO.applyLeave();
	            				break;
	            			case 3:
	            				CO.viewMyLeaveRequests();
	            				break;
	            			case 4:
	            				CO.checkLeaveBalance();
	            				break;
	            			case 5:
	            				System.out.println("returing to main menu....");
	            				break;
	            				
	            			default:
	            				System.out.println("invalid choice...");
	            				
	            			}
	            			
	            			
	            			}while(employeechoice !=5);
	            		break;
	            		
	            		
	            	case 2:
	            		
	            		int managerchoice;
	            		do {
	            			m.managerMenu();
	            		
	            			managerchoice=sc.nextInt();
	            			sc.nextLine();
	            			
	            		switch(managerchoice) {
	            		case 1:
	            			CO.displayEmployees();
	            			break;
	            		 case 2:
                            CO.viewPendingLeaveRequests();
                             break;

                         case 3:
                             TM.approveleave();
                             break;

                         case 4:
                           TM.rejectleave();
                             break;

                         case 5:
                             System.out.println("Returning to Main Menu...");
                             break;

                         default:
                             System.out.println("Invalid choice!");	
	            			
	            			
	            			
	            			
	            		}
	            		
	            		
	            		}while(managerchoice != 5);
	            		break;
	            	
	            	
	            	   case 3:

	                        System.out.println("Thank you!");

	                        break;


	                    default:

	                        System.out.println("Invalid choice!");
	            		
	            		
	            
	            	}
	            	
	            } while (choice != 3);
	            
	            con.close();
	            sc.close();
	            
	            
	           
	            
	            
	            }
	         catch (Exception e) {
	            e.printStackTrace();
	        }
		    
		    
	            
		    
		    }
		    
	}


