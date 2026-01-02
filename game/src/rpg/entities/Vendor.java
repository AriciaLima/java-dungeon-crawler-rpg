package rpg.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import rpg.items.HeroItem;
import rpg.items.MainWeapon;
import rpg.items.Consumable;

/**
 * Represents a vendor NPC in the game.
 * The vendor sells a random selection of items to the hero.
 */
public class Vendor {

    private List<HeroItem> shop;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Creates a vendor with a random stock of up to 10 items.
     *
     * @param allItems All items available in the game.
     */
    public Vendor(List<HeroItem> allItems) {

        this.shop = new ArrayList<>(allItems);

        if (shop.size() > 10) {
            Collections.shuffle(shop, new Random());
            shop = new ArrayList<>(shop.subList(0, 10));
        }
    }

    /**
     * Prints the current shop stock.
     */
    private void printShop() {

        System.out.println();
        System.out.println("===== Merchant Shop =====");

        for (int i = 0; i < shop.size(); i++) {
            System.out.print((i + 1) + ". ");
            shop.get(i).showDetails();
            System.out.println();
        }

        System.out.println((shop.size() + 1) + ". Leave shop and continue journey");
        System.out.println();
    }

    /**
     * Handles buying items and leaving the shop.
     *
     * @param hero The hero interacting with the vendor.
     */
    public void sell(Hero hero) {

        while (true) {

            printShop();
            System.out.println("Your gold: " + hero.getGold());
            System.out.print("Choose an option: ");

            int choice = readIntInRange(1, shop.size() + 1);

            if (choice == shop.size() + 1) {
                System.out.println("You leave the shop.");
                return;
            }

            HeroItem item = shop.get(choice - 1);

            if (hero.getGold() < item.getPrice()) {
                System.out.println("You do not have enough gold.");
                continue;
            }

            if (!item.canBeUsedBy(hero.getHeroClass())) {
                System.out.println("Your class cannot use this item.");
                continue;
            }

            hero.addGold(-item.getPrice());

            if (item instanceof MainWeapon) {

                System.out.println("You bought " + item.getName());
                System.out.println("Old weapon: " + hero.getMainWeapon().getName());
                hero.setMainWeapon((MainWeapon) item);
                System.out.println("New weapon equipped.");

            } else if (item instanceof Consumable) {

                hero.addConsumable((Consumable) item);
                System.out.println("You bought " + item.getName());
            }
        }
    }

    /**
     * Reads an integer input within a valid range.
     */
    private int readIntInRange(int min, int max) {

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

            System.out.print("Invalid input. Choose between " + min + " and " + max + ": ");
        }
    }
}
