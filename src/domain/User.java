package domain;
/**
 * Represents a user in the social network.
 * Each user has a unique ID, a first name, and a last name.
 */

public class User {
    private int id;
    private String firstName;
    private String lastName;


    /**
     * Creates a user with the specified ID and name.
     *
     * @param id the user's unique identifier
     * @param firstname the user's first name
     * @param lastName the user's last name
     */

    public User(int id, String firstname, String lastName){
        this.id =id;
        this.firstName = firstname;
        this.lastName =lastName;
    }


    public int getId(){
        return this.id;
    }

//    public void setId(int id){
//        this.id = id;
//    }
//

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
