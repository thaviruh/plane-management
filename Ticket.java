import java.io.*;

public class Ticket {
    private int seat;
    private String row;
    private float price;
    private Person person;

    public Ticket(int seat, String row, float price, Person person) {
        this.seat = seat;
        this.row = row;
        this.price = price;
        this.person = person;
    }

    public String getRow() { // Get the row
        return row;
    }

    public void setRow(String row) { // Set the row
        this.row = row;
    }

    public int getSeat() { // Get the seat
        return seat;
    }

    public void setSeat(int seat) { // Set the seat
        this.seat = seat;
    }

    public float getPrice() { // Get the price
        return price;
    }

    public void setPrice(float price) { // Set the price
        this.price = price;
    }

    public Person getPerson() { // Get the person
        return person;
    }

    public void setPerson(Person person) { // Set the person
        this.person = person;
    }

    public void printInformation() {
        System.out.printf("Row: %s%n", row);
        System.out.printf("Seat: %s%n", seat + 1);
        System.out.printf("Price: €%.2f%n", price);
        person.printInformation();
    }

    public void save(String row, int column) {
        try {
            FileWriter writer = new FileWriter(getRow() + getSeat() + ".txt"); // Append mode
            writer.write("Ticket Information\n");
            writer.write("Name: " + getPerson().getName() + "\n"); // Access instance methods correctly
            writer.write("Surname: " + getPerson().getSurname() + "\n");
            writer.write("Email: " + getPerson().getEmail() + "\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + column + "\n");
            writer.write("Price: €" + price + "\n\n");
            writer.close(); // Always close the writer to avoid memory leaks.
            System.out.println("Ticket information saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }


}
