package klondike.ui;
import klondike.*;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

/**
 * JavaFX Launcher
 */
public class GameApp extends Application {

    // ---------- fields -----------------
    private static GameEngine engine;
    private static PileView selectedWasteView = null; // selecting TOPCARD of waste pile

    private static int selectedSourceCol = -1; // 1 - 7
    private static int selectedSourceRow = -1; // rows down in col
    private static HBox tableauRowRef; 
    private static PileView stockViewRef;
    private static PileView wasteViewRef;
    private static HBox foundationRowRef;


    // ---------- helpers -----------------
    public static int getSelectedSourceCol() {
        return selectedSourceCol;
    }
    public static GameEngine getEngine() {
        return engine;
    }
    public static int getSelectedSourceRow() {
        return selectedSourceRow;
    }
    public static PileView getStockView() {
        return stockViewRef;
    }
    public static PileView getWasteView() {
        return wasteViewRef;
    }
    public static void setTableauRow(HBox row) {
        tableauRowRef = row;
    }
    public static boolean isWasteSelected() {
        return selectedWasteView != null;
    }
    public static void clearSelection() {
        selectedSourceCol = -1;
        selectedSourceRow = -1;
    }
    public static boolean gameWon() {
        return engine.isGameOver();
    }

    private static void redrawAllTableaus() {
        if (tableauRowRef == null) return;

        for (Node node : tableauRowRef.getChildren()) {
            if (node instanceof PileView pv) {
                pv.redraw();
            }
        }
    }

    private static void redrawAllFoundations() {
        if (foundationRowRef == null) return;

        for (Node node : foundationRowRef.getChildren()) {
            if (node instanceof PileView pv) {
                pv.redraw();
            }
        }
    }
    // --------------------------------------



    // ---------- Start ---------------
    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();

        engine = new GameEngine(true);
        engine.dealNewGame();
        Board board = engine.getBoard();



        // ----- StockWaste Pile *****
        HBox stockWaste = new HBox(50);
        PileView stockView = new PileView(board.getStock(), 0);
        PileView wasteView = new PileView(board.getWaste(), 0);

        stockViewRef = stockView;
        wasteViewRef = wasteView;

        // undo button
        Button undo = new Button("Undo");
        undo.setPrefWidth(80);
        HBox bottomBar = new HBox(40);
        bottomBar.setAlignment(Pos.BOTTOM_LEFT);
        bottomBar.setPadding(new Insets(0, 0, 20, 20));
        bottomBar.getChildren().addAll(stockWaste, undo);

        // click: move one card from stock -> waste
        stockView.setOnMouseClicked(e -> {
            Card drawn = board.getStock().peek();
            if (!board.getStock().isEmpty()) {
                if (drawn != null) engine.draw();
            } else {
                engine.recycle();
            }

            stockView.redraw();
            wasteView.redraw();
        });


        wasteView.setOnMouseClicked(e -> {
            // if the waste is empty
            if (board.getWaste().isEmpty()) {
                if (selectedWasteView != null) {
                    selectedWasteView.setStyle(""); // removes highlight
                }
                selectedWasteView = null;
                return;
            }

            // if clicked waste again -> deselect
            if (selectedWasteView == wasteView) { // pointing to same object references in mem
                wasteView.setStyle(""); 
                selectedWasteView = null;
                return;
            }

            // select waste
            if (selectedWasteView != null) {
                selectedWasteView.setStyle(""); // clears previous waste selection
            }

            wasteView.setStyle("-fx-border-color: gold; -fx-border-width: 3;");
            selectedWasteView = wasteView;

            System.out.println("Selected waste card: " + board.getWaste().topCard());
        });

        StackPane stockCell = new StackPane(new CardSlot("STOCK"), stockView);
        StackPane wasteCell = new StackPane(new CardSlot("WASTE"), wasteView);

        stockCell.setAlignment(Pos.CENTER);
        wasteCell.setAlignment(Pos.CENTER);
        stockWaste.getChildren().addAll(stockCell, wasteCell);


