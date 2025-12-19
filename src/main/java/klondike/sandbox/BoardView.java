package klondike.sandbox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import java.util.*;

import klondike.*;

public class BoardView extends Pane{
    private final GameEngine engine;
    private final Board board;

    private final PileView stockView;
    private final PileView wasteView;

    private final EnumMap<Card.Suit, PileView> foundationViews = new EnumMap<>(Card.Suit.class);

    // indexed 1 based
    private final PileView[] tableauViews = new PileView[8];
    
    // layout constants 
    private static final double CARD_W = 70; // card width
    private static final double CARD_H = 100; // card height
    private static final double GAP_X = 20; // space btwn piles (horizontal)
    private static final double GAP_Y = 30; // space btwn foundations & tableaus

    private static final double TABLEAU_Y_OFFSET = 30;

    public BoardView(GameEngine engine) {
        this.engine = engine;
        this.board = engine.getBoard();

        // create the views once
        this.stockView = new PileView(board.getStock(), 0);
        this.wasteView = new PileView(board.getWaste(), 0);

        for (Card.Suit suit : Card.Suit.values()) {
            foundationViews.put(suit, new PileView(board.getFoundation(suit), 0));
        }

        for (int col = 1; col <= 7; col++) {
            tableauViews[col] = new PileView(board.getTableau(col), TABLEAU_Y_OFFSET);
        }

        getChildren().add(stockView);
        getChildren().add(wasteView);
        getChildren().addAll(foundationViews.values());
        for (int col = 1; col <= 7; col++) {
            getChildren().add(tableauViews[col]);
        }

        layoutPiles();
    }

    private void layoutPiles() {
        double x0 = 60;
        double y0 = 40;

        stockView.relocate(x0, y0);
        wasteView.relocate(x0 + (CARD_W + GAP_X), y0);

        double fx = x0 + (CARD_W + GAP_X) * 3;
        Card.Suit[] order = {Card.Suit.HEARTS, Card.Suit.DIAMONDS, Card.Suit.SPADES, Card.Suit.CLUBS};
        for (int i = 0; i < order.length; i++) {
            foundationViews.get(order[i]).relocate(fx + i * (CARD_W + GAP_X), y0);
        }

        double ty = y0 + CARD_H + GAP_Y;
        for (int col = 1; col <= 7; col++) {
            tableauViews[col].relocate(x0 + (col - 1) * (CARD_W + GAP_X), ty);
        }
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
