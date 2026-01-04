package rpg.entities;

/**
 * Abstract base class that represents any living entity in the game.
 */
public abstract class Entity {

    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int strength;

    public Entity(String name, int maxHp, int strength) {
        this.name = name;
        this.maxHp = maxHp;
        this.strength = strength;
        this.currentHp = maxHp;
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
            return;
        }
        currentHp = Math.max(currentHp - damage, 0);
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        currentHp = Math.min(currentHp + amount, maxHp);
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return "Name: " + name +
                "\nHP: " + currentHp + "/" + maxHp +
                "\nStrength: " + strength;
    }
}
