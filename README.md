# Oathbound Depths

A text-based Dungeon Crawler RPG developed in Java.
In this game, you play as a Guild hunter exploring the cursed depths of Blackstone to break an ancient curse.

## How to Run

1.  **Prerequisites**:
    *   Java Development Kit (JDK) installed (version 8 or higher).
    *   A terminal or command prompt.

2.  **Compilation**:
    Navigate to the `game/src` directory:
    ```bash
    cd game/src
    ```
    Compile all Java files:
    ```bash
    javac rpg/game/Main.java
    ```

3.  **Execution**:
    Run the `Main` class:
    ```bash
    java rpg.game.Main
    ```

## Game Rules

### Character Creation
*   **Classes**:
    *   **Steel Executioner**: High HP, heavy armor (reduces damage taken), starts with a Greatsword.
    *   **Shadow Tracker**: High damage, critical hits, but takes more damage. Starts with Dual Daggers.
    *   **Oathbound Hunter**: Balanced stats, uses a Bow.
*   **Attributes**:
    *   **HP (Health Points)**: If it reaches 0, the game is over.
    *   **Strength**: Adds to your weapon damage.
*   **Creation Points**: You can distribute points between HP and Strength at the start.

### Exploration
*   The game consists of several rooms (nodes).
*   You will face choices at each step (e.g., choosing a path, interacting with vendors).
*   Some paths lead to combat, others to safe havens.

### Combat
*   Combat is turn-based.
*   **Actions**:
    1.  **Normal Attack**: Basic damage based on Strength + Weapon.
    2.  **Special Attack**: Higher damage, but may have limitations or cooldowns (depending on implementation).
    3.  **Combat Consumable**: Use items like Bombs or Throwing Knives for instant effects.
    4.  **Potion**: Heal yourself or boost stats.
*   **Loot**: Defeating enemies grants Gold and Experience (Level Up).

### Economy
*   **Gold**: Earned by defeating monsters.
*   **Vendor**: You can find a wandering merchant to buy better weapons, potions, and combat items.

### Goal
Defeat "The Broken Oath" in the final chamber to lift the curse and win the game.

## Project Structure

*   `rpg.game`: Contains the main game loop and logic.
*   `rpg.entities`: Contains Hero classes, Enemy NPCs, and Vendor logic.
*   `rpg.items`: Contains Weapons, Potions, and Consumables.
*   `rpg.enums`: Contains enumerations for Game State, Difficulty, etc.

---
*Developed by [Your Name/ID]*
