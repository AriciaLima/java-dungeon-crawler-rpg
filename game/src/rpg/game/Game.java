package rpg.game;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import rpg.entities.Hero;
import rpg.entities.SteelExecutioner;
import rpg.entities.ShadowTracker;
import rpg.entities.OathboundHunter;
import rpg.entities.NPC;
import rpg.entities.Vendor;

import rpg.items.CombatConsumable;
import rpg.items.HeroItem;
import rpg.items.Potion;
import rpg.items.MainWeapon;

import rpg.enums.HeroClass;
import rpg.enums.Difficulty;
import rpg.enums.Rooms;
import rpg.game.ConsoleColors;

/**
 * Main game controller.
 * Handles hero creation, game flow, rooms, enemies and story progression.
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private Difficulty selectedDifficulty;
    private List<HeroItem> allItems;

    public Game() {
        initializeItems();
    }

    /* =========================
       ITEM INITIALIZATION
       ========================= */

    private void initializeItems() {

        allItems = new ArrayList<>();

        // Weapons
        allItems.add(new MainWeapon("Greatsword of Fire", 50, 20, 30,
                List.of(HeroClass.STEEL_EXECUTIONER)));
        allItems.add(new MainWeapon("Iron Axe", 30, 15, 25,
                List.of(HeroClass.STEEL_EXECUTIONER)));

        allItems.add(new MainWeapon("Shadow Blade", 45, 18, 28,
                List.of(HeroClass.SHADOW_TRACKER)));
        allItems.add(new MainWeapon("Dagger of Venom", 25, 12, 20,
                List.of(HeroClass.SHADOW_TRACKER)));

        allItems.add(new MainWeapon("Elven Bow", 40, 16, 26,
                List.of(HeroClass.OATHBOUND_HUNTER)));
        allItems.add(new MainWeapon("Crossbow", 35, 14, 24,
                List.of(HeroClass.OATHBOUND_HUNTER)));

        // Combat consumables
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

    /* =========================
       HERO CREATION
       ========================= */

    public Hero createHero() {

        System.out.println("Enter your hero name:");
        String name = scanner.nextLine();

        System.out.println("\nChoose your class:");
        System.out.println("1 - Steel Executioner");
        System.out.println("2 - Shadow Tracker");
        System.out.println("3 - Oathbound Hunter");

        int heroChoice = readIntInRange(1, 3);

        System.out.println("\nChoose difficulty:");
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

        System.out.println("\nBase stats:");
        System.out.println("HP: " + baseHp);
        System.out.println("Strength: " + baseStrength);
        System.out.println("Creation points: " + creationPoints);
        System.out.println("Each HP point gives +5 HP");
        System.out.println("Each Strength point gives +1 Strength");

        int hpPoints;
        int strengthPoints;

        while (true) {

            System.out.print("\nPoints to HP: ");
            hpPoints = readIntInRange(0, creationPoints);

            int remaining = creationPoints - hpPoints;
            System.out.println("Remaining points: " + remaining);

            System.out.print("Points to Strength: ");
            strengthPoints = readIntInRange(0, remaining);

            if (hpPoints + strengthPoints == creationPoints) {
                break;
            }

            System.out.println("You must spend all creation points.");
        }

        int finalHp = baseHp + hpPoints * 5;
        int finalStrength = baseStrength + strengthPoints;

        Hero hero = createHeroByChoice(
                heroChoice,
                name,
                finalHp,
                finalStrength,
                1,
                initialGold
        );

        System.out.println("\nHero created successfully:");
        hero.showDetails();

        return hero;
    }

    private Hero createHeroByChoice(
            int choice,
            String name,
            int hp,
            int strength,
            int level,
            int gold) {

        switch (choice) {

            case 1:
                return new SteelExecutioner(
                        name, hp, strength, level, gold,
                        new MainWeapon(
                                "Rusted Greatsword", 0, 10, 16,
                                List.of(HeroClass.STEEL_EXECUTIONER)));

            case 2:
                return new ShadowTracker(
                        name, hp, strength, level, gold,
                        new MainWeapon(
                                "Twin Rust Daggers", 0, 8, 18,
                                List.of(HeroClass.SHADOW_TRACKER)));

            case 3:
                return new OathboundHunter(
                        name, hp, strength, level, gold,
                        new MainWeapon(
                                "Oathbound Bow", 0, 7, 19,
                                List.of(HeroClass.OATHBOUND_HUNTER)));

            default:
                throw new IllegalStateException("Invalid hero choice");
        }
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
                case SMOKE:
                    currentRoom = roomCombat(hero);
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
       ROOMS
       ========================= */

    private Rooms roomVillageVendor(Hero hero) {

        System.out.println();
        System.out.println("Hunters Hall of Blackstone");
        System.out.println();

        System.out.println("The hall is silent.");
        System.out.println("Old banners hang torn.");
        System.out.println("The smell of iron and ash fills the air.");
        System.out.println();

        System.out.println("The Guildmaster steps forward.");
        System.out.println("\"Blackstone is dying.\"");
        System.out.println();
        System.out.println("\"Below this village lie the Oathbound Depths.\"");
        System.out.println("\"Hunters we sent never returned.\"");
        System.out.println();
        System.out.println("\"At the heart of the dungeon stands a fallen hunter.\"");
        System.out.println("\"Known only as The Broken Oath.\"");
        System.out.println();
        System.out.println("\"Your contract is simple.\"");
        System.out.println("\"Enter the Depths.\"");
        System.out.println("\"End the curse.\"");
        System.out.println();

        System.out.println("1 Accept the contract");
        System.out.println("2 Refuse and remain in Blackstone");

        int choice = readIntInRange(1, 2);

        if (choice == 2) {
            System.out.println();
            System.out.println("You turn away from the contract.");
            System.out.println("Blackstone remains cursed.");
            return Rooms.GAME_OVER;
        }

        return Rooms.ENTRANCE;
    }

    private Rooms roomEntrance() {

        System.out.println("\nDungeon Entrance");
        System.out.println("Cold air rises from below.");

        return Rooms.CROSSROADS;
    }

    private Rooms roomCrossroads() {

        System.out.println("\nYou reach a crossroads.");
        System.out.println("Two paths stretch before you.\n");

        System.out.println("1 - Path of Bones");
        System.out.println("   Direct, brutal and dangerous.");

        System.out.println("\n2 - Path of Smoke");
        System.out.println("   Twisted, slow and full of secrets.");

        if (readIntInRange(1, 2) == 1) {
            return Rooms.BONES;
        }

        return Rooms.SMOKE;
    }

    private Rooms roomCombat(Hero hero) {

        System.out.println("\nYou advance deeper into the dungeon...");
        System.out.println("You sense danger nearby.");

        NPC enemy = generateRandomEnemy();

        if (!hero.attack(enemy)) {
            return Rooms.GAME_OVER;
        }

        System.out.println("\nYou survive the encounter.");

        System.out.println("\nDo you want to use a potion?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");

        if (readIntInRange(1, 2) == 1) {
            hero.usePotion();
        }

        return Rooms.SHRINE_VENDOR;
    }


    private Rooms roomShrineVendor(Hero hero) {

        System.out.println("\nForgotten Shrine");
        Vendor vendor = new Vendor(allItems);

        while (true) {
            System.out.println("1 - Continue");
            System.out.println("2 - Trade");

            if (readIntInRange(1, 2) == 1) {
                return Rooms.BOSS;
            }

            vendor.sell(hero);
        }
    }

    private Rooms roomBoss(Hero hero) {

        System.out.println("\nFinal Chamber");
        System.out.println("The Broken Oath stands before you.");

        NPC boss = new NPC(
                "The Broken Oath",
                100,
                15,
                100,
                "A fallen hunter bound by shattered oaths."
        );

        if (hero.attack(boss)) {
            return Rooms.VICTORY;
        }

        return Rooms.GAME_OVER;
    }

    /* =========================
   RANDOM EVENT
   ========================= */

    private void randomEvent(Hero hero) {

        System.out.println();
        System.out.println(
                ConsoleColors.CYAN +
                        "Something stirs in the darkness..." +
                        ConsoleColors.RESET
        );

        int roll = (int) (Math.random() * 3);

        switch (roll) {

            case 0:
                System.out.println(
                        ConsoleColors.GREEN +
                                "You find an abandoned satchel among the ruins." +
                                ConsoleColors.RESET
                );
                System.out.println("You gain 15 gold.");
                hero.addGold(15);
                break;

            case 1:
                System.out.println(
                        ConsoleColors.RED +
                                "A hidden trap snaps beneath your feet!" +
                                ConsoleColors.RESET
                );
                System.out.println("You lose 10 HP.");
                hero.takeDamage(10);
                break;

            case 2:
                System.out.println(
                        ConsoleColors.YELLOW +
                                "You feel watched, but nothing happens." +
                                ConsoleColors.RESET
                );
                break;
        }

        System.out.println();
    }

    /* =========================
       ENEMIES
       ========================= */

    private NPC generateRandomEnemy() {

        if (Math.random() < 0.5) {
            return new NPC(
                    "Skeleton Warrior",
                    40,
                    8,
                    15,
                    "A reanimated pile of bones."
            );
        }

        return new NPC(
                "Cursed Ghost",
                35,
                10,
                20,
                "A wailing spirit trapped between worlds."
        );
    }

    /* =========================
       INPUT
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

            System.out.println("Invalid option.");
        }
    }

    /* =========================
       INTRO
       ========================= */

    private void showIntro() {

        System.out.println(
                ConsoleColors.PURPLE + ConsoleColors.BOLD +
                        "====================================\n" +
                        "           OATHBOUND DEPTHS\n" +
                        "====================================" +
                        ConsoleColors.RESET
        );

        System.out.println();
        System.out.println("The village of Blackstone was built over ancient ruins.");
        System.out.println("For generations, the depths below remained sealed.");
        System.out.println();
        System.out.println("Now, cursed lights flicker in forgotten tunnels.");
        System.out.println("Hunters vanish.");
        System.out.println("Screams echo through the stone at night.");
        System.out.println();
        System.out.println("The ground itself feels restless.");
        System.out.println();
        System.out.println("End the curse.");
        System.out.println("Or fall with it.");
    }


}
