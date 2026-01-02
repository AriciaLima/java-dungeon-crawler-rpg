# 🗡️ Oathbound Depths

> A dark fantasy, text based RPG dungeon crawler built in Java

---

## 📖 Story

Beneath the quiet village of **Blackstone** lies a forgotten dungeon sealed by ancient oaths.

For years, nothing stirred.

Now, cursed lights glow in the tunnels.  
Hunters vanish.  
Screams echo through stone.

At the heart of the depths stands a fallen legend  
a hunter who broke a sacred contract and became the dungeon itself.

You are a bounty hunter of the Guild.

Your mission is simple.

**Enter the depths.  
End the curse.  
Survive.**

---

## 🎯 Project Goal

This project was developed as a **practical assignment for the Object Oriented Programming module** of the Software Developer course.

The objective is to apply OOP principles while building a fully playable RPG experience with:

- Turn based combat
- Inventory and vendor systems
- Narrative driven progression
- Player choices and branching paths

---

## 🧱 Core Concepts Used

- Object Oriented Programming
- Abstraction and inheritance
- Polymorphism
- Encapsulation
- Java Collections
- Randomized mechanics
- Console based user interaction

---

## 🧙 Hero Classes

The player can choose between **three distinct hero archetypes**, each with unique combat behavior.

### 🛡️ Steel Executioner
- Heavy armored melee fighter
- Enemy attacks first
- Receives **20% less damage**
- Reliable and durable

### 🗡️ Shadow Tracker
- High damage assassin
- Chance to land **critical hits**
- Receives **10% more damage**
- Risk and reward playstyle

### 🏹 Oathbound Hunter
- Balanced ranged combatant
- Consistent damage output
- Standard combat flow
- Adaptable and steady

All heroes have:
- Level progression
- Gold
- A main weapon
- An inventory of consumables

---

## ⚔️ Combat System

Combat is **turn based** and continues until one side reaches zero HP.

Before each attack, the player may choose:

1. **Normal Attack**  
   Strength + weapon attack

2. **Special Attack**  
   Strength + weapon special attack  
   Can only be used **once per fight**

3. **Combat Consumable**  
   Instant damage item  
   Removed from inventory after use

4. **Potion**  
   Restores health and or increases strength

### Victory Rewards
When the hero wins:
- Gains enemy gold
- Levels up
- Maximum HP increases
- Strength increases

---

## 🧪 Items and Inventory

The game features multiple item types:

- **Main Weapons**
- **Combat Consumables**
- **Potions**

Potions never exceed the hero’s maximum HP and warn the player when healing would overflow.

---

## 🧑‍💼 Vendor System

A traveling vendor appears in key locations.

Vendor features:
- Displays **10 random items** per visit
- Inventory changes every time
- Checks hero class compatibility
- Requires sufficient gold
- Buying a weapon replaces the current one

---

## 🗺️ Dungeon Structure

The dungeon is composed of multiple rooms including:

- Story rooms
- Combat encounters
- Vendor rooms
- Branching paths
- A final boss chamber

The player must make decisions that affect the path taken through the dungeon.

---

## 🎲 Random Mechanics

The `Random` class is used in multiple systems:

- Random enemies
- Random shop inventory
- Combat damage variation
- Event outcomes

This ensures replayability and unpredictability.

---

## ▶️ How to Run

1. Clone the repository
2. Open the project in your IDE
3. Run `Main.java`
4. Follow the console instructions

---

## 🛠️ Technologies

- Java
- Java Collections Framework
- Scanner for input handling
- Random for game logic
- ANSI escape codes for colored console output

---

## 🚧 Future Improvements

- Random event rooms
- Multiple enemies per encounter
- Replay options after defeat
- Save and load system
- Additional hero classes

---

## 👩‍💻 Author

**Arícia Farias Lima**  
Software Developer Student

---

> *“End the curse.  
Or fall with it.”*
