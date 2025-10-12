# Klondike Solitaire (Java)
A fully object-oriented implementation of Klondike Solitaire written in Java. 
The project is designed to model the full logic of the game, including deck handling, 
pile behaviors, move validation, and board setup. 

This version runs on the console. 

## Current State of Game
- Console-based logic and testing
- Card/Deck creation, basic pile functionalities and acceptance
- Latest edits -- Fixed bug issues that weren't allowing certain functions to be called. 
Issue resolved by cloning the project into new folder. 
- **Currently** working on adding to gameEngine basic functionalities and will get into more complex logic as I build upon the class. Also need gameEngine to deal inital board to test Boards display. 

## Todays Progress
- Working through the initial deal function `dealNewGame()` in the game engine. 

## Features
- Object Oriented Design
Clear seperation of responsibilities across classes and interfaces
- Complete Game Logic
Implements Solitaire's core rules: alternating colors, descending order, foundation suit stacking,
and stock/waste recycling
- Encapsulation and Extensibility
Designed for easy expansion to GUI or web environments.
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
| **GameEngine** | Controls rules, moves, validations, and overall game progression (`deal`, `move`, `checkWin`). |
| **Deck** | Creates and shuffles a standard 52-card deck. |

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