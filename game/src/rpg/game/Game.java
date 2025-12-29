package rpg.game;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import rpg.entities.Hero;
import rpg.entities.SteelExecutioner;
import rpg.entities.ShadowTracker;
import rpg.entities.OathboundHunter;
import rpg.items.CombatConsumable;
import rpg.items.HeroItem;
import rpg.items.Potion;
import rpg.items.MainWeapon;
import rpg.entities.NPC;
import rpg.entities.Vendor;
import rpg.enums.HeroClass;
import rpg.enums.Difficulty;
import rpg.enums.Rooms;

public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private Difficulty selectedDifficulty;
    private List<HeroItem> allItems;

    public Game() {
        initializeItems();
    }

    private void initializeItems() {
        allItems = new ArrayList<>();

        // Weapons
        allItems.add(new MainWeapon("Greatsword of Fire", 50, 20, 30, List.of(HeroClass.STEEL_EXECUTIONER)));
        allItems.add(new MainWeapon("Iron Axe", 30, 15, 25, List.of(HeroClass.STEEL_EXECUTIONER)));
        allItems.add(new MainWeapon("Shadow Blade", 45, 18, 28, List.of(HeroClass.SHADOW_TRACKER)));
        allItems.add(new MainWeapon("Dagger of Venom", 25, 12, 20, List.of(HeroClass.SHADOW_TRACKER)));
        allItems.add(new MainWeapon("Elven Bow", 40, 16, 26, List.of(HeroClass.OATHBOUND_HUNTER)));
        allItems.add(new MainWeapon("Crossbow", 35, 14, 24, List.of(HeroClass.OATHBOUND_HUNTER)));

        // Combat Consumables
        allItems.add(new CombatConsumable("Fire Bomb", 20, 30, null));
        allItems.add(new CombatConsumable("Ice Bomb", 20, 25, null));
        allItems.add(new CombatConsumable("Throwing Knife", 10, 15, null));
        allItems.add(new CombatConsumable("Holy Water", 25, 40, null));

        // Potions
        allItems.add(new Potion("Minor Health Potion", 15, 20, 0, null));
        allItems.add(new Potion("Major Health Potion", 30, 50, 0, null));
        allItems.add(new Potion("Strength Potion", 20, 0, 5, null));
        allItems.add(new Potion("Greater Strength Potion", 40, 0, 10, null));
        allItems.add(new Potion("Elixir of Life", 100, 100, 5, null));
    }

    /*
     * =========================
     * HERO CREATION
     * =========================
     */

    public Hero createHero() {

        System.out.println("Enter your hero name:");
        String name = scanner.nextLine();

        System.out.println();
        System.out.println("Choose your class:");
        System.out.println("1 - Steel Executioner");
        System.out.println("2 - Shadow Tracker");
        System.out.println("3 - Oathbound Hunter");

        int heroChoice = readIntInRange(1, 3);

        System.out.println();
        System.out.println("Choose difficulty:");
        System.out.println("1 - Easy");
        System.out.println("2 - Hard");

        int diffChoice = readIntInRange(1, 2);

        int creationPoints;
        int initialGold;

        if (diffChoice == 1) {
            selectedDifficulty = Difficulty.NORMAL;
            creationPoints = 10;
            initialGold = 20;
        } else {
            selectedDifficulty = Difficulty.HARD;
            creationPoints = 7;
            initialGold = 15;
        }

        int baseHp = 50;
        int baseStrength = 5;
        int level = 1;

        System.out.println();
        System.out.println("Base hero stats:");
        System.out.println("HP: " + baseHp);
        System.out.println("Strength: " + baseStrength);

        System.out.println();
        System.out.println("You have " + creationPoints + " creation points.");
        System.out.println("Each point spent on HP gives +5 HP.");
        System.out.println("Each point spent on Strength gives +1 Strength.");
        System.out.println("You must spend all points.");

        int hpPoints;
        int strengthPoints;

        while (true) {

            System.out.print("\nHow many points do you want to spend on HP? ");
            hpPoints = readIntInRange(0, creationPoints);

            int remaining = creationPoints - hpPoints;
            System.out.println("Remaining points: " + remaining);

            System.out.print("How many points do you want to spend on Strength? ");
            strengthPoints = readIntInRange(0, remaining);

            if (hpPoints + strengthPoints == creationPoints) {
                break;
            }

            System.out.println("You must spend all creation points.");
        }

        int hp = baseHp + (hpPoints * 5);
        int strength = baseStrength + strengthPoints;

        Hero hero = createHeroByChoice(
                heroChoice,
                name,
                hp,
                strength,
                level,
                initialGold);

        System.out.println();
        System.out.println("Hero created successfully:");
        hero.showDetails();

        return hero;
    }

    private Hero createHeroByChoice(
            int heroChoice,
            String name,
            int hp,
            int strength,
            int level,
            int gold) {

        switch (heroChoice) {

            case 1:
                return new SteelExecutioner(
                        name,
                        hp,
                        strength,
                        level,
                        gold,
                        new MainWeapon(
                                "Rusted Greatsword",
                                0,
                                10,
                                16,
                                List.of(HeroClass.STEEL_EXECUTIONER)));

            case 2:
                return new ShadowTracker(
                        name,
                        hp,
                        strength,
                        level,
                        gold,
                        new MainWeapon(
                                "Twin Rust Daggers",
                                0,
                                8,
                                18,
                                List.of(HeroClass.SHADOW_TRACKER)));

            case 3:
                return new OathboundHunter(
                        name,
                        hp,
                        strength,
                        level,
                        gold,
                        new MainWeapon(
                                "Oathbound Bow",
                                0,
                                7,
                                19,
                                List.of(HeroClass.OATHBOUND_HUNTER)));

            default:
                throw new IllegalStateException("Invalid hero choice");
        }
    }

    public Difficulty getSelectedDifficulty() {
        return selectedDifficulty;
    }

    /*
     * =========================
     * GAME FLOW
     * =========================
     */

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

            if (currentRoom != Rooms.VICTORY && currentRoom != Rooms.GAME_OVER) {
                System.out.println("Do you want to use a potion? (1- Yes, 2- No)");
                int choice = readIntInRange(1, 2);
                if (choice == 1) {
                    hero.usePotion();
                }
            }
        }

        System.out.println();

        if (currentRoom == Rooms.VICTORY) {
            System.out.println("You survived the Oathbound Depths.");
            System.out.println("The curse of Blackstone has been broken.");
        } else {
            System.out.println("Your journey ends here.");
            System.out.println("Blackstone remains cursed.");
        }
    }

    /*
     * =========================
     * STORY INTRO
     * =========================
     */

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
    }

    /*
     * =========================
     * ROOM METHODS
     * =========================
     */

    private Rooms roomVillageVendor(Hero hero) {
        System.out.println();
        System.out.println("Hunters Hall of Blackstone");
        System.out.println("1 - Accept the contract");
        System.out.println("2 - Refuse and stay in the village");

        int choice = readIntInRange(1, 2);

        if (choice == 2) {
            return Rooms.GAME_OVER;
        }

        return Rooms.ENTRANCE;
    }

    private Rooms roomEntrance() {
        System.out.println();
        System.out.println("Dungeon Entrance");
        System.out.println("Cold air rises from the depths below.");
        return Rooms.CROSSROADS;
    }

    private Rooms roomCrossroads() {
        System.out.println();
        System.out.println("Crossroads");
        System.out.println("1 - Path of Bones");
        System.out.println("2 - Path of Smoke");

        int choice = readIntInRange(1, 2);

        if (choice == 1) {
            return Rooms.BONES;
        }
        return Rooms.SMOKE;
    }

    private Rooms roomBones(Hero hero) {
        System.out.println();
        System.out.println("Path of Bones");
        System.out.println("Enemies block your way.");

        NPC skeleton = new NPC("Skeleton Warrior", 40, 8, 15, "A reanimated pile of bones.");
        boolean victory = hero.attack(skeleton);

        if (victory) {
            return Rooms.SHRINE_VENDOR;
        } else {
            return Rooms.GAME_OVER;
        }
    }

    private Rooms roomSmoke(Hero hero) {
        System.out.println();
        System.out.println("Path of Smoke");
        System.out.println("The fog hides strange dangers.");

        NPC ghost = new NPC("Cursed Ghost", 35, 10, 20, "A wailing spirit.");
        boolean victory = hero.attack(ghost);

        if (victory) {
            return Rooms.SHRINE_VENDOR;
        } else {
            return Rooms.GAME_OVER;
        }
    }

    private Rooms roomShrineVendor(Hero hero) {
        System.out.println();
        System.out.println("Forgotten Shrine");
        System.out.println("You take a moment to rest.");

        Vendor vendor = new Vendor(allItems);
        System.out.println("A wandering merchant is resting here.");
        System.out.println("1 - Continue to the depths");
        System.out.println("2 - Trade");

        while (true) {
            int choice = readIntInRange(1, 2);

            if (choice == 1) {
                return Rooms.BOSS;
            } else {
                vendor.sell(hero);
                System.out.println("1 - Continue to the depths");
                System.out.println("2 - Trade");
            }
        }
    }

    private Rooms roomBoss(Hero hero) {
        System.out.println();
        System.out.println("Final Chamber");
        System.out.println("The Broken Oath stands before you.");

        NPC boss = new NPC("The Broken Oath", 100, 15, 100, "The legendary hunter, corrupted by the curse.");
        boolean victory = hero.attack(boss);

        if (victory) {
            return Rooms.VICTORY;
        } else {
            return Rooms.GAME_OVER;
        }
    }

    /*
     * =========================
     * INPUT UTILITIES
     * =========================
     */

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
