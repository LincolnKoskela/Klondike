package klondike.ui;

import klondike.*;
import java.util.EnumMap;
import javafx.scene.layout.*;

public class BoardView extends Pane {
    private final GameEngine engine;
    private final Board board;

    private final StockView stockView;
    private final WasteView wasteView;
    
    private final EnumMap<Card.Suit, FoundationView> foundationViews = new EnumMap<>(Card.Suit.class);
    
    private final TableauView[] tableauViews = new TableauView[8]; // index 1based

    public BoardView (GameEngine engine) {
        this.engine = engine;
        this.board = engine.getBoard();

        this.stockView = new StockView(board.getStock());
        this.wasteView = new WasteView(board.getWaste());

        for (Card.Suit suit : Card.Suit.values()) {
            foundationViews.put(suit, new FoundationView(board.getFoundation(suit)));
        }

        for (int col = 1; col <= 7; col++) {
            tableauViews[col] = new TableauView(board.getTableau(col));
        }

        getChildren().add(stockView);
        getChildren().add(wasteView);
        getChildren().addAll(foundationViews.values());
        for (int col = 1; col <= 7; col++) {
            getChildren().add(tableauViews[col]);
        }

        layoutPiles();
    }

    /**
     * Klondike board design
     */
    private void layoutPiles() {
       
        // (x, y) 60,40
        double x0 = UiMetrics.X0;
        double y0 = UiMetrics.Y0;
        stockView.relocate(x0, y0);
        wasteView.relocate(x0 + (UiMetrics.CARD_W + UiMetrics.GAP_X), y0);

        // layout foundation
        double stepX = UiMetrics.STEP_X;
        double fx = UiMetrics.F_X;
        

        Card.Suit[] order = {Card.Suit.HEARTS, Card.Suit.CLUBS, Card.Suit.SPADES, Card.Suit.DIAMONDS};
        for (int i = 0 ; i < order.length; i++) {
            foundationViews.get(order[i]).relocate(fx + i * stepX, y0);
        }

        double ty = UiMetrics.T_Y;
        for (int col = 1; col <= 7; col++) {
            tableauViews[col].relocate(x0 + (col - 1) * stepX, ty);
        }
    }

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
}
