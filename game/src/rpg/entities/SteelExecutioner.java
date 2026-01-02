package rpg.entities;

import java.util.ArrayList;
import java.util.List;

import rpg.items.CombatConsumable;
import rpg.items.Consumable;
import rpg.items.MainWeapon;
import rpg.game.ConsoleColors;
import rpg.enums.HeroClass;

/**
 * Represents the Steel Executioner hero class.
 * A heavily armored melee fighter that excels at absorbing damage.
 * Enemies attack first, but their damage is reduced by armor.
 */
public class SteelExecutioner extends Hero {

    /**
     * Constructor for SteelExecutioner.
     *
     * @param name       The hero's name
     * @param maxHp      The hero's maximum HP
     * @param strength   The hero's strength attribute
     * @param level      The hero's level
     * @param gold       The hero's starting gold
     * @param mainWeapon The hero's starting weapon
     */
    public SteelExecutioner(String name, int maxHp, int strength,
                            int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    /**
     * Combat logic for the Steel Executioner.
     *
     * Rules:
     * Enemies attack first
     * Enemy damage is reduced by 20 percent due to heavy armor
     * No special attack
     * Can use combat consumables
     *
     * @param enemy The enemy NPC
     * @return true if the hero wins the combat, false otherwise
     */
    @Override
    public boolean attack(NPC enemy) {

        System.out.println();
        System.out.println(
                ConsoleColors.PURPLE + ConsoleColors.BOLD +
                        "=========================\n" +
                        "     EXECUTION COMBAT    \n" +
                        "=========================" +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.YELLOW +
                        "Enemy: " + enemy.getName() +
                        ConsoleColors.RESET
        );

        enemy.showDetails();

        while (this.isAlive() && enemy.isAlive()) {

            /* ENEMY TURN FIRST (reduced damage due to armor) */
            int enemyDamage = (int) (enemy.getStrength() * 0.8);
            takeDamage(enemyDamage);

            System.out.println(
                    ConsoleColors.RED +
                            "Enemy strikes for " + enemyDamage + " damage." +
                            ConsoleColors.RESET
            );

            System.out.println(
                    ConsoleColors.WHITE +
                            "Your HP: " + currentHp + "/" + maxHp +
                            ConsoleColors.RESET
            );

            if (!this.isAlive()) {
                System.out.println(
                        ConsoleColors.RED + ConsoleColors.BOLD +
                                "YOU WERE DEFEATED." +
                                ConsoleColors.RESET
                );
                return false;
            }

            boolean actionDone = false;

            while (!actionDone) {

                System.out.println();
                System.out.println("Choose your action:");
                System.out.println("1 - Normal attack");
                System.out.println("2 - Combat consumable");

                int choice = readIntInRange(1, 2);

                /* NORMAL ATTACK */
                if (choice == 1) {

                    int heroDamage = strength + mainWeapon.getAttack();
                    enemy.takeDamage(heroDamage);

                    System.out.println(
                            ConsoleColors.GREEN +
                                    "You crush the enemy for " + heroDamage + " damage." +
                                    ConsoleColors.RESET
                    );

                    System.out.println(
                            ConsoleColors.WHITE +
                                    "Enemy HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp() +
                                    ConsoleColors.RESET
                    );

                    actionDone = true;
                }

                /* COMBAT CONSUMABLE */
                else {

                    List<CombatConsumable> combatItems = new ArrayList<>();

                    for (Consumable c : inventory) {
                        if (c instanceof CombatConsumable) {
                            combatItems.add((CombatConsumable) c);
                        }
                    }

                    if (combatItems.isEmpty()) {
                        System.out.println(
                                ConsoleColors.YELLOW +
                                        "You have no combat consumables." +
                                        ConsoleColors.RESET
                        );
                        continue;
                    }

                    System.out.println("Combat consumables:");
                    for (int i = 0; i < combatItems.size(); i++) {
                        System.out.println((i + 1) + " - " + combatItems.get(i).getName());
                    }
                    System.out.println((combatItems.size() + 1) + " - Cancel");

                    int c = readIntInRange(1, combatItems.size() + 1);

                    if (c == combatItems.size() + 1) {
                        continue;
                    }

                    CombatConsumable cc = combatItems.get(c - 1);
                    enemy.takeDamage(cc.getInstantAttackDamage());
                    inventory.remove(cc);

                    System.out.println(
                            ConsoleColors.GREEN +
                                    "You used " + cc.getName() +
                                    " dealing " + cc.getInstantAttackDamage() + " damage." +
                                    ConsoleColors.RESET
                    );

                    actionDone = true;
                }
            }
        }

        System.out.println();
        System.out.println(
                ConsoleColors.GREEN + ConsoleColors.BOLD +
                        "ENEMY DEFEATED!" +
                        ConsoleColors.RESET
        );

        addGold(enemy.getGold());
        levelUp();

        return true;
    }

    /**
     * Returns the hero class enum.
     *
     * @return HeroClass.STEEL_EXECUTIONER
     */
    @Override
    public HeroClass getHeroClass() {
        return HeroClass.STEEL_EXECUTIONER;
    }
}
