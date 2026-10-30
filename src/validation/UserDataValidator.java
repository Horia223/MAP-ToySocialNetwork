package validation;

import domain.User;
import exception.ValidationException;

public class UserDataValidator implements  Validator<User> {

    @Override
    public void validate(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }

        StringBuilder errors = new StringBuilder();

        if (user.getId() == null || user.getId() <= 0) {
            errors.append("ID must be positive. ");
        }

        String firstName = user.getFirstName();
        if (firstName == null || firstName.isBlank()) {
            errors.append("First name cannot be empty. ");
        } else if (!Character.isUpperCase(firstName.charAt(0))) {
            errors.append("First name must start with a capital letter. ");
        }

        String lastName = user.getLastName();
        if (lastName == null || lastName.isBlank()) {
            errors.append("Last name cannot be empty. ");
        } else if (!Character.isUpperCase(lastName.charAt(0))) {
            errors.append("Last name must start with a capital letter. ");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString());
        }
    }
}
