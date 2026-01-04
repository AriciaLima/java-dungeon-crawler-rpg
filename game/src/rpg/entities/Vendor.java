package rpg.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import rpg.items.Consumable;
import rpg.items.HeroItem;
import rpg.items.MainWeapon;

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
     * @param allItems all items available in the game
     */
    public Vendor(List<HeroItem> allItems) {

        this.shop = new ArrayList<>(allItems);

        if (shop.size() > 10) {
            Collections.shuffle(shop, new Random());
            shop = new ArrayList<>(shop.subList(0, 10));
        }
    }

    /* =========================
       SHOP DISPLAY
       ========================= */

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

    /* =========================
       SHOP INTERACTION
       ========================= */

    public void sell(Hero hero) {

        while (true) {

            printShop();
            showHeroGold(hero);

            int choice = readIntInRange(1, shop.size() + 1);

            if (choice == shop.size() + 1) {
                System.out.println("You leave the shop.");
                return;
            }

            HeroItem item = shop.get(choice - 1);

            if (!canBuyItem(hero, item)) {
                continue;
            }

            // spend gold
            hero.addGold(-item.getPrice());

            handlePurchase(hero, item);
        }
    }

    private void showHeroGold(Hero hero) {
        System.out.println("Your gold: " + hero.getGold());
        System.out.print("Choose an option: ");
    }

    private boolean canBuyItem(Hero hero, HeroItem item) {

        if (hero.getGold() < item.getPrice()) {
            System.out.println("You do not have enough gold.");
            return false;
        }

        if (!item.canBeUsedBy(hero.getHeroClass())) {
            System.out.println("Your class cannot use this item.");
            return false;
        }

        return true;
    }

    private void handlePurchase(Hero hero, HeroItem item) {

        if (item instanceof MainWeapon) {
            equipWeapon(hero, (MainWeapon) item);
            return;
        }

        if (item instanceof Consumable) {
            hero.addConsumable((Consumable) item);
            System.out.println("You bought " + item.getName());
        }
    }

    private void equipWeapon(Hero hero, MainWeapon weapon) {

        System.out.println("You bought " + weapon.getName());

        if (hero.getMainWeapon() != null) {
            System.out.println("Old weapon: " + hero.getMainWeapon().getName());
        } else {
            System.out.println("Old weapon: none");
        }

        hero.setMainWeapon(weapon);
        System.out.println("New weapon equipped.");
    }

    /* =========================
       INPUT UTILITY
       ========================= */

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

            System.out.print(
                    "Invalid input. Choose between " + min + " and " + max + ": "
            );
        }
    }
}
