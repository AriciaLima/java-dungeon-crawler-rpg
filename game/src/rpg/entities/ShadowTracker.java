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
 * Represents the Shadow Tracker hero class.
 * Agile hunter that strikes first and relies on critical hits.
 * Takes 10% more damage due to light armor.
 */
public class ShadowTracker extends Hero {

    private final Random random = new Random();

    /**
     * Constructor for ShadowTracker.
     *
     * @param name       The hero's name
     * @param maxHp      The hero's max HP
     * @param strength   The hero's strength
     * @param level      The hero's level
     * @param gold       The hero's starting gold
     * @param mainWeapon The hero's starting weapon
     */
    public ShadowTracker(String name, int maxHp, int strength,
                         int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    /**
     * Returns the hero class enum.
     *
     * @return HeroClass.SHADOW_TRACKER
     */
    @Override
    public HeroClass getHeroClass() {
        return HeroClass.SHADOW_TRACKER;
    }

    /**
     * Combat logic for the Shadow Tracker.
     *
     * Rules:
     * - Hero attacks first
     * - 20% chance to deal double damage (critical hit)
     * - Special attack usable once per combat
     * - Takes 10% more damage from enemies
     *
     * @param enemy The enemy NPC
     * @return true if the hero wins, false otherwise
     */
    @Override
    public boolean attack(NPC enemy) {

        boolean specialUsed = false;

        System.out.println();
        System.out.println(
                ConsoleColors.PURPLE + ConsoleColors.BOLD +
                        "=========================\n" +
                        "      SHADOW COMBAT      \n" +
                        "=========================" +
                        ConsoleColors.RESET
        );

        System.out.println(
                ConsoleColors.YELLOW +
                        "Enemy: " + enemy.getName() +
                        ConsoleColors.RESET
        );
        showEnemyHp(enemy);

        while (this.isAlive() && enemy.isAlive()) {

            boolean actionDone = false;

            /* HERO TURN */
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

                    int damage = strength + mainWeapon.getAttack();

                    if (random.nextDouble() < 0.2) {
                        damage *= 2;
                        System.out.println(
                                ConsoleColors.YELLOW + ConsoleColors.BOLD +
                                        "CRITICAL HIT!" +
                                        ConsoleColors.RESET
                        );
                    }

                    enemy.takeDamage(damage);

                    System.out.println(
                            ConsoleColors.GREEN +
                                    "You deal " + damage + " damage." +
                                    ConsoleColors.RESET
                    );

                    showEnemyHp(enemy);
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
                                    "You unleash a special attack dealing " + damage + " damage!" +
                                    ConsoleColors.RESET
                    );

                    showEnemyHp(enemy);
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

                    showEnemyHp(enemy);
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

            /* ENEMY TURN (10% extra damage) */
            int enemyDamage = (int) (enemy.getStrength() * 1.1);
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
