package rpg.entities;

import rpg.items.MainWeapon;

public class OathboundHunter extends Hero {

    public OathboundHunter(String name, int maxHp, int strength,
                           int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    @Override
    public boolean attack(NPC enemy) {
        // TODO: implement OathboundHunter combat:
        // hero attacks first, normal damage taken,
        // you can add some "oath" debuff effect here later
        return false;
    }
}
