package rpg.entities;

import java.util.ArrayList;
import java.util.List;

import rpg.items.MainWeapon;
import rpg.items.Consumable;

public abstract class Hero extends Entity {

    protected int level;
    protected int gold;
    protected MainWeapon mainWeapon;
    protected List<Consumable> inventory;

    public Hero(String name, int maxHp, int strength,
                int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength);
        this.level = level;
        this.gold = gold;
        this.mainWeapon = mainWeapon;
        this.inventory = new ArrayList<>();
    }

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

    public int getGold() {
        return gold;
    }

    public MainWeapon getMainWeapon() {
        return mainWeapon;
    }

    public abstract boolean attack(NPC enemy);
}
