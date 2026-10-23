package ui;
import domain.Friendship;
import service.NetworkService;
import repository.UserRepository;
import repository.FriendshipRepository;

import java.util.Scanner;

public class Main {

    static void main(String[] args){

        UserRepository userRepository = new UserRepository();
        FriendshipRepository friendshipRepository = new FriendshipRepository();

        NetworkService networkService = new NetworkService(
                userRepository,
                friendshipRepository
        );

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while(running){
            System.out.println("\n======== Social Network =======");
            System.out.println("1. Add user.");
            System.out.println("2. Remove user.");
            System.out.println("3. Add friendship.");
            System.out.println("4. Remove friendship.");
            System.out.println("5. List users.");

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
                        int id = Integer.parseInt(scanner.nextLine());

                        networkService.removeUser(id);
                        System.out.println("User has been removed.");

                    }

                    case "3" -> {
                        System.out.print("Id for the first user: ");
                        int id1 = Integer.parseInt(scanner.nextLine());

                        System.out.print("Id for the second user: ");
                        int id2 = Integer.parseInt(scanner.nextLine());

                        networkService.addFriendship(id1, id2);

                        System.out.println("Friendship added.");


                    }

                    case  "4" -> {
                        System.out.print("First user: ");
                        int id1 = Integer.parseInt(scanner.nextLine());

                        System.out.print("Second user: ");
                        int id2 = Integer.parseInt(scanner.nextLine());

                        networkService.removeFriendship(id1,id2);
                        System.out.println("Friendship removed.");


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
