import repository.FriendshipRepository;
import repository.UserRepository;
import validation.FriendshipValidator;
import validation.UserDataValidator;

import ui.Main;

public class Run {
     static void main(String[] args) {
        UserRepository userRepository = new UserRepository(new UserDataValidator());
        FriendshipRepository friendshipRepository = new FriendshipRepository(new FriendshipValidator());

        Main main = new Main(userRepository, friendshipRepository);
        main.main();
    }
}
