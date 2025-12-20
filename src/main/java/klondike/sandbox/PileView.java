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

    // the state that belongs to the pileView as a whole
    private int selectedIndex = -1;

    public PileView(Pile pile, double yOffset) {
        this.pile = pile;
        this.yOffset = yOffset;

        redraw();
    }

    /**
     * The function answers -> how are the cards arranged inside this pileView
     * 
     * Handler click is in this function because handlers live on nodes, and if
     * you rebuild a node, you must reattach handlers
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

            // i'm selecting this cardView -> if X happens do Y -> if view clicked, do ...
            final int idx = i;
            view.setOnMouseClicked(e -> {
                selectedIndex = idx;
                redraw();
                e.consume();
                System.out.println("Selected Index: " + selectedIndex);
            });

            if (i == selectedIndex) {
                view.setStyle("-fx-border-color: gold;" + 
                    "-fx-border-width: 3;"
                );
            }

            getChildren().add(view);
            currentY += yOffset;
        }
    } 
}
