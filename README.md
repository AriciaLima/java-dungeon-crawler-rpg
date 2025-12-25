# Java Dungeon Crawler RPG

Project developed as part of the Object Oriented Programming module of the Software Developer course.

This project is a console based RPG developed in Java, where the player creates a hero and explores a dungeon full of choices, enemies and random events, with the goal of surviving and ending the final threat.

## Game Concept

The game takes place in the village of Blackstone, built over ancient ruins known as the Oathbound Depths. A former hunter from the Guild disappeared inside the dungeon and became part of the curse that now threatens the village.

The player assumes the role of a bounty hunter from the Guild and accepts a contract to enter the dungeon, face enemies and put an end to the curse.

## Main Features

1. Interactive character creation through the console  
2. Hero class selection  
3. Difficulty selection  
4. Distribution of creation points between health and strength  
5. Turn based combat system  
6. Item and inventory system  
7. Vendor with random stock  
8. Dungeon with multiple rooms and choices  
9. Use of randomness in events and encounters  
10. Win and lose conditions  
11. Option to replay the game after it ends  

## Hero Classes

The player can choose between three hero types, each with different combat behavior.

Steel Executioner  
A melee focused hero with higher resistance.

Shadow Tracker  
An agile and stealth oriented hero with fast combat style.

Oathbound Hunter  
A strategic hero linked to oaths and contracts.

## Character Creation System

All heroes start with base attributes.

Base health 50  
Base strength 5  

After choosing the difficulty, the player receives creation points.

Easy mode  
10 creation points  
20 starting gold  

Hard mode  
7 creation points  
15 starting gold  

Each creation point can be spent as follows.

1 point in health gives +5 HP  
1 point in strength gives +1 Strength  

All creation points must be spent before continuing.

## Combat System

Combat is turn based between the hero and an NPC.

On each hero turn, the player can choose.

Normal attack  
Special attack from the main weapon, usable only once per combat  
Combat consumable attack  

Combat ends when one entity reaches zero health.

If the hero wins, they gain gold, level up and improve their attributes.

## Items

The game includes a complete item system.

Main Weapons  
Increase hero damage and provide a special attack.

Combat Consumables  
Deal instant damage to enemies and are removed after use.

Potions  
Restore health and may increase strength.

There is at least one universal potion that restores 25 health points and can be used by all hero classes.

## Vendor

A vendor appears at the beginning of the game and in specific dungeon rooms.

The vendor has a larger inventory, but only 10 items are displayed randomly per visit.

The system validates.

Available gold  
Allowed hero class  
Replacement of the main weapon when applicable  

## Dungeon and Rooms

The dungeon is composed of several connected rooms.

There are at least six rooms and a minimum of three different choices throughout the game.

Rooms may contain.

Enemies  
Vendor  
Lost items  
Gold  
Traps  
Random events  

At the end of each room, the player can use potions or continue forward.

## Use of Randomness

The Random library is used in multiple contexts, such as.

Risk and reward events  
Number of enemies per room  
Random vendor stock  
Hidden item discovery  

## Win and Lose Conditions

Victory  
The player survives the dungeon and defeats the final threat.

Defeat  
The hero health reaches zero due to combat, traps or events.

After the game ends, the player can choose to.

Play again with the same hero  
Create a new hero  
Exit the game  

## Package Structure

rpg.entities  
Contains all game entities such as Hero, NPC and subclasses.

rpg.items  
Contains all items usable by the hero.

rpg.game  
Contains the main game logic, including Game and Main classes.

rpg.enums  
Contains helper enums such as Difficulty, HeroClass and Rooms.

## Running the Project

1. Open the project in a Java compatible IDE  
2. Run the Main class  
3. Follow the instructions displayed in the console  

## Technologies Used

Java  
Object Oriented Programming  
Java Random  
Java Collections  

## Author

Arícia Farias Lima  

Academic project developed for evaluation in the Software Developer course.
