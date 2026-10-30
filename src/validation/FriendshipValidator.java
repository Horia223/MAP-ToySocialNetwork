package validation;

import  domain.Friendship;
import exception.ValidationException;

public class FriendshipValidator implements Validator<Friendship>{

    @Override
    public void validate(Friendship friendship){
        if (friendship == null){
            throw new ValidationException("Friendship cannot be null");
        }

        StringBuilder errors = new StringBuilder();

        if (friendship.getUserId1() <= 0) {
            errors.append("First user ID must be positive. ");
        }
        if (friendship.getUserId2() <= 0) {
            errors.append("Second user ID must be positive. ");
        }
        if (friendship.getUserId1().equals(friendship.getUserId2())) {
            errors.append("A user cannot be friends with themselves. ");
        }
        if (friendship.getDate() == null) {
            errors.append("Friendship date cannot be null. ");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString());
        }
    }

}
