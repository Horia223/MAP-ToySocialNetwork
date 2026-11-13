import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;
import repository.RepositoryFactory;
import validation.FriendshipValidator;
import validation.UserDataValidator;
import ui.Main;
import exception.ValidationException;
import validation.Validator;

 import java.nio.file.Paths;

public class Run {
    public static void main(String[] args) {
        RepositoryFactory factory = new RepositoryFactory(
                Paths.get("src/resources/config.properties"));


        Validator<User> noDigitsValidator = user -> {
            boolean firstNameHasDigit = user.getFirstName()
                    .chars()
                    .anyMatch(Character::isDigit);



            boolean lastNameHasDigit = user.getLastName()
                    .chars()
                    .anyMatch(Character::isDigit);

            if (firstNameHasDigit || lastNameHasDigit) {
                throw new ValidationException("Names cannot contain digits");
            }
        };

        Validator<User> userValidator =
                new UserDataValidator().andThen(noDigitsValidator);

        //sa folosec find ul nou cu predicat

        Repository<Long, User> userRepository =
                factory.createUserRepository(userValidator);


        Repository<Tuple<Long, Long>, Friendship> friendshipRepository =
                factory.createFriendshipRepository(new FriendshipValidator());

        Main main = new Main(userRepository, friendshipRepository);

        main.main();
    }
}