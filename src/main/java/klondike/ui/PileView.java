package klondike.ui;

import javafx.scene.layout.Pane;
import klondike.*;
import java.util.*;

/**
 * Abstract class PileView holds sync/updates. 
 * Subclasses will override for layout specs, the abstract function.
 */
abstract class PileView extends Pane{
    private final Pile pile;
    private final List<CardView> cardViews = new ArrayList<>();

    public PileView(Pile pile) {
        this.pile = pile;
        redraw();
    }

    public Pile getPile() {
        return pile;
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
     * UPDATE + LAYOUT: make each view show the right card, the position it
     * 
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

        // UPDATE + LAYOUT: refresh appearance and stack them
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

    protected abstract void layoutCards();
}
