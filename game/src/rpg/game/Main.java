package rpg.game;

import rpg.entities.Hero;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Hero hero = game.createHero();

        System.out.println();
        System.out.println("Hero created:");
        hero.showDetails();
    }
}
