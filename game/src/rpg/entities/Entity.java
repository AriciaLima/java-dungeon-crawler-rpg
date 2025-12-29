package rpg.entities;

public abstract class Entity {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int strength;

    public Entity(String name, int maxHp, int strength) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
           return;
        }
        currentHp -= damage;
        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        currentHp += amount;
        if (currentHp > maxHp) {
            currentHp = maxHp;
        }
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void showDetails() {
        System.out.println("Name: " + name);
        System.out.println("HP: " + currentHp + "/" + maxHp);
        System.out.println("Strength: " + strength);
    }
}
