# Klondike Solitaire (Java)
A fully playable Java implementation of Klondike Solitaire, built from scratch using OOP principles. 
The project is designed to model the full logic of the game, including deck handling, 
pile behaviors, move validation, and board setup. The goal of this project is to expand my 
knowledge and skillset in object oriented principles and designs in Java. I enjoy building card games, 
and plan to build several more in the future.

This project is playable from console.

## How to Run
1. Clone the repository  
2. Open in VS Code  
3. Compile all `.java` files  
4. Run `Play.java` from the terminal  

## Current State of Game
- Pile interface lays out basic pile functions for `Stock` `Waste` `Foundation` `Tableau`
- Terminal logic and testing
- Card/Deck creation, basic pile functionalities and acceptance
- Deal a new board with initial card layout of all tableaus and stock pile
- **Left off at:** Playing and testing for bugs. Looking for minor improvements.
Also adding last move description, in gameengine.
Deep into testing phase.

## Project Management
You can view my Agile board and current development progress here:

[Klondike Agile Board](https://github.com/users/LincolnKoskela/projects/1)

## Features
- Object Oriented Design
Clear seperation of responsibilities across classes and interfaces
- Complete Game Logic
Implements Solitaire's core rules: alternating colors, descending order, foundation suit stacking,
and stock/waste recycling
- Player Perspective Rendering
Each pile supports `toDisplay()` method for player facing views (showing only visible cards)
- Debug friendly
Each object has a `toString()` output for testing and debugging the internal state

## Class Architecture
| **Component** | **Responsibility** |
|----------------|--------------------|
| **Card** | Represents a single playing card (Rank, Suit, and face-up/face-down state). |
| **Pile (Interface)** | Defines common pile operations (`push`, `draw/pop`, `canAccept`, `size`, `clear`). |
| **Stock** | Manages the draw pile (face-down cards, handles recycling). |
| **Waste** | Holds drawn cards (face-up). |
| **Foundation** | Builds up cards by suit from Ace to King. |
| **Tableau** | Manages seven columns where cards build down in alternating color. |
| **Board** | Holds all piles, manages layout, and provides getters and clear/reset functions. |
| **GameEngine** | Controls rules, moves, validations, and overall game progression (`initial deal`, `move`, `checkWin`). |
| **Deck** | Creates and shuffles a standard 52-card deck. |
| **Play** | User plays the game, it's the intermediary between gameengine and player

## Future Roadmap
| **Milestone** | **Description** |
|----------------|--------------------|
| **GameEngine completion** | Full movement management, stock/waste, foudations, and tableau's in fluidity. |
| **Interactive terminal UI** | Add input commands for moves |
| **Graphic interface** | Implement a GUI version. JavaFX, then JS/web | 
| **Save/load system** | Save game state and resume for later |
| **Make CPU strategies** | Make AI's to revert to specific strategies (ex. always select from tab before stock) |
| **Statistical Analysis** | (ex. when x card is played, outcomes of winning changes) |

## Tech
- Langauge: Java
- IDE: VS CODE
- Version Control: Git & Github

## History Progress Log

### This weeks: (Nov 03 - 09, 2025)
- Constructing user inputs
- Game is playable. Still finding little bugs while testing game out. 
- Added Command Interface to use maps instead of if's. This allows associating
keys with actions. Such as 'x' -> game.draw() inside a map.
- testing and more testing
- Added try catch blocks in the asking functions in `Play.java` to validate 
users enter columns within range and don't enter input mismatches 
ex) entering number when asking for foundation. 

### (Oct 26-Nov 02, 2025)
- Added unshuffled deck overload along with gameengine overload to use unshuffled 
decks for more efficient testing. 
- Edited Deck constructor to give the option to shuffle or not, and edited `GameEngine` constructor 
to follow similar structure. This helps with testing out functions
- Added move functions for each pile ex) `moveFoundationToTableau(Card.Suit suit, int dest)`
- Added `remove` function `Foundation.java` and `Waste.java`
- Added `sublist` function to `Tableau.java` for moving sublist around within tabs.
- Added static helper function in board class to format the display better
- Added a players `draw()` function, Stock -> Waste from waste, play the card
- Fixed alignment on `Board.java`
- Fully functional GameEngine complete.

### (Oct 20-26, 2025)

- Finished `recycle()` function added a while loop to push cards onto stock until waste is empty 
- Created `Play.java` to start thinking about how Play will interact with `GameEngine`
- Edited what was `topCard()` in tableau class to be called `head()` and added `topCard()` 
function to return the first card in a tab. 
- Finished `canMove()` function which validates moving cards from one tableau to another
- Added `remove(card)` function to Tableau class to be able to remove card from column without
returning a value, like the `draw()` function

