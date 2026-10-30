package domain;
/**
 * Represents a user in the social network.
 * Each user has a unique ID, a first name, and a last name.
 */

public class User extends Entity<Long> {
    private String firstName;
    private String lastName;


    /**
     * Creates a user with the specified ID and name.
     *
     * @param id the user's unique identifier
     * @param firstname the user's first name
     * @param lastName the user's last name
     */

    public User(Long id, String firstname, String lastName){
        super(id);
        this.firstName = firstname;
        this.lastName =lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String FirstName){
        this.firstName = FirstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}
