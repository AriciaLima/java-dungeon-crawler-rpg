package rpg.entities;

/**
 * Represents a non playable character in the game.
 * Usually enemies or hostile entities that can be defeated
 * and reward the hero with gold.
 */
public class NPC extends Entity {

    protected int gold;
    protected String description;

    public NPC(String name, int maxHp, int strength, int gold, String description) {
        super(name, maxHp, strength);
        this.gold = Math.max(gold, 0);
        this.description = description;
    }

    public int getGold() {
        return gold;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns a formatted string with NPC details.
     *
     * @return string containing NPC information
     */
    @Override
    public String getDetails() {

        StringBuilder details = new StringBuilder();

        details.append(super.getDetails());
        details.append("\nGold: ").append(gold);

        if (description != null && !description.isBlank()) {
            details.append("\nDescription: ").append(description);
        }

        return details.toString();
    }
}
