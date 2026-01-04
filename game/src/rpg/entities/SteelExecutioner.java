package rpg.entities;

import java.util.ArrayList;
import java.util.List;

import rpg.enums.HeroClass;
import rpg.game.ConsoleColors;
import rpg.items.CombatConsumable;
import rpg.items.Consumable;
import rpg.items.MainWeapon;

/**
 * Represents the Steel Executioner hero class.
 * A heavily armored melee fighter that absorbs damage.
 */
public class SteelExecutioner extends Hero {

    private static final int ACTION_NORMAL_ATTACK = 1;
    private static final int ACTION_COMBAT_ITEM = 2;

    public SteelExecutioner(String name, int maxHp, int strength,
                            int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.STEEL_EXECUTIONER;
    }

    @Override
    public boolean attack(NPC enemy) {

        showCombatHeader(enemy);

        while (isAlive() && enemy.isAlive()) {

            if (!enemyTurn(enemy)) {
                return false;
            }

            if (!enemy.isAlive()) {
                break;
            }

            heroTurn(enemy);
        }

        endCombatVictory(enemy);
        return true;
    }

    /* =========================
       ENEMY TURN FIRST
       ========================= */

    private boolean enemyTurn(NPC enemy) {

        int damage = (int) (enemy.getStrength() * 0.8);
        takeDamage(damage);

        System.out.println(ConsoleColors.RED +
                "Enemy strikes for " + damage + " damage." +
                ConsoleColors.RESET);

        System.out.println(ConsoleColors.WHITE +
                "Your HP: " + currentHp + "/" + maxHp +
                ConsoleColors.RESET);

        if (!isAlive()) {
            System.out.println(ConsoleColors.RED + ConsoleColors.BOLD +
                    "YOU WERE DEFEATED." +
                    ConsoleColors.RESET);
            return false;
        }

        return true;
    }

    /* =========================
       HERO TURN
       ========================= */

    private void heroTurn(NPC enemy) {

        while (true) {

            showActionMenu();
            int choice = readIntInRange(1, 4);

            if (choice == 1) {
                normalAttack(enemy);
                return;
            }

            if (choice == 2) {
                System.out.println(
                        ConsoleColors.YELLOW +
                                "This hero has no special attack." +
                                ConsoleColors.RESET
                );
                continue;
            }

            if (choice == 3) {
                if (useCombatConsumable(enemy)) {
                    return;
                }
            }

            if (choice == 4) {
                System.out.println(
                        ConsoleColors.YELLOW +
                                "You cannot use potions during this combat." +
                                ConsoleColors.RESET
                );
            }
        }
    }

    private void normalAttack(NPC enemy) {

        int weaponDamage = mainWeapon != null ? mainWeapon.getAttack() : 0;
        int damage = strength + weaponDamage;

        enemy.takeDamage(damage);

        System.out.println(ConsoleColors.GREEN +
                "You crush the enemy for " + damage + " damage." +
                ConsoleColors.RESET);

        showEnemyHp(enemy);
    }

    /* =========================
       COMBAT CONSUMABLES
       ========================= */

    private boolean useCombatConsumable(NPC enemy) {

        List<CombatConsumable> items = getCombatConsumables();

        if (items.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW +
                    "You have no combat consumables." +
                    ConsoleColors.RESET);
            return false;
        }

        showCombatConsumableMenu(items);
        int choice = readIntInRange(1, items.size() + 1);

        if (choice == items.size() + 1) {
            return false;
        }

        CombatConsumable item = items.get(choice - 1);
        enemy.takeDamage(item.getInstantAttackDamage());
        inventory.remove(item);

        System.out.println(ConsoleColors.GREEN +
                "You used " + item.getName() +
                " dealing " + item.getInstantAttackDamage() + " damage." +
                ConsoleColors.RESET);

        showEnemyHp(enemy);
        return true;
    }

    private List<CombatConsumable> getCombatConsumables() {

        List<CombatConsumable> result = new ArrayList<>();

        for (Consumable c : inventory) {
            if (c instanceof CombatConsumable) {
                result.add((CombatConsumable) c);
            }
        }

        return result;
    }

    private void showCombatConsumableMenu(List<CombatConsumable> items) {

        System.out.println("Combat consumables:");

        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + " - " + items.get(i).getName());
        }

        System.out.println((items.size() + 1) + " - Cancel");
    }

    /* =========================
       COMBAT START AND END
       ========================= */

    private void showCombatHeader(NPC enemy) {

        System.out.println();
        System.out.println(ConsoleColors.PURPLE + ConsoleColors.BOLD +
                "=========================\n" +
                "     EXECUTION COMBAT    \n" +
                "=========================" +
                ConsoleColors.RESET);

        System.out.println(ConsoleColors.YELLOW +
                "Enemy: " + enemy.getName() +
                ConsoleColors.RESET);
    }

    private void endCombatVictory(NPC enemy) {

        System.out.println();
        System.out.println(ConsoleColors.GREEN + ConsoleColors.BOLD +
                "ENEMY DEFEATED!" +
                ConsoleColors.RESET);

        addGold(enemy.getGold());
        levelUp();
    }
}
