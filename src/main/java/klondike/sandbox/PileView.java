package klondike.sandbox;

import klondike.Pile;
import klondike.Card;
import java.util.*;

import javafx.scene.layout.Pane;

public class PileView extends Pane {
    
    private final Pile pile;
    private final double yOffset; // spacing of the Panes

    public PileView(Pile pile, double yOffset) {
        this.pile = pile;
        this.yOffset = yOffset;

        redraw();
    }

    public void redraw() {
        getChildren().clear(); // remove previous state

        List<Card> cards = pile.getCards();
        double currentY = 0.0;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            CardView view = new CardView(card);
            view.setLayoutY(currentY);

            getChildren().add(view);
            currentY += yOffset;
        }
    } 
}
