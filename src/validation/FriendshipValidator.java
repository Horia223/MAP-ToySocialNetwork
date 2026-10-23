package validation;

import  domain.Friendship;
import exception.ValidationException;

public class FriendshipValidator implements Validator<Friendship>{

    @Override
    public void validate(Friendship friendship){
        if (friendship == null){
            throw new ValidationException("Friendship cannot be null");
        }

        if(friendship.getUserId1() == friendship.getUserId2()) {
            throw new ValidationException(
                    "A user cannout be friends with themselves."
            );
        }
    }

}
