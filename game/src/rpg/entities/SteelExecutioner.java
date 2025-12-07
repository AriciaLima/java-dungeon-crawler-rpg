package rpg.entities;

import rpg.items.MainWeapon;
public class SteelExecutioner extends Hero{
    public SteelExecutioner(String name,int maxHp,int strength, int level,int gold, MainWeapon mainWeapon) {
        super(name,maxHp,strength,level,gold,mainWeapon);

    }

    @Override
    public boolean attack(NPC enemy){
        // TODO: implement SteelExecutioner combat:
        // enemy attacks first, hero takes 80% damage, then hero attacks
        return false;
    }
}
