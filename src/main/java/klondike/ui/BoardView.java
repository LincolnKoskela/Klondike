package klondike.ui;

import klondike.*;
import java.util.EnumMap;
import javafx.scene.layout.*;

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



    public BoardView (GameEngine engine) {
        this.engine = engine;
        this.board = engine.getBoard();

        this.stockView = new StockView(board.getStock());
        this.stockCell = new PileCell(new CardSlot("Stock"), stockView);
        this.wasteView = new WasteView(board.getWaste());
        this.wasteCell = new PileCell(new CardSlot("Waste"), wasteView);

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

        for (int col = 1; col <= 7; col++) {
            final int destCol = col; // loop tracker

            tableauViews[col] = new TableauView(board.getTableau(col), this, col);
            getChildren().add(tableauViews[col]);


            // // ------------------- attached click handlers --------------------
            // /* clicks are inside the constructor to wire up the controller buttons
            // clicks = pressing the already wired buttons */
            // tableauViews[col].setOnMouseClicked(e -> {
            //     if (selectedSourceCol == -1) return; // nothing selected
            //     if (destCol == selectedSourceCol) return; // clicked same pile

            //     engine.move(selectedSourceCol, selectedSourceRow, destCol);

            //     clearSelection();
            //     redraw();
            // });
        }

        getChildren().add(stockCell);
        getChildren().add(wasteCell);

        // ------------------- click handlers -------------------------
        stockCell.setOnMouseClicked(e -> {
            this.engine.draw();
            redraw();
        });

        // --------- Waste Clicks ------------
        wasteCell.setOnMouseClicked(e -> {
            if (board.getWaste().isEmpty()) return;

            int before = board.getWaste().size();

            // 1) Try Waste -> Foundation
            Card top = board.getWaste().topCard();
            engine.moveWastetoFoundation(top.getSuit());

            // if it moved, stop
            if (board.getWaste().size() < before) {
                redraw();
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
        });

        layoutPiles();
    }

    /**
     * Klondike board design
     */
    private void layoutPiles() {
       
        // (x, y) 60,40
        double x0 = UiMetrics.X0;
        double y0 = UiMetrics.Y0;
        double stepX = UiMetrics.STEP_X; // card width + gap_x -> one "step" 
        double fx = UiMetrics.F_X;

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

        // otherwise, attempt to move the clicked col as destination
        engine.move(selectedSourceCol, selectedSourceRow, clickedCol);

        clearHighlights();
        clearSelection();
        redraw();
    }

    public void tryTableauToFoundation(int sourceCol, Card.Suit suit) {
        engine.moveTableauToFoundation(sourceCol, suit);
        clearHighlights();
        clearSelection();
        redraw();
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

    public Board getBoard() {
        return board;
    }
}
