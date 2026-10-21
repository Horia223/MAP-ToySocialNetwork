package domain;

public class User {
    private int id;
    private String firstName;
    private String lastName;


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
