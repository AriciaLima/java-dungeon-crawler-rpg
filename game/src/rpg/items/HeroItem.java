package rpg.items;
import java.util.List;
import rpg.enums.HeroClass;

public abstract class HeroItem {
    protected String name;
    protected int price;
    protected List<HeroClass> allowedHeroes;

    public HeroItem(String name, int price, List<HeroClass> allowedHeroes) {
        this.name = name;
        this.price = price;
        this.allowedHeroes = allowedHeroes;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public List<HeroClass> getAllowedHeroes() {
        return allowedHeroes;
    }
    public boolean canBeUsedBy(HeroClass heroClass) {
        return allowedHeroes == null || allowedHeroes.isEmpty() || allowedHeroes.contains(heroClass);
    }
    public void showDetails() {
        System.out.print(name + " | Price: " + price + " gold");
    }
}
