import java.util.*;


public class PlaneManagement {
   static final int ROW_COUNT = 4; // Constant for the number of ROW_COUNT
   static final String[] ROWS = {"A", "B", "C", "D"}; // Characters for the ROW_COUNT


   static Scanner scanner = new Scanner(System.in); // Scanner for input


   static int[][] seats = new int[ROW_COUNT][]; // 2D array to represent the plane


   static Ticket[] tickets = new Ticket[0]; // Array to store tickets


   public static void main(String[] args) {
       for (int i = 0; i < ROW_COUNT; i++) {
           if (getRow(i).equals("B") || getRow(i).equals("C")) {
               seats[i] = new int[12];
           } else {
               seats[i] = new int[14];
           }
           Arrays.fill(seats[i], 0); // Fill the array with 0
       }


       int option = -1; // using -1 as the defalt value


       do { // Setting up the menu
           if (option == 1) buySeat();
           else if (option == 2) cancelSeat();
           else if (option == 3) displayFirstAvailableSeat();
           else if (option == 4) displaySeatingPlan();
           else if (option == 5) printTicketInformation();
           else if (option == 6) searchTicket();
           option = getOption();
       } while (option != 0);
       ThankYou();
   }


   static String getRow(int row){
       return ROWS[row];
   } // Get the row


   static int getOption() { // Get the option
       System.out.println();
       System.out.println("Welcome to the Plane Management System");
       System.out.println();
       System.out.println("**********************************************");
       System.out.println("*                     Menu                   *");
       System.out.println("**********************************************");
       System.out.println("1. Buy a seat");
       System.out.println("2. Cancel a seat");
       System.out.println("3. Find first available seat");
       System.out.println("4. Show seating plan");
       System.out.println("5. Print tickets information and total sales");
       System.out.println("6. Search Ticket");
       System.out.println("0. Exit");


       System.out.println();
       System.out.print("Enter Selected option: ");


       int option = -1; // -1 is the default value! dont use 0 as the default value
       do {
           try {
               option = scanner.nextInt(); // Get the option
           } catch (InputMismatchException e) { // Catch invalid input
               scanner.nextLine();
               System.out.println("Invalid input! Please enter a number");
           }
           return option;
       } while (option < 0 || option > 6); // Loop until the option is valid
   }


   static int[] userPreferSeat() {
       try {
           System.out.println("Enter the row (A-D): ");
           String character = scanner.next().toUpperCase();
           if (!Arrays.asList(ROWS).contains(character)) { // Check if the character is valid and converting to a list
               System.out.println("Invalid row! Please enter a valid row");
               return userPreferSeat();
           }
           int row = Arrays.asList(ROWS).indexOf(character); // Get the index of the character
           System.out.println("Enter the seat number: ");
           int column = scanner.nextInt();
           if (column < 1 || column > seats[row].length) {
               System.out.println("Invalid Seat! Please enter a valid Seat Number");
               scanner.nextLine();
               return userPreferSeat();
           }
           return new int [] {row, column}; // Return the row and column
       }catch (InputMismatchException e) {
           scanner.nextLine();
           System.out.println("Invalid input! Please enter a valid input");
           return userPreferSeat();
       }
   }


   static void buySeat() {
       int[] seat = userPreferSeat(); // Get the seat
       int row = seat[0]; // Get the row
       int column = seat[1]; // Get the column
       if (seats[row][column - 1] == 1) { // Check if the seat is already taken
           System.out.println("Seat is already taken!");
           return;
       } else {
           System.out.println("Enter the name: ");
           String name = scanner.next(); // Get the name
           System.out.println("Enter the surname: ");
           String surname = scanner.next(); // Get the surname
           System.out.println("Enter the email: ");
           String email = scanner.next(); // Get the email
           Person person = new Person(name, surname, email);
           float price = getTicketPrice(column); // Get the price from the relevant column
           Ticket ticket = new Ticket(column, getRow(row), price, person); // Create a new ticket
           System.out.println("here");
           ticket.printInformation();
           addTicket(ticket); // Add the ticket
           seats[row][column - 1] = 1; // Set the seat to taken
           ticket.save(getRow(row), column);
           System.out.println("Ticket bought successfully! Price is €" + price);
       }
   }

   static void addTicket(Ticket ticket) {
       Ticket[] newTickets = new Ticket[tickets.length + 1]; // Create a new array with one more element
       for (int i = 0; i < tickets.length; i++) {
           newTickets[i] = tickets[i]; // Copy the old tickets to the new array
       }
       newTickets[tickets.length] = ticket; // Add the new ticket to the new array
       tickets = newTickets; // Set the tickets to the new array
   }


