# Klondike Solitaire (Java)

Play Klondike Solitaire 

- Desktop Application (JavaFX GUI w/ windows installer)

## Desktop Version (Windows)

### Install 
1. Go to **Releases** page of this repository
2. Download the latest file: `KlondikeSolitaire-1.0.msi`
3. Double-click the `.msi` file
4. After installation
- Press the **Windows key**
- Type: `KlondikeSolitaire`
- Click to launch the app

⚠ Windows may show a SmartScreen warning because the installer is unsigned. Click "More info" → "Run anyway" to proceed.

---

## Packaging
This project uses:

- Java Modules
- Maven
- JavaFX
- jlink (custom runtime image)
- jpackage (Windows installer)
- WiX Toolset (MSI builder backend)

The application does not require java to be installed.

## Features
- Object Oriented Design
- Complete Game Logic
- Player Perspective Rendering
- Graphic Interface Design (JavaFX)

## klondike Architecture
| **Components** | **Responsibility** |
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
| **Time** | Counts the start and end time in seconds or minutes. |
| **GameState** | Represents a frozen snapshot of the current game in action. | 
| **Snapshot** | Creates a `GameState` of deep copies each piles `copy()` function. |

## klondike GUI Architecture (JavaFX)
| **Components** | **Responsibility** |
|----------------|--------------------|
| **AnimationManager** | Handles lifting, travel, and placement of cards with Animations |
| **BoardView** | Visual layout of a klondike board using `PileViews`, `GameEngine`, and `Board` |
| **CardSlot** | Empty slot of CardView |
| **CardView** | Visual representation of a playing card (`Card`) |
| **FoundationView** | Extends `PileView` |
| **GameApp** | Runs game application |
| **HowToPlayView** | Scene to read how to play | 
| **MainMenuView** | Handles Main Menu and its button action|
| **MainMenuList** | Displays list of buttons on Main Menu |
| **PileCell** | StackPane layering `CardSlot` and `PileView` |
| **PileView** | Visually displays a pile of `CardViews` |
| **SettingView** | Scene to adjust settings |
| **SideMenu** | Side menu with toggle buttons |
| **StatView** | Scene to view career stats |
| **StockView** | Extends `PileView` |
| **TableauView** | Extends `PileView` |
| **UiMetrics** | Static variables used for consistency while constructing UI nodes and board layout | 
| **WasteView** | Extends `PileView` |

## Tech
- Language: Java (JDK 17)
- UI Framework: JavaFX
- Build Tool: Maven
- Packaging: jlink & jpackage
- Installer Backend: WiX Toolset (Windows MSI)
- Version ControlL Git & Github

## Upcoming Features
- 3 Card Klondike
- Personal Leaderboard
- Different layouts options
- Main menu

## History Progress Log (Project Commenced 16 Sept 2025 - Complete 10 Feb 2026)

### (Oct 20-26, 2025) **started tracking**

- Finished `recycle()` function added a while loop to push cards onto stock until waste is empty 
- Created `Play.java` to start thinking about how Play will interact with `GameEngine`
- Edited what was `topCard()` in tableau class to be called `head()` and added `topCard()` 
function to return the first card in a tab. 
- Finished `canMove()` function which validates moving cards from one tableau to another
- Added `remove(card)` function to Tableau class to be able to remove card from column without
returning a value, like the `draw()` function

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

### (Nov 03 - 09, 2025)
- Constructing user inputs
- Game is playable. Still finding little bugs while testing game out. 
- Added Command Interface to use maps instead of if's. This allows associating
keys with actions. Such as 'x' -> game.draw() inside a map.
- testing and more testing

### (Nov 10 - 16, 2025)
- Added try catch blocks in the asking functions in `Play.java` to validate 
users enter columns within range and don't enter input mismatches 
ex) entering number when asking for foundation. 
- Added Timer functionality with `Time.java`.
- `isGameWon()` tracks tableau's instead of foundations.
- Added `getCards()` functions to each pile class. Returns type list. 
Will be used for creating snapshots for undo function.

### (Nov 17 - 23, 2025)
- Created `GameState.java` to represent a frozen snapshot of the current state 
of the game. 
- Added Copy Constructor in `Card` class.
- Added `copy()` functions to each pile class do perform a deep copy of the list, using 
`Card` copy construtor
- Created `Snapshot.java` to create a `snapshot` of the GameState.
- Created `undo()` functionality in the `GameEngine`. (Debugging)
- Added `InputController.java` to command controller inputs using map and `execute` function.
- Added `TextViews.java` class break up menus and displays throughout the game.
- Breaking up `Play.java` class so its not so cluttered. 

