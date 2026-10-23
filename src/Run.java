import repository.FriendshipRepository;
import repository.UserRepository;

import ui.Main;

public class Run {
    static void run(){
        UserRepository  userRepository =  new UserRepository();
        FriendshipRepository friendshipRepository = new FriendshipRepository();
        Main main  = new Main(userRepository, friendshipRepository);
        main.main();
    }
}
