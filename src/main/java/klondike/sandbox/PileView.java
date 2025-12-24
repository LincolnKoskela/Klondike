package klondike.sandbox;

import klondike.Pile;
import klondike.Card;
import java.util.*;

import javafx.scene.layout.Pane;

/**
 * Give a Pile, draw its cards in the correct verticle arrangement
 */
public class PileView extends Pane {
    
    private final Pile pile;
    private final double yOffset; // spacing of the Panes

    private final BoardView boardView;

    public PileView(BoardView boardView, Pile pile, double yOffset) {
        this.boardView = boardView;
        this.pile = pile;
        this.yOffset = yOffset;

        redraw();
    }

    /**
     * The function answers -> how are the cards arranged inside this pileView
     * 
     * Handler click is in this function because handlers live on nodes, and if
     * you rebuild a node, you must reattach handlers. The PileView does NOT 
     * decide what click means, it detects the click, identifies which index was 
     * clicked, and reports to BoardView.
     * 
     * Handler is a function given to javaFx to run later when something happens
     */
    public void redraw() {
        getChildren().clear(); // remove previous state

        List<Card> cards = pile.getCards();
        double currentY = 0.0;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);

            // everytime redraw, you create new cardview
            CardView view = new CardView(card);
            view.setLayoutY(currentY);

            final int idx = i;
            view.setOnMouseClicked(e -> {
                e.consume();
                boardView.getController().handleCardsClicked(pile, idx);
                System.out.print("Clicked. ");
            });

            if (boardView.getController().isSelected(pile, idx)) {
                view.setStyle(
                    "-fx-border-color: gold;" +
                    "-fx-border-width: 3;"
                );
            }

            getChildren().add(view);
            currentY += yOffset;
        }
    } 
}
