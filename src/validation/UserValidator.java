package validation;

import domain.User;
import exception.ValidationException;

public class UserValidator implements  Validator<User> {

    @Override
    public void validate(User user) {
        if(user == null){

            throw new ValidationException("User cannot be null.");

        }

        if(user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new ValidationException("First name cannot be null or empty.");
        }

        if(user.getLastName() == null || user.getLastName().isBlank()){
            throw new ValidationException("Last name cannot be null or empty.");
        }
    }
}
