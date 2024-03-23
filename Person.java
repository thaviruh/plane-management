public class Person {
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public void printInformation() {
        System.out.printf("Name: %s%n", name);
        System.out.printf("Surname: %s%n", surname);
        System.out.printf("Email: %s%n", email);
    }
}
