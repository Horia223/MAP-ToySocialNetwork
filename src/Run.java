import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;
import repository.RepositoryFactory;
import validation.FriendshipValidator;
import validation.UserDataValidator;
import ui.Main;

 import java.nio.file.Paths;

public class Run {
    public static void main(String[] args) {
        RepositoryFactory factory = new RepositoryFactory(
                Paths.get("src/resources/config.properties"));

        Repository<Long, User> userRepository =
                factory.createUserRepository(new UserDataValidator());


        Repository<Tuple<Long, Long>, Friendship> friendshipRepository =
                factory.createFriendshipRepository(new FriendshipValidator());

        Main main = new Main(userRepository, friendshipRepository);

        main.main();
    }
}