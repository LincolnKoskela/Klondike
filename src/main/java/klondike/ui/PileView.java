package klondike.ui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import klondike.*;
import java.util.*;

public class PileView extends Pane{
    private Pile pile;
    private double yOffset = UiMetrics.Y_OFFSET;

    public PileView(Pile pile, double yOffset) {
        this.pile = pile;
        this.yOffset = yOffset;

        redraw();
    }

    /**
     * Redraw refreshes each card view. First clearing the visuals (nodes)
     * Then pulls data from the model Pile. Loops through the pile and 
     * refreshes the views
     */
    public void redraw() {
        getChildren().clear();
        List<Card> cards = pile.getCards();
        double currentY = 0.0;

        for (int i = 0; i < pile.size(); i++) {

            final int idx = i;
            Card card = cards.get(i);
            CardView view = new CardView(card);

            view.setLayoutY(currentY);
            view.setOnMouseClicked(e -> {
                System.out.println("Clicked Pile at index " + idx);
            });

            getChildren().add(view);
            currentY += yOffset;
        }
    }
}
