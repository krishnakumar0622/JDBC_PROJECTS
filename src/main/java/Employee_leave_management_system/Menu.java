package Employee_leave_management_system;

public class Menu {

    public void mainMenu() {
        System.out.println("\n======================================");
        System.out.println("   EMPLOYEE LEAVE MANAGEMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Employee");
        System.out.println("2. Manager");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public void employeeMenu() {
        System.out.println("\n========== EMPLOYEE MENU ==========");
        System.out.println("1. Register Employee");
        System.out.println("2. Apply Leave");
        System.out.println("3. View My Leave Requests");
        System.out.println("4. Check Leave Balance");
        System.out.println("5. Back");
        System.out.print("Enter your choice: ");
    }

    public void managerMenu() {
        System.out.println("\n========== MANAGER MENU ==========");
        System.out.println("1. Display Employees");
        System.out.println("2. View Pending Leave Requests");
        System.out.println("3. Approve Leave");
        System.out.println("4. Reject Leave");
        System.out.println("5. Back");
        System.out.print("Enter your choice: ");
    }
}