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

        allItems.add(new MainWeapon(
                "Greatsword of Fire", 50, 20, 30,
                List.of(HeroClass.STEEL_EXECUTIONER)));

        allItems.add(new MainWeapon(
                "Iron Axe", 30, 15, 25,
                List.of(HeroClass.STEEL_EXECUTIONER)));

        allItems.add(new MainWeapon(
                "Shadow Blade", 45, 18, 28,
                List.of(HeroClass.SHADOW_TRACKER)));

        allItems.add(new MainWeapon(
                "Dagger of Venom", 25, 12, 20,
                List.of(HeroClass.SHADOW_TRACKER)));

        allItems.add(new MainWeapon(
                "Elven Bow", 40, 16, 26,
                List.of(HeroClass.OATHBOUND_HUNTER)));

        allItems.add(new MainWeapon(
                "Crossbow", 35, 14, 24,
                List.of(HeroClass.OATHBOUND_HUNTER)));

        allItems.add(new CombatConsumable("Fire Bomb", 20, 30, null));
        allItems.add(new CombatConsumable("Ice Bomb", 20, 25, null));
        allItems.add(new CombatConsumable("Throwing Knife", 10, 15, null));
        allItems.add(new CombatConsumable("Holy Water", 25, 40, null));

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

        System.out.println("\n" + ConsoleColors.PURPLE + "Choose your class:" + ConsoleColors.RESET + "\n");

        System.out.println(ConsoleColors.YELLOW + "1 - Steel Executioner" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Heavy armored warrior" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Enemy attacks first" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Reduced damage taken\n" + ConsoleColors.RESET);

        System.out.println(ConsoleColors.YELLOW + "2 - Shadow Tracker" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Agile assassin" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Chance for critical hits" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Takes extra damage\n" + ConsoleColors.RESET);

        System.out.println(ConsoleColors.YELLOW + "3 - Oathbound Hunter" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Balanced fighter" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Special attack once per combat" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "  Tactical and consistent\n" + ConsoleColors.RESET);

        int heroChoice = readIntInRange(1, 3);

        String name;

        if (heroChoice == 1) {
            name = "Steel Executioner";
        } else if (heroChoice == 2) {
            name = "Shadow Tracker";
        } else {
            name = "Oathbound Hunter";
        }

        System.out.println("\n" + ConsoleColors.YELLOW + "Choose difficulty:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "1 - Normal" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2 - Hard" + ConsoleColors.RESET);

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

        System.out.println("\n" + ConsoleColors.WHITE + "Base stats:" + ConsoleColors.RESET);
        System.out.println("HP: " + baseHp);
        System.out.println("Strength: " + baseStrength);
        System.out.println("Creation points: " + creationPoints);

        int hpPoints;
        int strengthPoints;

        while (true) {

            System.out.print(ConsoleColors.CYAN + "\nPoints to HP: " + ConsoleColors.RESET);
            hpPoints = readIntInRange(0, creationPoints);

            int remaining = creationPoints - hpPoints;
            System.out.println("Remaining points: " + remaining);

            System.out.print(ConsoleColors.CYAN + "Points to Strength: " + ConsoleColors.RESET);
            strengthPoints = readIntInRange(0, remaining);

            if (hpPoints + strengthPoints == creationPoints) {
                break;
            }

            System.out.println(ConsoleColors.RED + "You must spend all creation points." + ConsoleColors.RESET);
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

        System.out.println("\n" + ConsoleColors.GREEN + "Hero created successfully:" + ConsoleColors.RESET);
        System.out.println(hero.getDetails());

        return hero;
    }

    private Hero createHeroByChoice(
            int choice,
            String name,
            int hp,
            int strength,
            int level,
            int gold) {

        if (choice == 1) {
            return new SteelExecutioner(
                    name, hp, strength, level, gold,
                    new MainWeapon(
                            "Rusted Greatsword", 0, 10, 16,
                            List.of(HeroClass.STEEL_EXECUTIONER)));
        }

        if (choice == 2) {
            return new ShadowTracker(
                    name, hp, strength, level, gold,
                    new MainWeapon(
                            "Twin Rust Daggers", 0, 8, 18,
                            List.of(HeroClass.SHADOW_TRACKER)));
        }

        return new OathboundHunter(
                name, hp, strength, level, gold,
                new MainWeapon(
                        "Oathbound Bow", 0, 7, 19,
                        List.of(HeroClass.OATHBOUND_HUNTER)));
    }

    /* =========================
       GAME FLOW
       ========================= */

    public void startGame(Hero hero) {

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
    }

    /* =========================
       ROOMS
       ========================= */

    private Rooms roomVillageVendor(Hero hero) {

        System.out.println("\n" + ConsoleColors.PURPLE + "Hunters Hall of Blackstone" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "1 Accept the contract" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2 Refuse" + ConsoleColors.RESET);

        if (readIntInRange(1, 2) == 2) {
            System.out.println(ConsoleColors.RED + "\nYou turn away from the contract." + ConsoleColors.RESET);
            return Rooms.GAME_OVER;
        }

        return Rooms.ENTRANCE;
    }

    private Rooms roomEntrance() {

        System.out.println("\n" + ConsoleColors.PURPLE + "Dungeon Entrance" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "Cold air rises from below." + ConsoleColors.RESET);
        return Rooms.CROSSROADS;
    }

    private Rooms roomCrossroads() {

        System.out.println("\n" + ConsoleColors.PURPLE + "Crossroads" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "1 - Path of Bones" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2 - Path of Smoke" + ConsoleColors.RESET);

        if (readIntInRange(1, 2) == 1) {
            return Rooms.BONES;
        }

        return Rooms.SMOKE;
    }

    private Rooms roomCombat(Hero hero) {

        System.out.println(
                "\n" + ConsoleColors.RED +
                        "You sense danger nearby." +
                        ConsoleColors.RESET
        );

        NPC enemy = generateRandomEnemy();

        System.out.println(
                ConsoleColors.YELLOW + "Enemy: " + enemy.getName() + ConsoleColors.RESET +
                        ConsoleColors.WHITE +
                        " (" + enemy.getCurrentHp() + "/" + enemy.getMaxHp() + " HP)" +
                        ConsoleColors.RESET
        );

        if (!hero.attack(enemy)) {
            return Rooms.GAME_OVER;
        }

        System.out.println();
        System.out.println(
                ConsoleColors.YELLOW +
                        "Do you want to use a potion?" +
                        ConsoleColors.RESET
        );
        System.out.println(ConsoleColors.YELLOW + "1 - Yes" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2 - No" + ConsoleColors.RESET);

        if (readIntInRange(1, 2) == 1) {
            hero.usePotion();
        }

        return Rooms.SHRINE_VENDOR;
    }


    private Rooms roomShrineVendor(Hero hero) {

        Vendor vendor = new Vendor(allItems);

        while (true) {

            System.out.println(
                    "\n" + ConsoleColors.PURPLE +
                            "Forgotten Shrine" +
                            ConsoleColors.RESET
            );

            System.out.println(ConsoleColors.YELLOW + "1 - Continue" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW + "2 - Trade" + ConsoleColors.RESET);

            int choice = readIntInRange(1, 2);

            if (choice == 1) {
                return Rooms.BOSS;
            }

            vendor.sell(hero);
        }
    }

    private Rooms roomBoss(Hero hero) {

        System.out.println("\n" + ConsoleColors.PURPLE + "Final Chamber" + ConsoleColors.RESET);

        NPC boss = new NPC(
                "The Broken Oath",
                100,
                15,
                100,
                "A fallen hunter bound by shattered oaths."
        );

        System.out.println(
                ConsoleColors.YELLOW + "Boss: " + boss.getName() + ConsoleColors.RESET +
                        ConsoleColors.WHITE +
                        " (" + boss.getCurrentHp() + "/" + boss.getMaxHp() + " HP)" +
                        ConsoleColors.RESET
        );

        if (hero.attack(boss)) {
            return Rooms.VICTORY;
        }

        return Rooms.GAME_OVER;
    }

    /* =========================
       ENEMIES
       ========================= */

    private NPC generateRandomEnemy() {

        boolean skeleton = Math.random() < 0.5;

        String name;
        int baseHp;
        int baseStrength;
        int gold;
        String description;

        if (skeleton) {
            name = "Skeleton Warrior";
            baseHp = 40;
            baseStrength = 8;
            gold = 15;
            description = "A reanimated pile of bones.";
        } else {
            name = "Cursed Ghost";
            baseHp = 35;
            baseStrength = 10;
            gold = 20;
            description = "A wailing spirit trapped between worlds.";
        }

        int finalHp = (int) (baseHp * selectedDifficulty.getEnemyHpMultiplier());
        int finalStrength = (int) (baseStrength * selectedDifficulty.getEnemyStrengthMultiplier());

        return new NPC(
                name,
                finalHp,
                finalStrength,
                gold,
                description
        );
    }


    /* =========================
       INPUT
       ========================= */

    private int readIntInRange(int min, int max) {

        int value;

        while (true) {

            System.out.print(
                    ConsoleColors.CYAN +
                            "Your choice (" + min + "-" + max + "): " +
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

            System.out.println(ConsoleColors.RED + "Invalid option." + ConsoleColors.RESET);
        }
    }
}
