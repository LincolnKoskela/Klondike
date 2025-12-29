package klondike.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import klondike.*;

/**
 * Visual representation of a playing card from Card.java
 */
public class CardView extends StackPane {
    private Card card;
    private Rectangle rect;
    private Label label;

    public CardView(Card card) {
        this.card = card;
        this.rect = new Rectangle(UiMetrics.CARD_W, UiMetrics.CARD_H);
        this.label = new Label();
        
    }

    private String getRankSymbol(Card.Rank rank) {
        switch (rank) {
            case ACE : return "A";
            case KING : return "K";
            case QUEEN : return "Q";
            case JACK : return "J";
            default : return String.valueOf(rank.getValue());
        }
    }

    private String getSuitSymbol(Card.Suit suit) {
        switch (suit) {
            case SPADES : return "♠";
            case CLUBS : return "♥";
            case HEARTS : return "♣";
            case DIAMONDS : return "♦";
            default : return "?";
        }
    }

    private void applySpecs() {
        if (card.isFaceUp()) {
            
        }
    }
}
