package rpg.entities;

public class NPC extends Entity {
    protected int gold;
    protected String description;

    public NPC(String name, int maxHp, int strength, int gold, String description) {
        super(name, maxHp, strength);
        this.gold = gold;
        this.description = description;
    }

    public int getGold() {
        return gold;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Gold: " + gold);
        if (description != null && !description.isBlank()) {
            System.out.println("Description: " + description);

        }
        System.out.println("----------------------");
    }
}

