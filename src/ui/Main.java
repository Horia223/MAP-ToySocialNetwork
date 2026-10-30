package ui;

import service.NetworkService;
import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;

import java.util.Scanner;
import java.util.Set;

public class Main {
    private final NetworkService networkService;




    public Main(Repository<Long, User> userRepository,
                Repository<Tuple<Long, Long>, Friendship> friendshipRepository) {

        this.networkService = new NetworkService(userRepository, friendshipRepository);
    }




    public void main(){


        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while(running){
            System.out.println("\n======== Social Network =======");
            System.out.println("1. Add user.");
            System.out.println("2. Remove user.");
            System.out.println("3. Add friendship.");
            System.out.println("4. Remove friendship.");
            System.out.println("5. List users.");
            System.out.println("6. List friendships.");
            System.out.println("7. Count communities");
            System.out.println("8. Get most sociable community");
            System.out.println("Choose your option: ");

            String option = scanner.nextLine();

            try{

                switch(option){

                    case "1" -> {
                        System.out.print("Firstname: ");
                        String firstname = scanner.nextLine();

                        System.out.print("LastName: ");
                        String lastname = scanner.nextLine();

                        var user = networkService.addUser(firstname,lastname);
                        System.out.println("User added with Id: " + user.getId());
                    }

                    case "2" -> {
                        System.out.print("User Id: ");
                        Long id = Long.parseLong(scanner.nextLine());

                        networkService.removeUser(id);
                        System.out.println("User has been removed.");

                    }

                    case "3" -> {
                        System.out.print("Id for the first user: ");
                        Long id1 = Long.parseLong(scanner.nextLine());

                        System.out.print("Id for the second user: ");
                        Long id2 = Long.parseLong(scanner.nextLine());

                        networkService.addFriendship(id1, id2);

                        System.out.println("Friendship added.");


                    }

                    case  "4" -> {
                        System.out.print("First user: ");
                        Long id1 = Long.parseLong(scanner.nextLine());

                        System.out.print("Second user: ");
                        Long id2 = Long.parseLong(scanner.nextLine());

                        networkService.removeFriendship(id1,id2);
                        System.out.println("Friendship removed.");

                    }
                    case "5" -> {
                        var users = networkService.getAllUsers();

                        if (users.isEmpty()) {
                            System.out.println("No users found.");
                        } else
                        {
                            for (var user : users) {
                                System.out.println("ID: " + user.getId() + ", Name: " + user.getFirstName() + " " + user.getLastName()
                                );
                            }
                        }
                    }
                    case "6" -> {
                        var friendships = networkService.getAllFriendships();

                        if (friendships.isEmpty()) {
                            System.out.println("No friendships found.");
                        } else
                        {
                            for (var friendship : friendships) {
                                System.out.println(friendship.getUserId1() + " <----> " + friendship.getUserId2()
                                        + " at " + friendship.getDate()
                                );
                            }
                        }
                    }

                    case "7" -> {
                        int count = networkService.countCommunities();
                        System.out.println("Number of communities: " + count);
                    }

                    case "8" -> {
                        Set<Long> sociableCommunity= networkService.getMostSociableCommunity();
                        if(sociableCommunity.isEmpty()){
                            System.out.println("There are no communities.");
                        }
                        else {
                            System.out.println("Most sociable community (Ids): " + sociableCommunity);
                        }
                    }


                    case "0" ->  running = false;


                    default -> System.out.println("Invalid option.");
                }
            }
            catch(RuntimeException e){
                System.out.println("Error: " + e.getMessage());
            }




        }

        scanner.close();
        System.out.println("Bye bye...");

    }


}
