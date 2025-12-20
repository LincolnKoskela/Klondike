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

    // private final GameController controller;
    // private final int col;

    public PileView(Pile pile, double yOffset) {
        this.pile = pile;
        this.yOffset = yOffset;

        redraw();
    }

    /**
     * The function answers -> how are the cards arranged inside this pileView
     */
    public void redraw() {
        getChildren().clear(); // remove previous state

        List<Card> cards = pile.getCards();
        double currentY = 0.0;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            CardView view = new CardView(card);
            view.setLayoutY(currentY);

            final int idx = i;
            view.setOnMouseClicked(e -> {
                System.out.println("Clicked card at index: " + idx);
            });

            getChildren().add(view);
            currentY += yOffset;
        }
    } 
}
