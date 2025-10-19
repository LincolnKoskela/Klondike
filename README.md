# Klondike Solitaire (Java)
A fully object-oriented implementation of Klondike Solitaire written in Java. 
The project is designed to model the full logic of the game, including deck handling, 
pile behaviors, move validation, and board setup. 

This version runs on the console. 

## Current State of Game
- Pile interface lays out basic pile functions for `Stock` `Waste` `Foundation` `Tableau`
- Console-based logic and testing
- Card/Deck creation, basic pile functionalities and acceptance
- Deal a new board with initial card layout of all tableaus and stock pile
- **Currently** finished debugging `dealNewGame()` function. Working on recycle function.
Need to push list of cards -> stock pile from waste. push waste draw stock. Move must 
be validated. 

## Todays Progress 10/19/25
- Debugged Boards output. Alignment fixed itself and is good for now but expecting alignment issues with further testing
as cards move around the piles. Started waste/stock recycle function. 
- In gameEngine main, we did couple sanity checks for cards distribution because 29
cards we're going to the tableau and 23 to stock. Had to edit the start and end to the for loop. 

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