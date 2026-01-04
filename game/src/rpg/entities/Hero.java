package rpg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import rpg.enums.HeroClass;
import rpg.game.ConsoleColors;
import rpg.items.Consumable;
import rpg.items.MainWeapon;
import rpg.items.Potion;

/**
 * Abstract class representing the hero controlled by the player.
 * Extends Entity and adds RPG mechanics such as level, gold,
 * equipment, inventory and combat utilities.
 */
public abstract class Hero extends Entity {

    protected int level;
    protected int gold;
    protected MainWeapon mainWeapon;
    protected List<Consumable> inventory;

    protected final Scanner scanner = new Scanner(System.in);

    public Hero(String name, int maxHp, int strength,
                int level, int gold, MainWeapon mainWeapon) {

        super(name, maxHp, strength);
        this.level = level;
        this.gold = gold;
        this.mainWeapon = mainWeapon;
        this.inventory = new ArrayList<>();
    }

    /* =========================
       GETTERS
       ========================= */

    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

    public MainWeapon getMainWeapon() {
        return mainWeapon;
    }

    public List<Consumable> getInventory() {
        return inventory;
    }

    /* =========================
       ECONOMY AND EQUIPMENT
       ========================= */

    public void addGold(int amount) {
        if (amount > 0) {
            gold += amount;
        }
    }

    public void setMainWeapon(MainWeapon weapon) {
        this.mainWeapon = weapon;
    }

    public void addConsumable(Consumable consumable) {
        if (consumable != null) {
            inventory.add(consumable);
        }
    }

    /* =========================
       LEVEL UP
       ========================= */

    public void levelUp() {

        level++;
        maxHp += 10;
        currentHp = maxHp;
        strength++;

        System.out.println("\nLevel up!");
        System.out.println("New level: " + level);
        System.out.println("Max HP: " + maxHp);
        System.out.println("Strength: " + strength);
    }

    /* =========================
       INPUT UTILITY
       ========================= */

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

    public boolean usePotion() {

        List<Potion> potions = getPotionsFromInventory();

        if (potions.isEmpty()) {
            System.out.println("You do not have any potions.");
            return false;
        }

        showPotionMenu(potions);

        int choice = readIntInRange(1, potions.size() + 1);

        if (choice == potions.size() + 1) {
            return false;
        }

        Potion potion = potions.get(choice - 1);
        applyPotionEffects(potion);
        inventory.remove(potion);

        return true;
    }

    private List<Potion> getPotionsFromInventory() {

        List<Potion> potions = new ArrayList<>();

        for (Consumable item : inventory) {
            if (item instanceof Potion) {
                potions.add((Potion) item);
            }
        }

        return potions;
    }

    private void showPotionMenu(List<Potion> potions) {

        System.out.println("Choose a potion:");

        for (int i = 0; i < potions.size(); i++) {
            System.out.print((i + 1) + ". ");
            potions.get(i).showDetails();
        }

        System.out.println((potions.size() + 1) + ". Cancel");
    }

    private void applyPotionEffects(Potion potion) {

        if (potion.getHealAmount() > 0) {
            heal(potion.getHealAmount());
            System.out.println("Healed " + potion.getHealAmount() + " HP.");
        }

        if (potion.getStrengthIncrease() > 0) {
            strength += potion.getStrengthIncrease();
            System.out.println(
                    "Strength increased by " + potion.getStrengthIncrease()
            );
        }

        System.out.println("Potion used: " + potion.getName());
        System.out.println("Current HP: " + currentHp + "/" + maxHp);
    }

    protected void showActionMenu() {

        System.out.println();
        System.out.println("Choose your action:");
        System.out.println("1 - Normal attack");
        System.out.println("2 - Special attack");
        System.out.println("3 - Combat consumable");
        System.out.println("4 - Use potion");
    }

    /* =========================
       COMBAT UTILITIES
       ========================= */

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
     * Executes a full combat against the given enemy.
     *
     * @param enemy the enemy NPC
     * @return true if the hero wins the combat
     */
    public abstract boolean attack(NPC enemy);

    public abstract HeroClass getHeroClass();
}
