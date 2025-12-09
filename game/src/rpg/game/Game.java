package rpg.game;

import java.util.Scanner;

import rpg.entities.Hero;
import rpg.entities.SteelExecutioner;
import rpg.entities.ShadowTracker;
import rpg.entities.OathboundHunter;
import rpg.items.MainWeapon;
import rpg.enums.HeroClass;

public class Game {

    private final Scanner scanner = new Scanner(System.in);

    public Hero createHero() {
        System.out.println("Enter your hero name:");
        String name = scanner.nextLine();

        System.out.println("Choose your class:");
        System.out.println("1 - Steel Executioner (high HP, low gold)");
        System.out.println("2 - Shadow Tracker (medium HP, high gold)");
        System.out.println("3 - Oathbound Hunter (low HP, medium gold)");

        int choice = readIntInRange(1, 3);

        return createHeroByChoice(choice, name);
    }

    private int readIntInRange(int min, int max) {
        int value;

        while (true) {
            System.out.print("Your choice (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // consume end of line
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.nextLine(); // consume invalid input
            }
            System.out.println("Invalid option, please try again.");
        }
    }

    private Hero createHeroByChoice(int heroChoice, String name) {
        int level = 1;
        int strength = 10;

        // TODO: replace this null with a real starting weapon instance
        MainWeapon startingWeapon = null;

        switch (heroChoice) {
            case 1:
                // SteelExecutioner: high HP, low gold
                return new SteelExecutioner(
                        name,
                        130,   // maxHp
                        strength,
                        level,
                        15,    // gold
                        startingWeapon
                );
            case 2:
                // ShadowTracker: medium HP, high gold
                return new ShadowTracker(
                        name,
                        110,   // maxHp
                        strength,
                        level,
                        30,    // gold
                        startingWeapon
                );
            case 3:
                // OathboundHunter: lower HP, medium gold
                return new OathboundHunter(
                        name,
                        95,    // maxHp
                        strength,
                        level,
                        20,    // gold
                        startingWeapon
                );
            default:
                // fallback, should not happen
                return new SteelExecutioner(
                        name,
                        120,
                        strength,
                        level,
                        15,
                        startingWeapon
                );
        }
    }
}
