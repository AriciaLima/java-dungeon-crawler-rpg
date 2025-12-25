package rpg.game;

import java.util.Scanner;
import java.util.List;

import rpg.entities.Hero;
import rpg.entities.SteelExecutioner;
import rpg.entities.ShadowTracker;
import rpg.entities.OathboundHunter;
import rpg.items.MainWeapon;
import rpg.enums.HeroClass;
import rpg.enums.Difficulty;
import rpg.enums.Rooms;

public class Game {

    /* =========================
       ATTRIBUTES
       ========================= */

    private final Scanner scanner = new Scanner(System.in);

    // Difficulty chosen for this run
    private Difficulty selectedDifficulty;

    /* =========================
       GAME SETUP
       ========================= */

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

    private Hero createHeroByChoice(int heroChoice, String name) {
        int level = 1;
        int strength = 10;

        switch (heroChoice) {

            case 1:
                return new SteelExecutioner(
                        name,
                        130,
                        strength,
                        level,
                        15,
                        new MainWeapon(
                                "Rusted Greatsword",
                                0,
                                10,
                                16,
                                List.of(HeroClass.STEEL_EXECUTIONER))
                );

            case 2:
                return new ShadowTracker(
                        name,
                        110,
                        strength,
                        level,
                        30,
                        new MainWeapon(
                                "Twin Rust Daggers",
                                0,
                                8,
                                18,
                                List.of(HeroClass.SHADOW_TRACKER))
                );

            case 3:
                return new OathboundHunter(
                        name,
                        95,
                        strength,
                        level,
                        20,
                        new MainWeapon(
                                "Oathbound Bow",
                                0,
                                7,
                                19,
                                List.of(HeroClass.OATHBOUND_HUNTER))
                );

            default:
                return null;
        }
    }

    public Difficulty getSelectedDifficulty() {
        return selectedDifficulty;
    }

    /* =========================
       GAME FLOW
       ========================= */

    public void startGame(Hero hero) {

        showIntro();

        Rooms currentRoom = Rooms.VILLAGE_VENDOR;

        while (currentRoom != Rooms.VICTORY && currentRoom != Rooms.GAME_OVER) {

            switch (currentRoom) {

                case VILLAGE_VENDOR:
                    currentRoom = roomVillageVendor(hero);
                    break;

                case ENTRANCE:
                    currentRoom = roomEntrance();
                    break;

                case CROSSROADS:
                    currentRoom = roomCrossroads();
                    break;

                case BONES:
                    currentRoom = roomBones(hero);
                    break;

                case SMOKE:
                    currentRoom = roomSmoke(hero);
                    break;

                case SHRINE_VENDOR:
                    currentRoom = roomShrineVendor(hero);
                    break;

                case BOSS:
                    currentRoom = roomBoss(hero);
                    break;
            }
        }

        if (currentRoom == Rooms.VICTORY) {
            System.out.println("\nYou survived the Oathbound Depths.");
            System.out.println("The curse of Blackstone has been broken.");
        } else {
            System.out.println("\nYour journey ends here.");
            System.out.println("Blackstone remains cursed.");
        }
    }

    /* =========================
       STORY INTRO
       ========================= */

    private void showIntro() {
        System.out.println("====================================");
        System.out.println("           OATHBOUND DEPTHS");
        System.out.println("====================================");
        System.out.println();
        System.out.println("The village of Blackstone was built over ancient ruins.");
        System.out.println("For years, the depths below remained silent.");
        System.out.println();
        System.out.println("Now the tunnels glow with cursed symbols.");
        System.out.println("Hunters disappear. Screams echo at night.");
        System.out.println();
        System.out.println("The Guild has issued a contract.");
        System.out.println("A legendary hunter known as The Broken Oath");
        System.out.println("is said to be the heart of the curse.");
        System.out.println();
        System.out.println("You are a bounty hunter of the Guild.");
        System.out.println("Enter the depths. End the curse. Survive.");
        System.out.println();
        System.out.println("====================================");
        System.out.println();
    }

    /* =========================
       ROOM METHODS
       ========================= */

    private Rooms roomVillageVendor(Hero hero) {
        System.out.println("\nHunters Hall of Blackstone");
        System.out.println("The Guild offers you a contract.");
        System.out.println("1 - Accept the contract");
        System.out.println("2 - Refuse and stay in the village");

        int choice = readIntInRange(1, 2);

        if (choice == 2) {
            return Rooms.GAME_OVER;
        }

        System.out.println("You accept the contract and prepare for the journey.");
        return Rooms.ENTRANCE;
    }

    private Rooms roomEntrance() {
        System.out.println("\nDungeon Entrance");
        System.out.println("Cold air rises from the depths below.");
        return Rooms.CROSSROADS;
    }

    private Rooms roomCrossroads() {
        System.out.println("\nCrossroads");
        System.out.println("1 - Path of Bones");
        System.out.println("2 - Path of Smoke");

        int choice = readIntInRange(1, 2);

        if (choice == 1) {
            return Rooms.BONES;
        }
        return Rooms.SMOKE;
    }

    private Rooms roomBones(Hero hero) {
        System.out.println("\nPath of Bones");
        System.out.println("Enemies block your way.");
        System.out.println("Combat will take place here.");
        return Rooms.SHRINE_VENDOR;
    }

    private Rooms roomSmoke(Hero hero) {
        System.out.println("\nPath of Smoke");
        System.out.println("The fog hides strange dangers.");
        return Rooms.SHRINE_VENDOR;
    }

    private Rooms roomShrineVendor(Hero hero) {
        System.out.println("\nForgotten Shrine");
        System.out.println("You take a moment to rest and prepare.");
        return Rooms.BOSS;
    }

    private Rooms roomBoss(Hero hero) {
        System.out.println("\nFinal Chamber");
        System.out.println("The Broken Oath stands before you.");
        System.out.println("The final battle begins.");
        return Rooms.VICTORY;
    }

    /* =========================
       INPUT UTILITIES
       ========================= */

    private int readIntInRange(int min, int max) {
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
