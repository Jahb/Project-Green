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
                        System.out.println(logedin);
                        break;
                    case 2:
                        create_user.create_user();
                        loop = true;
                        break;

                    default:
                        System.out.println("Option not available, please try again!");
                }
            }
            if (logedin != null) {
                System.out.println("What do you want to do?");
                System.out.println("\t 1 - Enter a vegatarian meal ");
                System.out.println("\t 2 - Buying local product");
                System.out.println("\t 3 - Use the bike ");
                System.out.println("\t 4 - Use public transport ");
                System.out.println("\t 5 - I lowered the temperature of my house ");
                System.out.println("\t 6 - I installed solar panels");
                System.out.println("\t 7 - I recycled");
                System.out.println("\t 8 - I want to go on events_menu");
                System.out.println("\t 9 - Exit the program");
                String feature = null;
                boolean loop2 = false;
                int option2;

                while (!loop2) {
                    System.out.print("Enter an option: ");
                    option2 = scanner.nextInt();

                    switch (option2) {
                        case 1:
                            feature = "Vegetarian Meal";
                            loop2 = true;
                            break;
                        case 2:
                            feature = "Local Product";
                            loop2 = true;
                            break;
                        case 3:
                            feature = "Usage of Bike";
                            loop2 = true;
                            break;
                        case 4:
                            feature = "Usage of Public Transport";
                            loop2 = true;
                            break;

                        case 5:

                            feature = "Lower Temperature";
                            loop2 = true;
                            break;
                        case 6:

                            feature = "Solar Panels";
                            loop2 = true;
                            break;
                        case 7:
                            feature = "Recycling";
                            loop2 = true;
                            break;
                        case 8:
                            events_main.main_event("paul", "carnaval");
                            loop2 = true;
                            break;
                        case 9:
                            System.exit(0);

                        default:
                            System.out.println("Option not available, please try again!");
                    }
                }
                new_feature.adding_feature(logedin, feature);
            }

        } catch (Exception exception) {
            System.out.println("There has been an error accessing the database");
            System.out.println(exception.getMessage());
        }

    }

}