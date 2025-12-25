package klondike.sandbox;

import javafx.scene.layout.Pane;
import java.util.*;

import klondike.*;

public class BoardView extends Pane {
    private final Board board;
    private final GameController controller;
    private final PileRegistry registry = new PileRegistry();

    private final PileView stockView;
    private final PileCell stockCell;
    private final PileView wasteView;
    private final PileCell wasteCell;

    private final EnumMap<Card.Suit, PileView> foundationViews = new EnumMap<>(Card.Suit.class);
    private final EnumMap<Card.Suit, PileCell> foundationCells = new EnumMap<>(Card.Suit.class);

    // indexed 1 based 1-7
    private final PileView[] tableauViews = new PileView[8];

    public BoardView(GameEngine engine) {
        this.board = engine.getBoard();
        this.controller = new GameController(engine, this);

        // create the views once
        this.stockView = new PileView(this, board.getStock(), 0);
        this.stockCell = new PileCell(new CardSlot("Stock"), stockView);

        this.wasteView = new PileView(this, board.getWaste(), 0);
        this.wasteCell = new PileCell(new CardSlot("Waste"), wasteView);

        for (Card.Suit suit : Card.Suit.values()) {
            Pile foundationPile = board.getFoundation(suit);
            PileView pv = new PileView(this, board.getFoundation(suit), 0);
            PileCell cell = new PileCell(new CardSlot(""), pv);

            registry.registerFoundation(foundationPile, suit);

            foundationViews.put(suit, pv);
            foundationCells.put(suit, cell);

            getChildren().add(cell);
        }

        for (int col = 1; col <= 7; col++) {
            Pile tableauPile = board.getTableau(col);
            tableauViews[col] = new PileView(this, board.getTableau(col), UiMetrics.TABLEAU_Y_OFFSET);

            registry.registerTableau(tableauPile, col);
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

    public GameController getController() {
        return controller;
    }

    public PileRegistry getRegistry() {
        return registry;
    }

    /**
     * UI reflects state
     */
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
