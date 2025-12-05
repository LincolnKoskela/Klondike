package klondike.ui;

import javafx.scene.layout.Pane;
import java.util.List;
import klondike.Pile;
import klondike.Card;
import klondike.Tableau;

/**
 * This class is a pane that draws CardViews
 */
public class PileView extends Pane {

    private final Pile pile;
    private final double yOffset; // defines spacing
    private final int column;

    // For TABLEAU PILES (int column needed)
    public PileView(Pile pile, double yOffset, int column) {
        this.pile = pile;
        this.yOffset = yOffset;
        this.column = column;

        setPrefSize(80, 120);
        setPrefSize(80, 120);
        redraw();
    }

    public PileView(Pile pile, double yOffset) {
        this(pile, yOffset, -1); // -1 is no column
    }

    /**
     * This function rebuilds the entire visual pile of cards 
     * turning pile data into actual javaFX card nodes on screen.
     * Everytime the pile changes (new card, card flipped, cards removed), 
     * this function is called and displays the update
     */
    public void redraw() {
        getChildren().clear(); // remove all previously drawn cards

        List<Card> cards = pile.getCards();
        double currentY = 0.0; // track verticle placement

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            final int cardindex = i;
            CardView view = new CardView(card); // convert card object into UI node
            view.setLayoutY(currentY);

            view.setOnMouseClicked(e -> {

                if (pile instanceof Tableau && 
                    column > 0 &&
                    !GameApp.isWasteSelected()) {

                    GameApp.handleCardSelection(view, pile, cardindex, column);
                    e.consume();
                }
            });

            if (pile instanceof Tableau &&
                column == GameApp.getSelectedSourceCol() && 
                cardindex >= GameApp.getSelectedSourceRow()) {

                view.setStyle("-fx-border-color: gold; -fx-border-width: 3");
            } else {
                view.setStyle("");
            }

            getChildren().add(view); // becomes visible on screen
            currentY += yOffset; // offset for the next card
        }
    }
}