        // ----- Foundations -----
        HBox foundationRow = new HBox(50); 
        foundationRowRef = foundationRow;
        foundationRow.setAlignment(Pos.TOP_LEFT);

        
        for (Card.Suit suit : Card.Suit.values()) {
            Foundation f = board.getFoundation(suit);
            PileView view = new PileView(f, 0); 

            CardSlot slot = new CardSlot();

            StackPane foundationContainer = new StackPane(slot, view);
            

            Card.Suit suitCopy = suit;

            foundationContainer.setOnMouseClicked(e -> {


                // Waste -> Foundation
                if (selectedWasteView != null) {
                    int beforeMoves = engine.getMoveCount();
                    engine.moveWastetoFoundation(suitCopy);

                    if (engine.getMoveCount() > beforeMoves) { // move succeeded
                        
                        if(wasteViewRef != null) {
                            wasteViewRef.redraw();
                        }

                        view.redraw();
                        System.out.println("Moved from Waste to Foundation " + suitCopy);
                    } else {
                        System.out.println("Invalid move from Waste to Foundation " + suitCopy);
                    }


                    // clear waste selections & highlights
                    selectedWasteView.setStyle("");
                    selectedWasteView = null;

                    e.consume();
                    return;
                }

                // Tableau -> Foundation
                if (selectedSourceCol != -1) {
                    int beforeMoves = engine.getMoveCount();
                    engine.moveTableauToFoundation(selectedSourceCol, suitCopy);

                    if (engine.getMoveCount() > beforeMoves) { // move succeeded
                        redrawAllTableaus();
                        view.redraw();
                        System.out.println("Moved from Tableau " + selectedSourceCol
                            + " to Foundation " + suitCopy
                        );
                    } else {
                        System.out.println("Invalid move from Tableau -> Foundation");
                    }

                    clearSelection();
                    e.consume();
                    return;
                }
            });

            foundationRow.getChildren().addAll(foundationContainer);
        }

        // ----- Tableaus -------
        HBox tableauRow = new HBox(40);
        GameApp.setTableauRow(tableauRow);
        tableauRow.setAlignment(Pos.TOP_CENTER);
        tableauRow.setPadding(new Insets(110, 0, 0, 0));


        for (int col = 1; col <= 7; col++) {
            Tableau tab = board.getTableau(col);

            PileView view = new PileView(tab, 30, col);
            
            int destCol = col; 

            view.setOnMouseClicked(e -> {

                // 1) Waste -> Tableau
                if (selectedWasteView != null) {
                    Card before = board.getWaste().topCard();

                    engine.moveWasteToTableau(destCol);

                    Card after = board.getWaste().topCard();

                    if (before != after) { // move succeeded
                        selectedWasteView.redraw();
                        view.redraw();

                        System.out.println("Moved from waste to tableau " + destCol);
                    } else {
                        System.out.println("Invalid move from waste to tableau " + destCol);
                    }

                    // CLEAR!
                    selectedWasteView.setStyle("");
                    selectedWasteView = null;
                    e.consume();
                    return;
                } 

                // 2) Tableau -> Tableau
                if (selectedSourceCol != -1) {

                    // ignore same column clicks 
                    if (selectedSourceCol == destCol) {
                        GameApp.clearSelection();
                        e.consume();
                        return;
                    }

                    int beforeMoves = engine.getMoveCount();
                    engine.move(selectedSourceCol, selectedSourceRow, destCol);

                    if (engine.getMoveCount() > beforeMoves) {
                        // move succeeded -> redraw all tableau views
                        for (Node node : tableauRow.getChildren()) {
                            if (node instanceof PileView pv) {
                                pv.redraw();
                            }
                        }

                        System.out.println("Moved tableau " + selectedSourceCol 
                        + " row " + selectedSourceRow + " to tableau "
                        + destCol);

                        if (gameWon()) {
                            System.out.println("YOU WIN! (all tabs are flipped.");
                        }
                    } else {
                        System.out.println("Invalid tableau move from "
                        + selectedSourceCol + " row " + selectedSourceRow
                        + " to " + destCol);
                    }

                    // CLEAR!
                    GameApp.clearSelection();
                    e.consume();
                    return;
                }
            });

            tableauRow.getChildren().add(view);
        }


        // ------------ Undo ---------------
        undo.setOnAction(e -> {
            engine.undo();

            redrawAllTableaus();
            redrawAllFoundations();

            if (stockViewRef != null) stockViewRef.redraw();
            if (wasteViewRef != null) wasteViewRef.redraw();

            clearSelection();
        });

        root.setTop(foundationRow);
        root.setBottom(bottomBar);
        root.setCenter(tableauRow);

        // Scene + Stage
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Klondike Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This function handles clicking a card in a pile, preping it for a possible move.
     * When card is clicked, any previous selections are cleared. If the click is a tableau pile, 
     * get all the cards from that card downwards.
     * 
     * @param clicked is the CardView node the user clicked
     * @param pile represents which pile the card belongs to
     * @param index represents position in a tableau pile. 
     */
    public static void handleCardSelection(CardView clicked, Pile pile, int index, int col) {

        if (selectedSourceCol == col && selectedSourceRow == index) {
            clearSelection();
            redrawAllTableaus();
            System.out.println("Deselected col " + col + " row " + index);
            return;
        }

        if (pile instanceof Tableau) { 
            selectedSourceCol = col;
            selectedSourceRow = index;
            System.out.println("Selected from col " + col + " row " + index);
        }

        redrawAllTableaus();
    }
    public static void main(String[] args) {
        launch(args); 
    }
}
