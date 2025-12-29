package rpg.entities;

import java.util.ArrayList;
import java.util.List;

import rpg.items.CombatConsumable;
import rpg.items.Consumable;
import rpg.items.MainWeapon;

import java.util.Random;

public class SteelExecutioner extends Hero {

    private Random random = new Random();

    public SteelExecutioner(String name, int maxHp, int strength,
            int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    @Override
    public rpg.enums.HeroClass getHeroClass() {
        return rpg.enums.HeroClass.STEEL_EXECUTIONER;
    }

    @Override
    public boolean attack(NPC enemy) {

        boolean specialUsed = false;

        System.out.println("Combat started against " + enemy.getName());

        while (this.isAlive() && enemy.isAlive()) {

            /* Enemy attacks first with reduced damage */
            int enemyDamage = (int) (enemy.getStrength() * 0.8);
            takeDamage(enemyDamage);
            System.out.println("Enemy attacks for " + enemyDamage + " damage.");

            if (!this.isAlive()) {
                System.out.println("You were defeated.");
                return false;
            }

            boolean actionDone = false;

            while (!actionDone) {

                System.out.println();
                System.out.println("Choose your action:");
                System.out.println("1 Normal attack");
                System.out.println("2 Special attack");
                System.out.println("3 Combat consumable");
                System.out.println("4 Use Potion");

                int choice = readIntInRange(1, 4);

                /* Normal attack */
                if (choice == 1) {
                    int heroDamage = strength + mainWeapon.getAttack() + random.nextInt(5);
                    enemy.takeDamage(heroDamage);
                    System.out.println("You deal " + heroDamage + " damage.");
                    actionDone = true;
                }

                /* Special attack */
                else if (choice == 2) {
                    if (specialUsed) {
                        System.out.println("Special attack already used.");
                    } else {
                        int heroDamage = strength + mainWeapon.getSpecialAttack();
                        enemy.takeDamage(heroDamage);
                        System.out.println("You deal " + heroDamage + " special damage.");
                        specialUsed = true;
                        actionDone = true;
                    }
                }

                /* Combat consumable */
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

                /* Use Potion */
                else {
                    if (usePotion()) {
                        actionDone = true;
                    }
                }
            }
        }

        System.out.println("Enemy defeated.");
        addGold(enemy.getGold());
        levelUp();
        return true;
    }
}
