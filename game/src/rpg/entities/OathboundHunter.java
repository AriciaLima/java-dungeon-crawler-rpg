package rpg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rpg.enums.HeroClass;
import rpg.game.ConsoleColors;
import rpg.items.CombatConsumable;
import rpg.items.Consumable;
import rpg.items.MainWeapon;

/**
 * Represents the Oathbound Hunter hero class.
 * A disciplined hunter bound by ancient contracts.
 */
public class OathboundHunter extends Hero {

    private static final int ACTION_NORMAL_ATTACK = 1;
    private static final int ACTION_SPECIAL_ATTACK = 2;
    private static final int ACTION_COMBAT_ITEM = 3;
    private static final int ACTION_POTION = 4;

    private final Random random = new Random();

    public OathboundHunter(String name, int maxHp, int strength,
                           int level, int gold, MainWeapon mainWeapon) {
        super(name, maxHp, strength, level, gold, mainWeapon);
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.OATHBOUND_HUNTER;
    }

    /**
     * Executes the full combat loop against an enemy.
     */
    @Override
    public boolean attack(NPC enemy) {

        boolean specialUsed = false;

        showCombatHeader(enemy);

        while (isAlive() && enemy.isAlive()) {

            if (!heroTurn(enemy, specialUsed)) {
                specialUsed = true;
            }

            if (!enemy.isAlive()) {
                break;
            }

            if (!enemyTurn(enemy)) {
                return false;
            }
        }

        endCombatVictory(enemy);
        return true;
    }

    /* =========================
       HERO TURN
       ========================= */

    private boolean heroTurn(NPC enemy, boolean specialUsed) {

        while (true) {

            showActionMenu();
            int choice = readIntInRange(1, 4);

            if (choice == ACTION_NORMAL_ATTACK) {
                normalAttack(enemy);
                return true;
            }

            if (choice == ACTION_SPECIAL_ATTACK) {
                if (specialUsed) {
                    System.out.println(ConsoleColors.YELLOW +
                            "Special attack already used." +
                            ConsoleColors.RESET);
                    continue;
                }
                specialAttack(enemy);
                return false;
            }

            if (choice == ACTION_COMBAT_ITEM) {
                if (useCombatConsumable(enemy)) {
                    return true;
                }
            }

            if (choice == ACTION_POTION) {
                if (usePotion()) {
                    return true;
                }
            }
        }
    }

    private void normalAttack(NPC enemy) {

        int weaponDamage = mainWeapon != null ? mainWeapon.getAttack() : 0;
        int variance = random.nextInt(3);
        int damage = strength + weaponDamage + variance;

        enemy.takeDamage(damage);

        System.out.println(ConsoleColors.GREEN +
                "You strike for " + damage + " damage." +
                ConsoleColors.RESET);

        showEnemyHp(enemy);
    }

    private void specialAttack(NPC enemy) {

        int weaponDamage = mainWeapon != null ? mainWeapon.getSpecialAttack() : 0;
        int damage = strength + weaponDamage;

        enemy.takeDamage(damage);

        System.out.println(ConsoleColors.GREEN + ConsoleColors.BOLD +
                "You invoke your oath and deal " + damage + " special damage!" +
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
       ENEMY TURN
       ========================= */

    private boolean enemyTurn(NPC enemy) {

        int damage = enemy.getStrength();
        takeDamage(damage);

        System.out.println(ConsoleColors.RED +
                "Enemy attacks and deals " + damage + " damage." +
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
       COMBAT START AND END
       ========================= */

    private void showCombatHeader(NPC enemy) {

        System.out.println();
        System.out.println(ConsoleColors.PURPLE + ConsoleColors.BOLD +
                "=========================\n" +
                "     OATHBOUND COMBAT    \n" +
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
