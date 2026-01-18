package klondike.ui;

import javafx.scene.layout.Pane;
import klondike.*;
import java.util.*;

/**
 * Abstract class PileView holds sync/updates. 
 * Subclasses will override for layout specs, the abstract function.
 */
abstract class PileView extends Pane {
    protected final Pile pile; // engine model cards
    protected final List<CardView> cardViews = new ArrayList<>(); // UI nodes/ view objects

    protected abstract void layoutCards();

    public PileView(Pile pile) {
        this.pile = pile;
        redraw();
    }

    /**
     * Make the UI match the model (pile.getCards())
     * 
     * SYNCING: makes sure the right number of CardViews exist.
     * making sure card.size and cardviews.size are always matching
     * Syncing makes sure the number of UI nodes matches the number of model objects
     * Model -> pile.getCards() UI -> cardViews + getChildren()
     * 
     * CASE A: model grows (added a card) -> Do i have fewer CardViews than cards
     * 
     * CASE B: model shrinks (card removed) -> Do I have more CardViews than cards
     * if yes, remove extras from the ends
     * 
     * UPDATE -> cardViews are set to whatever the pile is
     * 
     * Incremental synchronization keeps the view nodes in sync with the game model.
     * On redraw(), the piles cards.size() is the TRUTH. If the UI has too many/few cardViews,
     * it creates/removes only the difference, then updates each existing cardView to display the 
     * correct card, then lays them out.
     * This technique avoid destroying and then recreating the nodes everytime, leading to more 
     * efficiency and preserving the nodes. This is enable animations and handling input easier
     * and more clear to build.
     * 
     */
    public void redraw() {
        List<Card> cards = pile.getCards();

        // SYNC: ensure have exactly one CardView per Card (by index)
        while (cardViews.size() < cards.size()) {
            CardView cv = new CardView(cards.get(cardViews.size()));
            cardViews.add(cv);
            getChildren().add(cv);
        }

        while (cardViews.size() > cards.size()) {
            CardView cv = cardViews.remove(cardViews.size()-1);
            getChildren().remove(cv);
        }

        // UPDATE 
        for (int i = 0; i < cards.size(); i++) {
            CardView cv = cardViews.get(i);
            cv.setCard(cards.get(i));
        }

        layoutCards();

        // widths are consistent, heights will grow in tableaus
        setMinWidth(UiMetrics.CARD_W);
        setMaxWidth(UiMetrics.CARD_W);
        setPrefWidth(UiMetrics.CARD_W);
    }
}
