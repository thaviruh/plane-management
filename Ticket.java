public class Ticket {
    public String seat;
    private float price;
    private Person person; 

    public Ticket(String seat, float price, Person person){
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getSeat(){
        return seat;
    }

    public float getPrice(){
        return price;
    }

    public Person getPerson(){
        return person;
    }
        
}

