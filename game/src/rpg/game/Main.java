package rpg.game;

import java.util.Scanner;
import rpg.entities.Hero;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        boolean running = true;
        Hero hero = null;

        while (running) {

            if (hero == null) {
                hero = game.createHero();
            }

            game.startGame(hero);

            System.out.println();
            System.out.println("What do you want to do now?");
            System.out.println("1 - Play again with the same hero");
            System.out.println("2 - Create a new hero");
            System.out.println("3 - Exit game");

            int choice = readIntInRange(scanner, 1, 3);

            switch (choice) {
                case 1:
                    // same hero, nothing to do
                    break;

                case 2:
                    hero = null;
                    break;

                case 3:
                    running = false;
                    break;
            }
        }

        System.out.println();
        System.out.println("Thank you for playing.");
        scanner.close();
    }

    private static int readIntInRange(Scanner scanner, int min, int max) {
        int value;

        while (true) {
            System.out.print("Your choice (" + min + "-" + max + "): ");

            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();

                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }

            System.out.println("Invalid option, please try again.");
        }
    }
}
