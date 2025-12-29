package rpg.items;

import java.util.List;

import rpg.enums.HeroClass;

public class CombatConsumable extends Consumable {

    private int instantAttackDamage;

    public CombatConsumable(String name,
                            int price,
                            int instantAttackDamage,
                            List<HeroClass> allowedHeroes) {

        super(name, price, allowedHeroes);
        this.instantAttackDamage = instantAttackDamage;
    }

    public int getInstantAttackDamage() {
        return instantAttackDamage;
    }

    @Override
    public void showDetails() {
        System.out.println(getName() + " | Damage: " + instantAttackDamage);
    }
}
