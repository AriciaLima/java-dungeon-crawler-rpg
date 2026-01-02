# Oathbound Depths

Text based RPG dungeon crawler developed in Java.

## Project Overview

Oathbound Depths is a console based role playing game created as a practical project for the Object Oriented Programming module of the Software Developer course.

The player assumes the role of a hero hired by the Guild of Blackstone to descend into a cursed dungeon, face enemies, make decisions, manage inventory and defeat a corrupted legendary hunter known as The Broken Oath.

The project focuses on clean object oriented design, turn based combat, inventory management and narrative progression.

## Objectives

1. Apply Object Oriented Programming principles
2. Use abstraction inheritance and polymorphism
3. Implement a turn based combat system
4. Manage items inventory and vendors
5. Create a dungeon with choices and narrative
6. Ensure the program runs without compilation errors
7. Generate JavaDoc documentation

## Project Structure

src  
rpg  
entities  
Entity  
Hero  
SteelExecutioner  
ShadowTracker  
OathboundHunter  
NPC  
Vendor  

items  
HeroItem  
MainWeapon  
Consumable  
CombatConsumable  
Potion  

enums  
HeroClass  
Difficulty  
Rooms  

game  
Game  
Main  
ConsoleColors  

## Hero Classes

The game offers three playable hero classes with distinct combat behavior.

Steel Executioner  
High durability hero. The enemy attacks first and deals reduced damage due to armor.

Shadow Tracker  
High damage assassin with critical hit chance. Receives more damage due to low protection.

Oathbound Hunter  
Balanced ranged fighter with consistent damage and standard combat flow.

All heroes have level progression gold a main weapon and an inventory of consumables.

## Combat System

Combat is turn based and continues until either the hero or the enemy reaches zero health.

Before attacking the player can choose:

1. Normal Attack  
Hero strength plus weapon attack

2. Special Attack  
Hero strength plus weapon special attack  
This attack can only be used once per fight

3. Combat Consumable  
Instant damage item removed from inventory after use

4. Potion  
Heals health and or increases strength

When the hero wins a fight they gain enemy gold level up increase maximum health and strength.

## Vendor System

The vendor system allows the hero to buy items during the adventure.

Features:

1. Only ten random items are displayed per visit
2. Items include weapons combat consumables and potions
3. The hero must have enough gold
4. The item must be compatible with the hero class
5. Buying a weapon replaces the current one

## Dungeon and Narrative

The adventure begins in the Hunters Hall of Blackstone where the hero receives the contract.

The dungeon contains multiple rooms including:

1. Story driven rooms
2. Combat rooms
3. Vendor rooms
4. Branching paths
5. A final boss room

Narrative text guides the player through the story and choices affect progression.

## Random Mechanics

The Random class is used in multiple contexts:

1. Random enemy encounters
2. Random vendor inventory
3. Combat damage variation
4. Event based outcomes

## How to Run

1. Clone the repository
2. Open the project in an IDE
3. Run Main.java
4. Follow the console instructions

## Technologies Used

Java  
Object Oriented Programming  
Java Collections  
Scanner for input  
Random for game mechanics  
ANSI escape codes for colored console output

## Project Status

The game is fully playable from start to finish and meets all mandatory requirements of the assignment.

## Future Improvements

1. Random event rooms
2. Multiple enemies per room
3. Replay options after defeat
4. Save and load system
5. Additional hero classes

## Author

Arícia Farias Lima  
Software Developer Student
