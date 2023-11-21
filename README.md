# CS611-Assignment 5

## Legend of Valor
Name: Boyang Liu  
Email: liubyfly@bu.edu  
Student ID: U54290758

## Files
```
|-- Main

|-- Help

|-- Pools
|-- Config
|-- Utils

|-- FactoryGoods
|-- FactoryHero
|-- FactoryMonster
|-- FactorySpace

|-- InputScanner
|-- FileReader
|-- TablePrinter
|-- HeroMonsterSelector
|-- RandomMachine
|-- BattleController
|-- Color

|-- GameEntities
   |-- Goods
      |-- Armory
      |-- Weaponry
      |-- Spell
      |-- Potion
   |-- HeroMonster
      |-- Hero
      |-- Monster
      
|-- Space
   |-- SpaceCommon
   |-- SpaceInaccessible
   |-- SpaceMarket

|-- World      
|-- Market
|-- Player

// interface

|-- Movable
|-- Subject
|-- Observer
|-- Battler
|-- Printable
|-- StateInformation
|-- BuyOrSelect
```

## Design Pattern
### Factory Pattern
- Many factory class

### Singleton Pattern
- Market class uses singleton pattern
- Only one market instance guarantees unified resource scheduling

### Observer Pattern
- World is subject, Spaces are observers
- BattleController is subject, heroes and monsters are observers

### Prototype Pattern
- Design classes like GameEntities, Goods and HeroMonster
- Some of them are abstract classes
- which can highly proficiently make use of codes and generate relative subclasses

## Legends_Monsters_and_Heroes

### Color Representation
- Player can choose different colors as their representation
- Space are shown in color other than base board.

### User Interaction
- Easy quit by typing "quit" or "exit", Easy to use 'i' to get states.

### Extendability
- Created interfaces like FactorySpace, FactoryHero which is encapsulated tightly for future use.

### Scalability
- The game can be easily scaled to other sizes.
- Color can be chosen by players.

## Improvement
- dexterity, strength, monsterDamage, etc., can be a VariableAttribute class.


## How to compile and run
1. Navigate to the directory "liubyfly@csa2.bu.edu:~/cs611/hw4/"
2. Put the directory like this:
   ```
   |-- root
      |-- Legends_Monsters_and_Heroes
         |-- Armory.txt
         |-- ...
      |-- src
         |-- Main
         |-- ...
   ```
3. Go to the root directory
4. Run the following instructions:  
   mkdir out  
   javac src/*.java -d out  
   java -cp ./out Main

## Input/Output Example

```
==================== Instr ====================
Please choose a type of hero from:
1. Paladins
2. Sorcerers
3. Warriors
You can enter the full name or use the number.
Or leave it empty to randomly choose a hero.
==================== Instr ====================

You chose Kalabar.
==================== Instr ====================
Please choose a type of hero from:
1. Paladins
2. Sorcerers
3. Warriors
You can enter the full name or use the number.
Or leave it empty to randomly choose a hero.
==================== Instr ====================
1
==================== STATE ====================
You chose Paladins.
+--------------------------------------------------------------------------------------------------+
| No |       Name         |dexterity |starting money |mana |strength |starting experience |agility |
|----+--------------------+----------+---------------+-----+---------+--------------------+--------|
| 1  | Sehanine_Moonbow   |   700    |     2500      |300  |  750    |         7          |  700   |
| 2  | Garl_Glittergold   |   400    |     2500      |100  |  600    |         5          |  500   |
| 3  |Skoraeus_Stonebones |   350    |     2500      |250  |  650    |         4          |  600   |
| 4  |   Caliber_Heist    |   400    |     2500      |400  |  400    |         8          |  400   |
| 5  |     Parzival       |   700    |     2500      |300  |  750    |         7          |  650   |
| 6  |  Amaryllis_Astra   |   500    |     2500      |500  |  500    |         5          |  500   |
+--------------------------------------------------------------------------------------------------+
==================== STATE ====================
```

```
==================== Instr ====================
2
You chose Garl_Glittergold.
+---+---+---+---+---+---+---+---+
| / |   | M |   |   |   |   | M |
+---+---+---+---+---+---+---+---+
| M |   |   | X | M |   |   |   |
+---+---+---+---+---+---+---+---+
|   | M |   | / |   |   |   |   |
+---+---+---+---+---+---+---+---+
| / |   | / | M |   | M |   | / |
+---+---+---+---+---+---+---+---+
| / |   |   |   | / |   |   | / |
+---+---+---+---+---+---+---+---+
|   |   |   | M | / |   |   | / |
+---+---+---+---+---+---+---+---+
| M | / |   |   |   |   | / |   |
+---+---+---+---+---+---+---+---+
|   |   | / | M |   |   | M |   |
+---+---+---+---+---+---+---+---+
==================== Instr ====================
['w', 'a', 's', 'd'] to move;
'i' to show your properties;
'e' to equip weapons or use potions
==================== Instr ====================
```

```
==================== STATE ====================
***** Hero <Reign_Havoc> is taking action.
==================== STATE ====================
==================== Instr ====================
1. Attack, using the hero’s equipped weapon
2. Cast a spell from the hero’s inventory
3. Use a potion from the hero’s inventory
4. Equip a weapon
5. Equip a piece of armor
Please choose the number.
==================== Instr ====================
1
==================== STATE ====================
Monster Natsunomeryu receives 100 damage.
Monster Natsunomeryu: HP = 0.
==================== STATE ====================
Monster Natsunomeryu die!
Congratulations! You win!
==================== STATE ====================
(Hero) Segojan_Earthcaller alive!
Gold: 2500 -> 2700!
Experience: 5 -> 7!
HP: 100 -> 100!
MP: 900 -> 990!
==================== STATE ====================
==================== STATE ====================
(Hero) Reign_Havoc alive!
Gold: 2500 -> 2700!
Experience: 8 -> 10!
HP: 100 -> 100!
MP: 800 -> 880!
==================== STATE ====================
==================== STATE ====================
Congratulations!
Reign_Havoc's level: 1 -> 2!
Now Reign_Havoc has 0 experience.
==================== STATE ====================
-----
Hero: Hero{type=Sorcerers, name=Segojan_Earthcaller, level=1, hp=100, mp=990, gold=2700, experience=7, strength=800, dexterity=650, agility=500}
Your armories:
Your equipped armories:
Your Weaponry:
Your equipped weaponry:
Your spells:
Your potions:
-----
Hero: Hero{type=Sorcerers, name=Reign_Havoc, level=2, hp=100, mp=880, gold=2700, experience=0, strength=840, dexterity=880, agility=880}
Your armories:
Your equipped armories:
Your Weaponry:
Your equipped weaponry:
Your spells:
Your potions:
```
