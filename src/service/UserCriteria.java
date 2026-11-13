package service;

import domain.User;

import java.util.Objects;
import java.util.function.Predicate;


//provides a reusavle search criteria for users
public class UserCriteria {

    private UserCriteria() {
    }

    /**
     * Creates a criterion that matches users whose first name starts with the prefix.
     *
     * @param prefix the prefix to search for
     * @return a predicate that tests a user's first name
     * @throws NullPointerException if the prefix is null
     */
        public static Predicate<User> firstNameStartsWith(String prefix){
            Objects.requireNonNull(prefix, "Prefix canno pe null");
            return user -> user.getFirstName().startsWith(prefix);

        }

    /**
     * Creates a criterion that matches users whose last name starts with the prefix.
     *
     * @param prefix the prefix to search for
     * @return a predicate that tests a user's last name
     * @throws NullPointerException if the prefix is null
     */
        public static Predicate<User> lastNameStartsWith(String prefix){
        Objects.requireNonNull(prefix, "Prefix cannot be null");
        return user -> user.getLastName().startsWith(prefix);
    }

    //the methods are static so we can use this without creatin an object UserCriteria
}
