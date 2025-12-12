package rpg.game;

import java.util.Scanner;
import java.util.List;
import java.util.Collections;


import rpg.entities.Hero;
import rpg.entities.SteelExecutioner;
import rpg.entities.ShadowTracker;
import rpg.entities.OathboundHunter;
import rpg.items.MainWeapon;
import rpg.enums.HeroClass;
import rpg.enums.Difficulty;

public class Game {

    private final Scanner scanner = new Scanner(System.in);

    // Difficulty chosen for this run
    private Difficulty selectedDifficulty;

    public Hero createHero() {
        System.out.println("Enter your hero name:");
        String name = scanner.nextLine();

        System.out.println("Choose your class:");
        System.out.println("1 - Steel Executioner (high HP, low gold)");
        System.out.println("2 - Shadow Tracker (medium HP, high gold)");
        System.out.println("3 - Oathbound Hunter (low HP, medium gold)");

        int choice = readIntInRange(1, 3);
        chooseDifficulty();
        return createHeroByChoice(choice, name);
    }

    private void chooseDifficulty() {
        System.out.println();
        System.out.println("Choose difficulty:");
        System.out.println("1 - Normal");
        System.out.println("2 - Hard");

        int choice = readIntInRange(1, 2);
        if (choice == 1) {
            selectedDifficulty = Difficulty.NORMAL;
            System.out.println("You chose NORMAL difficulty.");
        } else {
            selectedDifficulty = Difficulty.HARD;
            System.out.println("You chose HARD difficulty.");
        }
        System.out.println();
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

        switch (heroChoice) {
            case 1: {
                MainWeapon startingWeapon = new MainWeapon(
                        "Rusted Greatsword",
                        0,
                        10,
                        16,
                        List.of(HeroClass.STEEL_EXECUTIONER)
                );

                return new SteelExecutioner(
                        name,
                        130,
                        strength,
                        level,
                        15,
                        startingWeapon
                );
            }
            case 2: {
                MainWeapon startingWeapon = new MainWeapon(
                        "Twin Rust Daggers",
                        0,
                        8,
                        18,
                        List.of(HeroClass.SHADOW_TRACKER)
                );

                return new ShadowTracker(
                        name,
                        110,
                        strength,
                        level,
                        30,
                        startingWeapon
                );
            }
            case 3: {
                MainWeapon startingWeapon = new MainWeapon(
                        "Oathbound Bow",
                        0,
                        7,
                        19,
                        List.of(HeroClass.OATHBOUND_HUNTER)
                );

                return new OathboundHunter(
                        name,
                        95,
                        strength,
                        level,
                        20,
                        startingWeapon
                );
            }
            default: {
                MainWeapon startingWeapon = new MainWeapon(
                        "Rusted Greatsword",
                        0,
                        10,
                        16,
                        List.of(HeroClass.STEEL_EXECUTIONER)
                );

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

    /* Allows other parts of the game (for example, NPC creation)
     * to know which difficulty is active.
     */
    public Difficulty getSelectedDifficulty() {
        return selectedDifficulty;
    }
}