package rpg.entities;

import rpg.items.MainWeapon;

public class ShadowTracker extends Hero {

    public ShadowTracker(String name, int maxHp, int strength,
                         int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    @Override
    public boolean attack(NPC enemy) {
        // TODO: implement ShadowTracker combat:
        // hero attacks first, enemy damage +10% when it hits the hero
        return false;
    }
}
