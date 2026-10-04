package Bus_Reservation_System;

public class Menu {

    public void mainMenu() {

        System.out.println("\n======================================");
        System.out.println("       BUS RESERVATION SYSTEM");
        System.out.println("======================================");

        System.out.println("1. Search Buses");
        System.out.println("2. Passenger Details");
        System.out.println("3. Book Ticket");
        System.out.println("4. Cancel Ticket");
        System.out.println("5. View Bookings");
        System.out.println("6. Exit");

        System.out.print("Enter your choice: ");
    }

    public void passengerMenu() {

        System.out.println("\n========== PASSENGER MENU ==========");

        System.out.println("1. Register Passenger");
        System.out.println("2. Display Passengers");
        System.out.println("3. Back");

        System.out.print("Enter your choice: ");
    }
}