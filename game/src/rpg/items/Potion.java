package rpg.items;

import java.util.List;
import rpg.enums.HeroClass;

/**
 * Represents a potion item that can be used by the hero.
 * Potions can heal HP or increase strength.
 */
public class Potion extends Consumable {

    private int healAmount;
    private int strengthIncrease;

    /**
     * Constructor for the Potion class.
     *
     * @param name             The name of the potion.
     * @param price            The price of the potion.
     * @param healAmount       The amount of HP the potion restores.
     * @param strengthIncrease The amount of strength the potion adds.
     * @param allowedHeroes    The list of hero classes allowed to use this potion (null for all).
     */
    public Potion(String name, int price, int healAmount, int strengthIncrease, List<HeroClass> allowedHeroes) {
        super(name, price, allowedHeroes);
        this.healAmount = healAmount;
        this.strengthIncrease = strengthIncrease;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public int getStrengthIncrease() {
        return strengthIncrease;
    }

    @Override
    public void showDetails() {
        System.out.print(name + " | Price: " + price + " gold");
        if (healAmount > 0) {
            System.out.print(" | Heals: " + healAmount + " HP");
        }
        if (strengthIncrease > 0) {
            System.out.print(" | Strength: +" + strengthIncrease);
        }
        System.out.println();
    }
}