   static void removeTicket(int index) {
       Ticket[] newTickets = new Ticket[tickets.length - 1]; // Create a new array with one less element
       for (int i = 0; i < index; i++) {
           newTickets[i] = tickets[i]; // Copy the old tickets to the new array
       }
       for (int i = index; i < tickets.length - 1; i++) {
           newTickets[i] = tickets[i + 1]; // Copy the old tickets to the new array
       }
       tickets = newTickets; // Set the tickets to the new array
   }


   static void cancelSeat() {
       int[] seat = userPreferSeat(); // Get the seat
       int row = seat[0];
       int column = seat[1];
       if (seats[row][column - 1] == 0) { // Check if the seat is already taken
           System.out.println("The seat is not purchased yet!");
           return;
       } else {
           seats[row][column - 1] = 0; // Set the seat to available
           int ticket = -1;
           for (int i = 0; i < tickets.length; i++) { // Loop through the tickets
               if (tickets[i].getRow().equals(getRow(row)) && tickets[i].getSeat() == column) { // Check if the ticket matches the seat
                   ticket = i; // Set the ticket to the index
                   break;
               }
           }
           if (ticket != -1) { // Check if the ticket was found
               removeTicket(ticket);
           } else {
               System.out.println("The ticket was not found!");
               return;
           }
           System.out.println("The seat had been successfully canceled!");
       }
   }


   static String getFirstAvailableSeat() {
       for (int i = 0; i < ROW_COUNT; i++) { // Loop through the ROW_COUNT
           for (int j = 0; j < seats[i].length; j++) { // Loop through the columns
               if (seats[i][j] == 0) { // Check if the seat is available
                   return String.format("%s%d", getRow(i), j + 1); // Return the seat
               }
           }
       }
       return "No available seats";
   }


   static void displayFirstAvailableSeat() {
       System.out.println();
       String seat = getFirstAvailableSeat(); // Get the first available seat
       if (seat != null) { // Check if the seat is available
           System.out.printf("The first available seat is %s", seat);
           System.out.println();
       } else {
           System.out.println("No available seats");
       }
       System.out.println();
   }


   static void displaySeatingPlan() {
       System.out.println();
       System.out.println("Seating Plan");
       for (int i = 0; i < ROW_COUNT; i++) { // Loop through the ROW_COUNT
           for (int j = 0; j < seats[i].length; j++) {  // Loop through the columns
               if (seats[i][j] == 0) { // Check if the seat is available
                   System.out.print("O ");
               } else {
                   System.out.print("X ");
               }
           }
           System.out.println();
       }
       System.out.println();
   }


   static float getTotalSales() {
       float total = 0;
       for (int i = 0; i< tickets.length; i++) { // Loop through the tickets
           total += tickets[i].getPrice(); // Add the price to the total
       }
       return total;
   }


   static void printTicketInformation() {
       System.out.println();
       System.out.println("Ticket Information");
       System.out.println();
       for (int i = 0; i < tickets.length; i++) { // Loop through the tickets
           System.out.println("Ticket " + (i + 1));
           System.out.println("Row: " + tickets[i].getRow());
           System.out.println("Seat: " + tickets[i].getSeat());
           System.out.println("Price: £" + tickets[i].getPrice());
           System.out.println("Name: " + tickets[i].getPerson().getName());
           System.out.println("Surname: " + tickets[i].getPerson().getSurname());
           System.out.println("Email: " + tickets[i].getPerson().getEmail());
           System.out.println();
       }
       System.out.println("Total Sales: £" + getTotalSales());
       System.out.println();
   }


   static void searchTicket() {
       int[] seat = userPreferSeat(); // Get the seat
       int row = seat[0];
       int column = seat[1];
       int ticket = -1;
       for (int i = 0; i < tickets.length; i++) { //Loop through the tickets
           if (tickets[i].getRow() == getRow(row) && tickets[i].getSeat() == column) { // Check if the ticket matches the seat
               ticket = i; // Set the ticket to the index
               break;
           }
       }
       if (ticket != -1) { // Check if the ticket was found
           System.out.println("Ticket Information");
           System.out.println("Name: " + tickets[ticket].getPerson().getName());
           System.out.println("Surname: " + tickets[ticket].getPerson().getSurname());
           System.out.println("Email: " + tickets[ticket].getPerson().getEmail());
           System.out.println("Price: " + tickets[ticket].getPrice());
       } else {
           System.out.println("The ticket was not found!"); // Print that the ticket was not found
           return;
       }
   }


   static float getTicketPrice(int column) {
       if (column < 6)
           return 200;
       else if (column < 12)
           return 150;
       else
           return 100;
   }


   static void ThankYou() {
       System.out.println("Thank you for Using the Plane Management System.");
   }
}