package rpg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import rpg.items.MainWeapon;
import rpg.items.Consumable;
import rpg.items.Potion;
import rpg.enums.HeroClass;
import rpg.game.ConsoleColors;

/**
 * Abstract class representing the main character controlled by the player.
 * Extends Entity and adds RPG-specific mechanics such as level, gold,
 * equipment, inventory, and combat utilities.
 */
public abstract class Hero extends Entity {

    /* =========================
       ATTRIBUTES
       ========================= */

    protected int level;
    protected int gold;
    protected MainWeapon mainWeapon;
    protected List<Consumable> inventory;

    protected Scanner scanner = new Scanner(System.in);

    /* =========================
       CONSTRUCTOR
       ========================= */

    /**
     * Creates a new Hero instance.
     *
     * @param name       hero name
     * @param maxHp      maximum health points
     * @param strength   strength attribute
     * @param level      starting level
     * @param gold       starting gold
     * @param mainWeapon starting weapon
     */
    public Hero(String name, int maxHp, int strength,
                int level, int gold, MainWeapon mainWeapon) {

        super(name, maxHp, strength);
        this.level = level;
        this.gold = gold;
        this.mainWeapon = mainWeapon;
        this.inventory = new ArrayList<>();
    }

    /* =========================
       DISPLAY
       ========================= */

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Level: " + level);
        System.out.println("Gold: " + gold);
        if (mainWeapon != null) {
            System.out.println("Main weapon: " + mainWeapon.getName());
        }
        System.out.println("----------------------");
    }

    /* =========================
       GETTERS
       ========================= */

    public int getGold() {
        return gold;
    }

    public MainWeapon getMainWeapon() {
        return mainWeapon;
    }

    public int getLevel() {
        return level;
    }

    public List<Consumable> getInventory() {
        return inventory;
    }

    /* =========================
       INVENTORY AND EQUIPMENT
       ========================= */

    public void addGold(int amount) {
        gold += amount;
    }

    public void setMainWeapon(MainWeapon weapon) {
        this.mainWeapon = weapon;
    }

    public void addConsumable(Consumable consumable) {
        inventory.add(consumable);
    }

    /* =========================
       LEVEL UP
       ========================= */

    /**
     * Levels up the hero, increasing stats.
     */
    public void levelUp() {

        level++;
        maxHp += 10;
        currentHp = maxHp;
        strength += 1;

        System.out.println();
        System.out.println("Level up!");
        System.out.println("New level: " + level);
        System.out.println("Max HP increased to " + maxHp);
        System.out.println("Strength increased to " + strength);
    }

    /* =========================
       INPUT UTILITY
       ========================= */

    /**
     * Reads an integer from console within a valid range.
     */
    protected int readIntInRange(int min, int max) {

        int value;

        while (true) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();

                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }

            System.out.println("Invalid option. Try again.");
        }
    }

    /* =========================
       POTIONS
       ========================= */

    /**
     * Allows the hero to use a potion from inventory.
     *
     * @return true if a potion was used, false otherwise
     */
    public boolean usePotion() {

        List<Potion> potions = new ArrayList<>();

        for (Consumable item : inventory) {
            if (item instanceof Potion) {
                potions.add((Potion) item);
            }
        }

        if (potions.isEmpty()) {
            System.out.println("You do not have any potions.");
            return false;
        }

        System.out.println("Choose a potion to use:");

        for (int i = 0; i < potions.size(); i++) {
            System.out.print((i + 1) + ". ");
            potions.get(i).showDetails();
        }

        System.out.println((potions.size() + 1) + ". Cancel");

        int choice = readIntInRange(1, potions.size() + 1);

        if (choice == potions.size() + 1) {
            return false;
        }

        Potion selectedPotion = potions.get(choice - 1);

        if (selectedPotion.getHealAmount() > 0) {
            heal(selectedPotion.getHealAmount());
            System.out.println("You healed " + selectedPotion.getHealAmount() + " HP.");
        }

        if (selectedPotion.getStrengthIncrease() > 0) {
            strength += selectedPotion.getStrengthIncrease();
            System.out.println(
                    "Your strength increased by " + selectedPotion.getStrengthIncrease() + "."
            );
        }

        inventory.remove(selectedPotion);

        System.out.println("Potion used: " + selectedPotion.getName());
        System.out.println("Current HP: " + currentHp + "/" + maxHp);

        return true;
    }

    /* =========================
       COMBAT UTILITIES
       ========================= */

    /**
     * Displays the enemy HP during combat.
     */
    protected void showEnemyHp(NPC enemy) {
        System.out.println(
                ConsoleColors.WHITE +
                        "Enemy HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp() +
                        ConsoleColors.RESET
        );
    }

    /* =========================
       COMBAT
       ========================= */

    /**
     * Abstract combat method implemented by each hero subclass.
     */
    public abstract boolean attack(NPC enemy);

    /**
     * Returns the hero class enum.
     */
    public abstract HeroClass getHeroClass();
}
