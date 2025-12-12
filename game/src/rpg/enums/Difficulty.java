package rpg.enums;

public enum Difficulty {
    NORMAL(1.0, 1.0), HARD(1.3, 1.2);

    private final double enemyHpMultiplier;
    private final double enemyStrengthMultiplier;

    Difficulty(double  enemyHpMultiplier, double enemyStrengthMultiplier) {
        this.enemyHpMultiplier =  enemyHpMultiplier;
        this.enemyStrengthMultiplier = enemyStrengthMultiplier;
    }

    public double getEnemyHpMultiplier() {
        return enemyHpMultiplier;
    }

    public double getEnemyStrengthMultiplier() {
        return enemyStrengthMultiplier;
    }
}
