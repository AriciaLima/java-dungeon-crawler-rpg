package rpg.items;

import java.util.List;

import rpg.enums.HeroClass;


/**
 * Represents a main weapon that can be equipped by the hero.
 * Weapons have attack power and special attack power.
 */
public class MainWeapon extends HeroItem {

    private int attack;
    private int specialAttack;

    /**
     * Constructor for the MainWeapon class.
     *
     * @param name          The name of the weapon.
     * @param price         The price of the weapon.
     * @param attack        The base attack power of the weapon.
     * @param specialAttack The special attack power of the weapon.
     * @param allowedHeroes The list of hero classes allowed to equip this weapon.
     */
    public MainWeapon(String name, int price, int attack, int specialAttack, List<HeroClass> allowedHeroes) {
        super(name, price, allowedHeroes);
        this.attack = attack;
        this.specialAttack = specialAttack;
    }

    //Getters
    public int getAttack() {

        return attack;
    }

    public int getSpecialAttack() {

        return specialAttack;
    }

    //Methods

    public boolean canBeUsedBy(HeroClass heroClass) {
        if (allowedHeroes == null || allowedHeroes.isEmpty()) {
            return true;
        }
        return allowedHeroes.contains(heroClass);
    }

    @Override
    public void showDetails() {
        System.out.println(name + " | Attack: " + attack + " | Special: " + specialAttack + " | Price: " + price + " gold");
    }

}
