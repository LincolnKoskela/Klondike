# Klondike Solitaire (Java)
A fully object-oriented program of Klondike Solitaire written in Java. 
The project is designed to model the full logic of the game, including deck handling, 
pile behaviors, move validation, and board setup. 

## Weeks Progress 10/20/2025 - 10/26/2025
- Finished `recycle()` function added a while loop to push cards onto stock until waste is empty 
- Created `Play.java` to start thinking about how Play will interact with `GameEngine`
- Edited what was `topCard()` in tableau class to be called `head()` and added `topCard()` 
function to return the first card in a tab. 
- Finished `canMove()` function which validates moving cards from one tableau to another
- Added `remove(card)` function to Tableau class to be able to remove card from column without
returning a value, like the `draw()` function
- Added `isGameWon()` to GameEngine
- Finished `move()` function

## Needs Testing 
- `canMove()` GameEngine
- `recycle()` GameEngine
- `move()` GameEngine
- `remove()` Tableau
- `foundationsComplete()` Board
- `isGameWon()` Board & GameEngine

## Current State of Game
- Pile interface lays out basic pile functions for `Stock` `Waste` `Foundation` `Tableau`
- Terminal logic and testing
- Card/Deck creation, basic pile functionalities and acceptance
- Deal a new board with initial card layout of all tableaus and stock pile
- **Currently** Need to test all your `needs testing` functions

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
| **Interactive terminal UI** | Add input commands for moves (ex. `move t1 fSpades`) |
| **Graphic interface** | Implement a GUI version. Swing/JavaFX or JS/web | 
| **Save/load system** | Save game state and resume for later |

## Tech
- Langauge: Java
- IDE: VS CODE
- Version Control: Git & Github