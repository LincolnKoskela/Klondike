package klondike.ui;

import javafx.scene.layout.Pane;
import java.util.List;
import klondike.Pile;
import klondike.Card;

/**
 * This class is a pane that draws CardViews
 */
public class PileView extends Pane {

    private final Pile pile;
    private final double yOffset; // defines spacing

    public PileView(Pile pile, double yOffset) {
        this.pile = pile;
        this.yOffset = yOffset;
        redraw();
    }

    public void redraw() {
        getChildren().clear(); // remove all previously drawn cards

        List<Card> cards = pile.getCards();
        double currentY = 0.0; // track verticle placement

        for (Card card : cards) {
            CardView view = new CardView(card); // convert card object into UI node

            view.setOnSelect(() -> {
                GameApp.handleCardSelection(view);
            });
            view.setLayoutY(currentY); // place children manually
            getChildren().add(view); // becomes visible on screen
            currentY += yOffset; // offset for the next card
        }
    }
}
