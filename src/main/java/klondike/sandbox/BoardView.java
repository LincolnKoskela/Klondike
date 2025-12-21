package klondike.sandbox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import java.util.*;

import klondike.*;

public class BoardView extends Pane {
    private final Board board;

    private final PileView stockView;
    private final PileCell stockCell;
    private final PileView wasteView;
    private final PileCell wasteCell;

    private final EnumMap<Card.Suit, PileView> foundationViews = new EnumMap<>(Card.Suit.class);
    private final EnumMap<Card.Suit, PileCell> foundationCells = new EnumMap<>(Card.Suit.class);

    // indexed 1 based 1-7
    private final PileView[] tableauViews = new PileView[8];

    // universe selection!
    private PileView selectedPileView = null;
    private int selectedIndex = -1;

    public BoardView(GameEngine engine) {
        this.board = engine.getBoard();

        // create the views once
        this.stockView = new PileView(this, board.getStock(), 0);
        this.stockCell = new PileCell(new CardSlot("Stock"), stockView);

        this.wasteView = new PileView(this, board.getWaste(), 0);
        this.wasteCell = new PileCell(new CardSlot("Waste"), wasteView);

        for (Card.Suit suit : Card.Suit.values()) {
            PileView pv = new PileView(this, board.getFoundation(suit), 0);
            PileCell cell = new PileCell(new CardSlot(""), pv);

            foundationViews.put(suit, pv);
            foundationCells.put(suit, cell);

            getChildren().add(cell);
        }

        for (int col = 1; col <= 7; col++) {
            tableauViews[col] = new PileView(this, board.getTableau(col), UiMetrics.TABLEAU_Y_OFFSET);
        }

        getChildren().add(stockCell);
        getChildren().add(wasteCell);
        for (int col = 1; col <= 7; col++) {
            getChildren().add(tableauViews[col]);
        }

        layoutPiles();
    }

    /**
     * Use this function to layout and design the placement of the nodes 
     * on the board for the cardsViews
     */
    private void layoutPiles() {
        /* Pane in JavaFX starts at (0, 0) which is the TOP_LEFT */

        // our starting point with stockView
        double x0 = 60;
        double y0 = 40;

        stockCell.relocate(x0, y0); // (60, 40)
        wasteCell.relocate(x0 + (UiMetrics.CARD_W + UiMetrics.PILE_GAP_X), y0); // (60 + 70 + 20, 40) // same y cordinate

        // foundation layout
        double fx = x0 + (UiMetrics.CARD_W + UiMetrics.PILE_GAP_X) * 3;
        Card.Suit[] order = {Card.Suit.HEARTS, Card.Suit.DIAMONDS, Card.Suit.SPADES, Card.Suit.CLUBS};
        for (int i = 0; i < order.length; i++) {
            foundationCells.get(order[i]).relocate(fx + i * (UiMetrics.CARD_W + UiMetrics.PILE_GAP_X), y0);
        }

        double ty = y0 + (UiMetrics.PILE_GAP_Y + UiMetrics.CARD_H);
        for (int col = 1; col <= 7; col++) {
            tableauViews[col].relocate(x0 + (col - 1) * (UiMetrics.CARD_W + UiMetrics.PILE_GAP_X), ty);
        }
    }

    /**
     * Across the entire board, which card is selected right now?
     * Select PileView at Index. 
     * It's inside the board view because the boardView can see 
     * all the piles at once.
     * Selection is global UI state, so it should live in the first class
     * that can see the entire UI -> BoardView
     * @param pv PileView being selected
     * @param idx Index within a given PileView
     */
    public void select(PileView pv, int idx) {
        if (selectedPileView == pv && selectedIndex == idx) {
            selectedPileView = null;
            selectedIndex = -1;
            System.out.println("Deselected index: " + idx);
        } else {
            selectedPileView = pv;
            selectedIndex = idx;
            System.out.println("Selected index: " + idx);
        }

        redraw(); // refresh the visuals after selection changes
    }

    public boolean isSelected(PileView pv, int idx) {
        return selectedPileView == pv && selectedIndex == idx;
    }

    public void clearSelection() {
        selectedPileView = null;
        selectedIndex = -1;
        redraw();
    }

    public void redraw() {
        stockView.redraw();
        wasteView.redraw();

        for (PileView pv : foundationViews.values()) {
            pv.redraw();
        }
        for (int col = 1; col <= 7; col++) {
            tableauViews[col].redraw();
        }

    }
}
