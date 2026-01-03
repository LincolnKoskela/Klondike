package klondike.ui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import klondike.Card;

public class CardView extends StackPane {
    private Card card;
    private Rectangle rect;
    private Label label;

    public CardView(Card card) {
        this.card = card;
        rect = new Rectangle(UiMetrics.CARD_W, UiMetrics.CARD_H);
    
        label = new Label();
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // confine the rectangle to its size
        setMaxSize(UiMetrics.CARD_W, UiMetrics.CARD_H);
        setMinSize(UiMetrics.CARD_W, UiMetrics.CARD_H);
        rect.setArcHeight(10);
        rect.setArcWidth(10);

        updateAppearance();
        getChildren().addAll(rect, label);


        setAlignment(label, Pos.TOP_LEFT);
        setPadding(new Insets(5, 0,0,5));
    }

    private String getRankSymbol(Card.Rank rank) {
        switch (rank) {
            case ACE: return "A";
            case JACK: return "J";
            case QUEEN: return "Q";
            case KING: return "K";
            default: return String.valueOf(rank.getValue());
        }
    }

    private String getSuitSymbol(Card.Suit suit) {
        switch (suit) {
            case SPADES: return "♠";
            case HEARTS: return "♥";
            case CLUBS: return "♣";
            case DIAMONDS: return "♦";
            default: return "?";
        }
    }

    /**
     * Defines what a card looks like.
     * "Given the current state of card, update the UI
     * to match that state."
     */
    private void updateAppearance() {
        if (card.isFaceUp()) {
            String text = getRankSymbol(card.getRank()) + getSuitSymbol(card.getSuit());
            label.setText(text);

            if (card.getSuit().getColor().equals("red")) {
                label.setTextFill(Color.RED);
            } else {
                label.setTextFill(Color.BLACK);
            }

            rect.setFill(Color.WHITE);
            rect.setStroke(Color.BLACK);
        } else {
            rect.setFill(Color.RED);
            rect.setStroke(Color.WHITE);
        }
    }

    /**
     * Enables reusage later
     * @param card
     */
    public void setCard(Card card) {
        this.card = card;
        updateAppearance();
    }
}