package klondike.ui;

import klondike.*;
import java.util.EnumMap;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BoardView extends Pane {
    private final GameEngine engine;
    private final Board board;

    private final StockView stockView;
    private final PileCell stockCell;
    private final WasteView wasteView;
    private final PileCell wasteCell;
    private final EnumMap<Card.Suit, FoundationView> foundationViews = new EnumMap<>(Card.Suit.class);
    private final EnumMap<Card.Suit, PileCell> foundationCells = new EnumMap<>(Card.Suit.class);
    private final TableauView[] tableauViews = new TableauView[8]; // index 1based

    // selection handlers variables
    private int selectedSourceCol = -1;
    private int selectedSourceRow = -1;

    // Button (eventually move to a sidemenu once we get further in project)
    private Button undo;

    // Animation fields
    private final Pane animationLayer = new Pane();
    private final AnimationManager animator;
    private boolean animating = false;

    // Timer fields
    private Label timerLabel;
    private Timeline uiTimer; // series of events thread
    private Time gameTimer; // stopwatch

    // Win fields 
    private boolean winShown = false;
    private Pane winPane;


    public BoardView (GameEngine engine) {
        this.engine = engine;
        this.board = engine.getBoard();

        this.undo = new Button("UNDO");
        styleButton();

        gameTimer = new Time();
        gameTimer.start();

        styleTimer();
        getChildren().add(timerLabel);

        uiTimer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> updateTimerLabel())
        );
        uiTimer.setCycleCount(Animation.INDEFINITE); // repeat forever
        uiTimer.play();

        this.stockView = new StockView(board.getStock());
        this.stockCell = new PileCell(new CardSlot("Stock"), stockView);
        this.wasteView = new WasteView(board.getWaste());
        this.wasteCell = new PileCell(new CardSlot("Waste"), wasteView);

        // ----------- Foundation Handlers -----------------
        for (Card.Suit suit : Card.Suit.values()) {
            FoundationView fv = new FoundationView(board.getFoundation(suit));
            PileCell pc = new PileCell(new CardSlot(""), fv);

            foundationViews.put(suit, fv);
            foundationCells.put(suit, pc);

            getChildren().add(pc);

            pc.setOnMouseClicked(e -> {

                // no tableau selected -> meaning clicking foundation first
                if (selectedSourceCol == -1) { 
                    if (board.getFoundation(suit).isEmpty()) return;

                    int before = board.getFoundation(suit).size();

                    // Scan Left to Right
                    for (int col = 1; col <= 7; col++) {
                        engine.moveFoundationToTableau(suit, col);

                        if (board.getFoundation(suit).size() < before) {
                            redraw();
                            return;
                        }
                    }
                }
            });
        }

        /*  Tableau click handlers live in the TableauView.java,
        clicking on individual CardViews. Since Cardviews call e.consume(),
        clicks won't be affected by our click handling here within our loop.
        Within this loop, we ensure we can click empty tableaus. 
        SetPickOnBounds(true) in our constructor enables clicking on empty tabs.
        CardView clicks will be handled first and consumed. If cardviews are 
        'empty' then nothing will be e.consume()'d */
        for (int col = 1; col <= 7; col++) {

            final int destCol = col;

            tableauViews[col] = new TableauView(board.getTableau(col), this, col);
            getChildren().add(tableauViews[col]);

            tableauViews[col].setOnMouseClicked(e -> {
                if (animating) return;

                if (selectedSourceCol == -1) return;
                if (destCol == selectedSourceCol) return;

                engine.move(selectedSourceCol, selectedSourceRow, destCol);
                clearHighlights();
                clearSelection();
                redraw();
            });
            
        }

        getChildren().add(stockCell);
        getChildren().add(wasteCell);
        getChildren().add(undo);

        getChildren().add(animationLayer);

        animator = new AnimationManager(animationLayer);

        // ------------------- click handlers -------------------------
        stockCell.setOnMouseClicked(e -> {

            if (animating) return; // Only one line animation at a time while in-flight

            if (board.getStock().isEmpty()) {
                engine.recycle();
                redraw();
                return;
            } 
            animating = true;
            
            animator.animateTopCardToNode(
                stockView,
                wasteView, 
                () -> {
                    engine.draw(); 
                },
                
                () -> {
                    redraw();
                    animating = false;
                });
        });

        // --------- Waste Clicks ------------
        wasteCell.setOnMouseClicked(e -> {
            if (animating) return;
            if (board.getWaste().isEmpty()) return;

            int before = board.getWaste().size();

            // 1) Try Waste -> Foundation: animate Waste -> Foundation
            Card top = board.getWaste().topCard();
            Card.Suit suit = top.getSuit();
            FoundationView fv = foundationViews.get(suit);

            engine.moveWastetoFoundation(suit);

            // If card moved to foundation: animate Waste -> Foundation
            if (board.getWaste().size() < before) {
                animating = true;

                animator.animateTopCardToNode(wasteView,
                    fv,
                    null, 
                    () -> {
                        redraw();
                        animator.foundationSplash(fv);
                        animating = false;
                    }
                );

                return;
            }

            // 2) Otherwise try Waste -> Tableau (scanning left to right)
            for (int col = 1; col <= 7; col++) {
                engine.moveWasteToTableau(col);

                if (board.getWaste().size() < before) {
                    redraw();
                    return;
                }
            }

            // no valid moves
            animator.shake(wasteCell);
        });

        // -------- Undo Button Clicker ----------
        undo.setOnMouseClicked(e -> {
            engine.undo();
            redraw();
        });

        layoutPiles();
    }

    /**
     * Klondike board design (with button temporarily)
     */
    private void layoutPiles() {
       
        // (x, y) 60,40
        double x0 = UiMetrics.X0;
        double y0 = UiMetrics.Y0;
        double stepX = UiMetrics.STEP_X; // card width + gap_x -> one "step" 
        double fx = UiMetrics.F_X;

        undo.relocate(x0, 5);

        timerLabel.relocate((x0 * 3) - 15, 5);

        stockCell.relocate(x0, y0);
        wasteCell.relocate(x0 + (stepX), y0);

        Card.Suit[] order = {Card.Suit.HEARTS, Card.Suit.CLUBS, Card.Suit.SPADES, Card.Suit.DIAMONDS};
        for (int i = 0 ; i < order.length; i++) {
            foundationCells.get(order[i]).relocate(fx + i * stepX, y0);
        }

        double ty = UiMetrics.T_Y;
        for (int col = 1; col <= 7; col++) {
            tableauViews[col].relocate(x0 + (col - 1) * stepX, ty);
        }
    }

    /**
     * Cool looking undo button dontcha!
     */
    private void styleButton() {
        undo.setMaxWidth(Double.MAX_VALUE);
        undo.setStyle(
            "-fx-background-color: #262626;" + 
            "-fx-border-color: #555;" +
            "-fx-border-width: 1;" + 
            "-fx-font-size: 12px;" + 
            "-fx-font-weight: bold;" + 
            "-fx-text-fill: white;"
        );
        undo.setOnMouseEntered(e -> undo.setStyle(
            "-fx-background-color: #333333;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;"
        ));

        undo.setOnMouseExited(e -> undo.setStyle(
            "-fx-background-color: #262626;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;"
        ));
    }

    private void styleTimer() {
        timerLabel = new Label("Time: 0:00");
        timerLabel.setStyle("""
            
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
                    
        """);
    }

    /**
     * BoardView's redraw() using individual PilesViews redraw() function
     */
    public void redraw() {
        stockView.redraw();
        wasteView.redraw();

        for (FoundationView fv : foundationViews.values()) {
            fv.redraw();
        }

        for (int col = 1; col <= 7; col++) {
            tableauViews[col].redraw();
        }
    }

    // --------- Tableau Run / Handler functions ---------
    public void clearSelection() {
        selectedSourceCol = -1;
        selectedSourceRow = -1;
    }

    public void onTableauCardClicked(int clickedCol, int clickedRow) {

        if (animating) return;

        // if nothing selected yet
        if (selectedSourceCol == -1) {
            selectedSourceCol = clickedCol;
            selectedSourceRow = clickedRow;

            clearHighlights();
            applyHighlight(clickedCol, clickedRow);
            return;
        }

        // if clicking same tableau again, deselect
        if (clickedCol == selectedSourceCol) {

            clearHighlights();
            clearSelection();
            System.out.println("Deselected.");
            return;
        }

        if (!isValidCol(selectedSourceCol) || !isValidCol(clickedCol)) {
            System.out.println("Invalid col: src=" + selectedSourceCol + " dest= " + clickedCol);
            clearHighlights();
            clearSelection();
            redraw();
            return;
        }

        // otherwise, attempt to move the clicked col as destination
        engine.move(selectedSourceCol, selectedSourceRow, clickedCol);

        clearHighlights();
        clearSelection();
        redraw();
        checkWin();
        
    }

    public void tryTableauToFoundation(int sourceCol, Card.Suit suit) {

        int before = board.getTableau(sourceCol).size();

        engine.moveTableauToFoundation(sourceCol, suit);


        clearHighlights();
        clearSelection();
        redraw();

        if (board.getTableau(sourceCol).size() < before) {
            animator.foundationSplash(foundationViews.get(suit));
            checkWin();
        }
        
    }

    public boolean hasSelection() {
        return selectedSourceCol != -1;
    }

    public int getSelectedSourceCol() {
        return selectedSourceCol;
    }
    // -------------------------------------------------------

    private void applyHighlight(int col, int startRow) {
        for (int i = startRow; i < tableauViews[col].getCardViews().size(); i++) {
            CardView cv = tableauViews[col].getCardViews().get(i);
            cv.setHighlighted(true);
        }
    }

    private void clearHighlights() {
        for (int col = 1; col <= 7; col++) {
            for (CardView cv : tableauViews[col].getCardViews()) {
                cv.setHighlighted(false);
            }
        }
    }

    /**
     * Convert elapsed time (using Time.java gameTimer) and convert to 
     * readable digital time 
     */
    private void updateTimerLabel() {
        long seconds = gameTimer.getElapsedTime();
        long mins = seconds / 60;
        long secs = seconds % 60;

        timerLabel.setText(
            String.format("Time: %d:%02d", mins, secs)
        );
    }

    private void showWinUI() {
        if (winPane != null) return;

        winPane = new Pane();
        winPane.setPickOnBounds(true);
        winPane.setStyle("-fx-background-color: rgba(0,0,0,0.55;)");

        // cover the whole board
        winPane.prefWidthProperty().bind(widthProperty());
        winPane.prefHeightProperty().bind(heightProperty());

        Label winLabel = new Label("YOU WIN!");
        winLabel.setStyle(
            "-fx-font-size: 48px;" + 
            "-fx-font-weight: 900;" + 
            "-fx-text-fill: white;"
        );

        Button newGameButton = new Button("Deal New Game");
        newGameButton.setStyle(
            "-fx-font-size: 16px;" + 
            "-fx-font-weight: bold;"
        );

        // center manually
        winLabel.layoutXProperty().bind(widthProperty().subtract(winLabel.widthProperty()).divide(2));
        winLabel.layoutYProperty().bind(heightProperty().divide(2).subtract(80));

        newGameButton.layoutXProperty().bind(widthProperty().subtract(newGameButton.widthProperty()).divide(2));
        newGameButton.layoutYProperty().bind(heightProperty().divide(2));

        winPane.getChildren().addAll(winLabel, newGameButton);

        newGameButton.setOnAction(e -> {

            animating = false;

            getChildren().remove(winPane);
            winPane = null;
            winShown = false;

            // reset model
            engine.dealNewGame();

            // reset any UI selections and highlights
            clearHighlights();
            clearSelection();

            redraw();
        });

        getChildren().add(winPane);
    }

    private void checkWin() {
        System.out.println("Checkwin called. isGameWon=" + board.isGameWon());

        if (winShown) return;

        if (board.isGameWon()) {
            winShown = true;
            animating = true;
            showWinUI();
        }
    }

    private boolean isValidCol(int col) {
        return col >= 1 && col <= 7;
    }

    public Board getBoard() {
        return board;
    }
}
