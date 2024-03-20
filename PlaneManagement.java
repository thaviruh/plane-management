import java.util.*;
import java.io.*;

public class PlaneManagement {
    static final int ROWS = 4; // Number of rows in the plane this is a constant variable // Marked as final as they cannot be chanegd

    static Scanner scanner = new Scanner(System.in); // Scanner object to read input from the user

    static String[] rowChars = { "A", "B", "C", "D" }; // Array to store the row characters
    static int[][] seats = new int[ROWS][]; // 2D array to store the seats // We use [] [] to indicate 2d array

    static ArrayList<Ticket> tickets = new ArrayList<Ticket>(); // ArrayList to store the tickets

    public static void main(String[] args) {
        System.out.println("Welcome to the plane management system!");

        for (int i = 0; i < ROWS; i++) {
            if (i == 1 || i == 2) {
                seats[i] = new int[12];
            } else {
                seats[i] = new int[14];
            }
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = 0;
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }

        int option = -1;
        do {
            if (option == 1)
                buySeat();
            if (option == 2)
                cancelSeat();
            if (option == 3)
                displayFirstAvailableSeat();
            if (option == 4)
                displaySeatingPlan();
            if (option == 5)
                displayTicketsAndTotalSales();
            if (option == 6)
                searchTicket();
        } while ((option = getOption()) != 0);
    }

    public static int getOption() {
        System.out.println("1. Buy a seat");
        System.out.println("2. Cancel a seat");
        System.out.println("3. Find first available seat");
        System.out.println("4. Show seating plan");
        System.out.println("5. Print tickets information and total sales");
        System.out.println("6. Search tickets");
        System.out.println("0. Exit");

        System.out.println("");
        System.out.print("Enter your option: ");

        int option = -1;
        do {
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                // Clear the scanner buffer
                scanner.nextLine();
            }
            System.out.println(option);
            return option;
        } while (option < 0 || option > 6);
    }

    static void displaySeatingPlan() {
        System.out.println();
        System.out.println("Seating Plan");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    static String getFirstAvailableSeat() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    return String.format("%s%d", getRow(i), j + 1);
                }
            }
        }
        return "No available seats";
    }

    static void displayFirstAvailableSeat() {
        System.out.println();
        String seat = getFirstAvailableSeat();
        if (seat != null) {
            System.out.printf("The first available seat is %s", seat);
            System.out.println();
        } else {
            System.out.println("No available seats");
        }
        System.out.println();
    }

    static void buySeat() {
        System.out.println();
        System.out.println("Enter the seat you want to buy: ");
        String seat = scanner.next();
        scanner.nextLine();
        int row = Arrays.asList(rowChars).indexOf(seat.substring(0, 1));
        int col = Integer.parseInt(seat.substring(1)) - 1;
        if (seats[row][col] == 0) {
            System.out.println("Enter the First Name :");
            String name = scanner.next();
            System.out.println("Enter the Surname :");
            String surname = scanner.next();
            System.out.println("Enter your Email :");
            String email = scanner.next();
            scanner.nextLine();
            Person person = new Person(name, surname, email);
            Ticket ticket = new Ticket(seat, getTicketPrice(col), person);
            System.out.println(person.getName() + "" + person.getSurname() + "has bought the seat" + seat + "for"
                    + ticket.getPrice() + "€");
            tickets.add(ticket);
            try {
                char[] data = (seat + "" + ticket.getPrice() + "" + person.getName() + "" + person.getSurname() + ""
                        + person.getEmail()).toCharArray();
                FileWriter file = new FileWriter(ticket.getSeat() + ".txt");
                file.write(data);
                file.close();
            } catch (IOException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
            }
            seats[row][col] = 1;
        } else {
            System.out.println("Seat" + seat + "is already taken. Please choose another seat.");
        }
        System.out.println();
    }

    static void cancelSeat() {
        System.out.println();
        System.out.print("Enter the seat seat you want to cancel: ");
        String seat = scanner.next();
        int row = Arrays.asList(rowChars).indexOf(seat.substring(0, 1));
        int col = Integer.parseInt(seat.substring(1)) - 1;
        if (seats[row][col] == 1) {
            seats[row][col] = 0;
            tickets.removeIf(t -> t.getSeat() == seat);
            System.out.println("Seat" + seat + "has been cancelled");
        } else {
            System.out.println("Seat" + seat + "is already available.");
        }
        System.out.println();
    }

    static void searchTicket() {
        System.out.println();
        System.out.print("Enter the seat you want to search: ");
        String seat = scanner.next();
        int row = Arrays.asList(rowChars).indexOf(seat.substring(0, 1));
        int col = Integer.parseInt(seat.substring(1)) - 1;
        if (seats[row][col] == 1) {
            System.out.println(seat);
            Ticket ticket = tickets.stream().filter(t -> t.seat == seat).findFirst().orElse(null);
            if (ticket != null) {
                System.out.println("Seat" + seat + "is taken by " + ticket.getPerson().getName() + ""
                        + ticket.getPerson().getSurname() + "(" + ticket.getPerson().getEmail() + ").");
            } else {
                System.out.println("Seat" + seat + "is available.");
            }
        } else {
            System.out.println("Seat" + seat + "is available.");
        }
        System.out.println();
    }

    static void displayTicketsAndTotalSales() {
        System.out.println();
        System.out.println("Tickets Information");
        System.out
                .println("Total Sales: " + tickets.stream().map(t -> t.getPrice()).reduce(0.0f, (a, b) -> a + b) + "€");
        System.out.println();
    }

    static float getTicketPrice(int col) {
        if (col < 6)
            return 200;
        else if (col < 12)
            return 150;
        else
            return 100;
    }

    static void noop() {
    }

    static String getRow(int row) {
        return rowChars[row];
    }
}