### (Nov 24 - 30, 2025)
- **Completed console version with updated undo, timer, and move count functions.**
- Created `GameApp.java` as main GUI
- Created `CardSlot.java` which is a StackPane representing open card slot and where
cards will go. 
- `CardView.java` is the image visual of my cards with `updateAppearance()` function, 
passing a card object in the constructor, giving it size as well.
- Added a gameengine to the `GameApp` to interact with the board constructing the 
stock, waste, foundations, and tableau rows and dealing a new game, `dealNewGame()`
- Added Clicking ability in `CardView` and `GameApp`.
- Can now click `Stock` to `Waste`. 

### (Dec 1 - 7, 2025)
- Click Waste to select waste, click on destination tableau to add selected card to destination tab.
- Added `GameEngine` field to `GameApp` to use engines moves instead of hardcode.
- Built Tableau -> Tableau move functionality, next is Foundations
- Moved Card labels to topLeft for better visability in tableau stacks.
- Added highlight functionality to selecting and deselecting a tableau card.
- Cards can now move to Foundation piles.

### (Dec 8 - 14, 2025)
- Added background slots to the foundations using `CardSlot` with a Stackpane(slot, `PileView`),
then used the StackPane as the clicker (named `foundationContainer`)
- Implemented `recycle()` to the game on the stockView clicker by checking if stock is not empty, 
clicking will draw card, else recycle, then `redraw()` the pileView
- Made into Maven Project -> added `pom.xml`  
- Implemented Undo button
- Sandboxing the project to explore around in javaFx and learn more. Running into some walls.

### (Dec 15 - 28, 2025)
- Sandbox learning

### (Dec 29, 2025 - Jan 19, 2026) 
- Starting over (x3) 
- Created scene, stage in `GameApp` `start(...)` function
- CardSlot layout
- Cardview layout 
- Debugged `Label` leaving the `CardView` and `CardSlot` due to not setting
max and min sizes on the `StackPane`
- `UiMetrics` class for consistency
- A lot of progress this week was review so got a lot done
- `PileView` is an abstract class that subclasses will extend, such as `TableauView`
- Created `TableauView` and `FoundationView` extending off `PileView`
- Created `StockView` and `WasteView` extending off `PileView`
- Created `BoardView` using the pileviews and `UiMetrics` for custom layout
- Stock to Waste clickable
- Play from Waste to Tableau
- Play from Tableau to Waste by a **single click**
- Yes empty Tableau columns are clickable
- Scrapping waste selection handlers and working on making each click, a single click move
- Might be tough for tableaus, may need source clicks and destination clicks for tab to tab moves
- Added Highlights around selections
- Tab -> Tab `engine` moves function properly using selections
- Foundation -> Tableau on single click
- Tab -> Tab works with single click feature for the foundation moves
- Full Game Playable! **18JAN26** (w/ undo feature)

### (Jan 20 - 31, 2026) -> Focusing on Animations
- Added `getTopCardView()` in `PileView` -> Animation helper function
- Added `popTopCardView()` in `PileView` -> Animation helper function
- Added `pushCardView(CardView cv)` in `PileView` -> Animation helper function
- Added Animation Layer Stock -> Waste using a `Pane` to temporarily place 
`CardView` into third party. After performing animation, goes to where it's suppose to be
which is the `WasteView`. StockView -> AnimationLayerPane -> WasteView
- Created `AnimationManger` class which handles lifting, transporting, and placing 
of `CardViews`. 
- Used `AnimationManager` class to be used in `BoardView` for cleaner architecture
- Added `foundationSplash(node)` to Manager class
- Implemented foundationSplash effects Waste -> Found && Tableau -> Found
- Added Audio Dependency to `Pom.xlm` file
- Added win animation w/ `winPop(Pane, Runnable)` (it's very bugged, need to fix and will eventually)
- Added `shake(node)` feature for clicking waste with no valid moves
- Removed `winPop` and winning animation for now
- Added Timer to screen
- Simple win detection where `showWinUI()` shows a label "YOU WIN" 
and has a Deal New Game button to restart the game.


### (Feb 01 - 10, 2026) -> Focusing an minor expansion ideas
- Added simple sidemenu - needs engine game moves linked to it
- Added runnables to `SideMenu` to link engine moves to the buttons
- Edited `dealNewGame()` function  in `GameEngine` to reset the whole board / 
game state instead of just reshuffling
- Added `doNewGame()` and `doUndo()` functions inside `BoardView` to use
inside `GameApp`, linking those functions to the sideMenu runnables
- Removed dealNewGame button when winPane is shown at the end of a game
- Added SideMenu feature with `deal new game` button
- Reset timer upon starting new game
- Added move count
- Game compiled into java application **10FEB2026**

### (16FEB2026 - Current) -> Making updates on game application
- Added main menu with a start button `MainMenuView.java`
- Added `MainMenuList.java` - the menu that'll be displayed at the main menu
- Added `SettingView.java` as a new scene (scene not complete)
- Added `HowToPlayView.java` as a new scene (scene not complete)
- Added `StatView.java` as a new scene (scene not complete)
