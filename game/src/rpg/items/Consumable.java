package rpg.items;

import java.util.List;

import rpg.entities.Hero;
import rpg.enums.HeroClass;

public abstract class Consumable extends HeroItem {

    public Consumable(String name, int price, List<HeroClass> allowedHeroes) {
        super(name, price, allowedHeroes);
    }

    // Consumables are used once and then removed
    // Specific behavior is defined in subclasses

    @Override
    public abstract void showDetails();
}
