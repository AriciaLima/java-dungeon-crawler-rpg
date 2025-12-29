package rpg.entities;

import rpg.items.CombatConsumable;
import rpg.items.Consumable;
import rpg.items.MainWeapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShadowTracker extends Hero {

    private Random random = new Random();

    public ShadowTracker(String name, int maxHp, int strength,
            int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    @Override
    public rpg.enums.HeroClass getHeroClass() {
        return rpg.enums.HeroClass.SHADOW_TRACKER;
    }

    @Override
    public boolean attack(NPC enemy) {

        boolean specialUsed = false;

        System.out.println("Combat started against " + enemy.getName());

        while (this.isAlive() && enemy.isAlive()) {

            boolean actionDone = false;

            while (!actionDone) {

                System.out.println("Choose your action:");
                System.out.println("1 Normal attack");
                System.out.println("2 Special attack");
                System.out.println("3 Combat consumable");
                System.out.println("4 Use Potion");

                int choice = readIntInRange(1, 4);

                if (choice == 1) {
                    // Critical hit chance
                    int dmg = strength + mainWeapon.getAttack();
                    if (random.nextDouble() < 0.2) {
                        dmg *= 2;
                        System.out.println("CRITICAL HIT!");
                    }
                    enemy.takeDamage(dmg);
                    System.out.println("You deal " + dmg + " damage.");
                    actionDone = true;
                }

                else if (choice == 2) {
                    if (specialUsed) {
                        System.out.println("Special attack already used.");
                    } else {
                        int dmg = strength + mainWeapon.getSpecialAttack();
                        enemy.takeDamage(dmg);
                        System.out.println("You deal " + dmg + " special damage.");
                        specialUsed = true;
                        actionDone = true;
                    }
                }

                else if (choice == 3) {
                    List<CombatConsumable> combatItems = new ArrayList<>();

                    for (Consumable c : inventory) {
                        if (c instanceof CombatConsumable) {
                            combatItems.add((CombatConsumable) c);
                        }
                    }

                    if (combatItems.isEmpty()) {
                        System.out.println("You have no combat consumables.");
                        continue;
                    }

                    System.out.println("Combat consumables:");
                    for (int i = 0; i < combatItems.size(); i++) {
                        System.out.println((i + 1) + " " + combatItems.get(i).getName());
                    }

                    System.out.println((combatItems.size() + 1) + " Cancel");

                    int c = readIntInRange(1, combatItems.size() + 1);

                    if (c == combatItems.size() + 1) {
                        continue;
                    }

                    CombatConsumable cc = combatItems.get(c - 1);
                    enemy.takeDamage(cc.getInstantAttackDamage());
                    inventory.remove(cc);

                    System.out.println("Consumable used.");
                    actionDone = true;
                }

                else {
                    if (usePotion()) {
                        actionDone = true;
                    }
                }
            }

            if (!enemy.isAlive()) {
                break;
            }

            int enemyDamage = (int) (enemy.getStrength() * 1.1);
            takeDamage(enemyDamage);
            System.out.println("Enemy attacks and deals " + enemyDamage + " damage.");

            if (!this.isAlive()) {
                return false;
            }
        }

        System.out.println("Enemy defeated.");
        addGold(enemy.getGold());
        levelUp();
        return true;
    }
}
