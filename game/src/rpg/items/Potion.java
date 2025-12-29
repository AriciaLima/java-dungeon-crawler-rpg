package rpg.items;

import java.util.List;
import rpg.enums.HeroClass;

public class Potion extends Consumable {

    private int healAmount;
    private int strengthIncrease;

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
