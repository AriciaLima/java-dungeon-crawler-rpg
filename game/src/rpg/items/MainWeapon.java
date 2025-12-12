package rpg.items;

import java.util.List;

import rpg.enums.HeroClass;


public class MainWeapon extends HeroItem {

    private int attack;
    private int specialAttack;

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
