package rpg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rpg.items.CombatConsumable;
import rpg.items.Consumable;
import rpg.items.MainWeapon;
import rpg.game.ConsoleColors;
import rpg.enums.HeroClass;

/**
 * Represents the Oathbound Hunter hero class.
 * A disciplined hunter bound by ancient contracts.
 * Balanced combat style with controlled damage variance.
 */
public class OathboundHunter extends Hero {

    private final Random random = new Random();

    /**
     * Constructor for OathboundHunter.
     *
     * @param name       The hero's name
     * @param maxHp      The hero's maximum HP
     * @param strength   The hero's strength attribute
     * @param level      The hero's level
     * @param gold       The hero's starting gold
     * @param mainWeapon The hero's starting weapon
     */
    public OathboundHunter(String name, int maxHp, int strength,
                           int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    /**
     * Returns the hero class enum.
     *
     * @return HeroClass.OATHBOUND_HUNTER
     */
    @Override
    public HeroClass getHeroClass() {
        return HeroClass.OATHBOUND_HUNTER;
    }

    /**
     * Combat logic for the Oathbound Hunter.
     *
     * Rules:
     * The hero attacks first
     * Damage has a small random variance
     * Special attack can be used once per combat
     * Standard damage received from enemies
     *
     * @param enemy The enemy NPC
     * @return true if the hero wins the combat, false otherwise
     */
    @Override
    public boolean attack(NPC enemy) {

        boolean specialUsed = false;

        System.out.println();
        System.out.println(
                ConsoleColors.PURPLE + ConsoleColors.BOLD +
                        "=========================\n" +
                        "     OATHBOUND COMBAT    \n" +
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

            boolean actionDone = false;

            while (!actionDone) {

                System.out.println();
                System.out.println("Choose your action:");
                System.out.println("1 - Normal attack");
                System.out.println("2 - Special attack (once per combat)");
                System.out.println("3 - Combat consumable");
                System.out.println("4 - Use potion");

                int choice = readIntInRange(1, 4);

                /* NORMAL ATTACK */
                if (choice == 1) {

                    int variance = random.nextInt(3);
                    int damage = strength + mainWeapon.getAttack() + variance;

                    enemy.takeDamage(damage);

                    System.out.println(
                            ConsoleColors.GREEN +
                                    "You strike for " + damage + " damage." +
                                    ConsoleColors.RESET
                    );

                    actionDone = true;
                }

                /* SPECIAL ATTACK */
                else if (choice == 2) {

                    if (specialUsed) {
                        System.out.println(
                                ConsoleColors.YELLOW +
                                        "Special attack already used." +
                                        ConsoleColors.RESET
                        );
                        continue;
                    }

                    int damage = strength + mainWeapon.getSpecialAttack();
                    enemy.takeDamage(damage);
                    specialUsed = true;

                    System.out.println(
                            ConsoleColors.GREEN + ConsoleColors.BOLD +
                                    "You invoke your oath and deal " + damage + " special damage!" +
                                    ConsoleColors.RESET
                    );

                    actionDone = true;
                }

                /* COMBAT CONSUMABLE */
                else if (choice == 3) {

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

                /* USE POTION */
                else {
                    if (usePotion()) {
                        actionDone = true;
                    }
                }
            }

            if (!enemy.isAlive()) {
                break;
            }

            /* ENEMY TURN */
            int enemyDamage = enemy.getStrength();
            takeDamage(enemyDamage);

            System.out.println(
                    ConsoleColors.RED +
                            "Enemy attacks and deals " + enemyDamage + " damage." +
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
}
