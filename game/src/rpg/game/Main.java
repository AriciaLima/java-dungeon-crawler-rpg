package rpg.game;

import java.util.Scanner;
import rpg.entities.Hero;
import rpg.game.ConsoleColors;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        boolean running = true;
        Hero hero;

        showTitle();
        showIntro();

        while (running) {

            hero = game.createHero();
            game.startGame(hero);

            System.out.println();
            System.out.println(ConsoleColors.YELLOW + "What do you want to do now?" + ConsoleColors.RESET);
            System.out.println("1 Play again");
            System.out.println("2 Exit game");

            int choice = readIntInRange(scanner, 1, 2);

            if (choice == 2) {
                running = false;
            }
        }

        System.out.println();
        System.out.println(ConsoleColors.CYAN + "Thank you for playing Oathbound Depths." + ConsoleColors.RESET);
        scanner.close();
    }

    private static void showTitle() {

        System.out.println();
        System.out.println(
                ConsoleColors.PURPLE + ConsoleColors.BOLD +
                        "====================================\n" +
                        "           OATHBOUND DEPTHS          \n" +
                        "====================================" +
                        ConsoleColors.RESET
        );
        System.out.println();
    }

    private static void showIntro() {

        System.out.println(
                ConsoleColors.BLUE +
                        "The village of Blackstone was built over ancient ruins." +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BLUE +
                        "For generations, the depths below remained sealed." +
                        ConsoleColors.RESET
        );

        System.out.println();

        System.out.println(
                ConsoleColors.BLUE +
                        "Now cursed lights flicker in forgotten tunnels." +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BLUE +
                        "Hunters vanish." +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BLUE +
                        "Screams echo through the stone at night." +
                        ConsoleColors.RESET
        );

        System.out.println();

        System.out.println(
                ConsoleColors.BLUE +
                        "A single contract remains." +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BLUE +
                        "Enter the depths." +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.BLUE +
                        "End the curse." +
                        ConsoleColors.RESET
        );

        System.out.println();
    }

    private static int readIntInRange(Scanner scanner, int min, int max) {

        int value;

        while (true) {

            System.out.print(
                    ConsoleColors.CYAN +
                            "Your choice (" + min + " to " + max + "): " +
                            ConsoleColors.RESET
            );

            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();

                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }

            System.out.println(
                    ConsoleColors.RED +
                            "Invalid option. Try again." +
                            ConsoleColors.RESET
            );
        }
    }
}
