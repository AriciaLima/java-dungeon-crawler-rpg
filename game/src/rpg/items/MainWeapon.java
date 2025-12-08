package rpg.items;
import java.util.List;

public class MainWeapon {

    private String name;
    private int price;
    private int attack;
    private int specialAttack;
    private List<String> allowedHeroes;

    public MainWeapon(String name,int price,int attack, int specialAttack, List<String> allowedHeroes) {
        this.name = name;
        this.price = price;
        this.attack = attack;
        this.specialAttack = specialAttack;
        this.allowedHeroes = allowedHeroes;
    }

    //Getters

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public List<String> getAllowedHeroes() {
        return allowedHeroes;
    }

    //Methods

    public boolean canBeUsedBy(String heroTypeName){
        return allowedHeroes.contains(heroTypeName);
    }

    public void showDetails(){
        System.out.println(name +
                " | Attack: " + attack +
                " | Special: " + specialAttack +
                " | Price: " + price + " gold");
    }

}
