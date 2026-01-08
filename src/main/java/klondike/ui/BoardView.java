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
    private boolean wasteSelected = false;

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
        }

        for (int col = 1; col <= 7; col++) {

            final int destCol = col;
            tableauViews[col] = new TableauView(board.getTableau(col));
            getChildren().add(tableauViews[col]);

            tableauViews[col].setOnMouseClicked(e -> {
                if (!wasteSelected) return;
                if (board.getWaste().isEmpty()) return;

                engine.moveWasteToTableau(destCol);
                wasteSelected = false;
                redraw();
            });
        }

        getChildren().add(stockCell);
        getChildren().add(wasteCell);

        // ------------------- click handlers -------------------------
        stockCell.setOnMouseClicked(e -> {
            
            this.engine.draw();
            Card card = board.getWaste().topCard();
            redraw();
            System.out.println(card.toString() + " drawn from Stock.");
        });

        wasteCell.setOnMouseClicked(e -> {
            wasteSelected = !wasteSelected; // toggle selection
            Card card = board.getWaste().topCard();
            System.out.println("Selected " + card.toString());
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
