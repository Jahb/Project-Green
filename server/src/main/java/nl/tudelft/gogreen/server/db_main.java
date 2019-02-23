package nl.tudelft.gogreen.server;
import java.util.Scanner;

public class db_main {

    public static void initial_menu() {
        try {

            Scanner scanner = new Scanner(System.in);


            System.out.println("Welcome to the GoGreen application!");
            System.out.println("What do you want to do?");
            System.out.println("\t 1 - Log in ");
            System.out.println("\t 2 - Sign in ");


            boolean loop = false;
            int option;
            String logedin = null;
            while (!loop) {
                System.out.print("Enter an option: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        logedin = log_in.log_in();
                        loop = true;
                        break;
                    case 2:
                        create_user.create_user();
                        loop = true;
                        break;

                    default:
                        System.out.println("Option not available, please try again!");
                }
            }
            if (logedin != null){
                System.out.println("What do you want to do?");
                System.out.println("\t 1 - Enter a vegatarian meal ");
                System.out.println("\t 2 - Exit the program");

                boolean loop2 = false;
                int option2;

                while (!loop2) {
                    System.out.print("Enter an option: ");
                    option2 = scanner.nextInt();

                    switch (option2) {
                        case 1:
                            vegetarian_meal.vegetarian_meal(logedin);
                            loop2 = true;
                            break;
                        case 2:
                            System.exit(0);

                        default:
                            System.out.println("Option not available, please try again!");
                    }
                }
            }

        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }

    }

}
