package rpg.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import rpg.items.HeroItem;
import rpg.items.MainWeapon;
import rpg.items.Consumable;

public class Vendor {

    private List<HeroItem> shop;
    private Scanner scanner = new Scanner(System.in);

    public Vendor(List<HeroItem> allItems) {
        this.shop = new ArrayList<>(allItems);
        // Select 10 random items
        if (shop.size() > 10) {
            Collections.shuffle(shop, new Random());
            shop = shop.subList(0, 10);
        }
    }

    public void printShop() {
        System.out.println("--- Vendor Shop ---");
        for (int i = 0; i < shop.size(); i++) {
            System.out.print((i + 1) + ". ");
            shop.get(i).showDetails();
            System.out.println();
        }
        System.out.println((shop.size() + 1) + ". Leave shop");
    }

    public void sell(Hero hero) {
        while (true) {
            printShop();
            System.out.println("Current Gold: " + hero.getGold());
            
            int choice = readIntInRange(1, shop.size() + 1);
            
            if (choice == shop.size() + 1) {
                break;
            }
            
            HeroItem item = shop.get(choice - 1);
            
            // Check gold
            if (hero.getGold() < item.getPrice()) {
                System.out.println("You don't have enough gold.");
                continue;
            }
            
            // Check class permission
            if (!item.canBeUsedBy(hero.getHeroClass())) {
                System.out.println("You cannot use this item.");
                continue;
            }
            
            hero.addGold(-item.getPrice());
            
            if (item instanceof MainWeapon) {
                // Replace weapon
                System.out.println("You bought " + item.getName());
                System.out.println("Old weapon: " + hero.mainWeapon.getName());
                hero.mainWeapon = (MainWeapon) item;
                System.out.println("New weapon equipped.");
                
            } else if (item instanceof Consumable) {
                hero.inventory.add((Consumable) item);
                System.out.println("You bought " + item.getName());
            }
        }
    }
    
    private int readIntInRange(int min, int max) {
        int value;
        while (true) {
            System.out.print("Choice: ");
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid input.");
        }
    }
}
