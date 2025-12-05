package klondike.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import klondike.Card;

public class CardView extends StackPane {

    private Card card;
    private Rectangle rect;
    private Label label;

    public CardView(Card card) {
        this.card = card;

        rect = new Rectangle(70, 100);
        rect.setArcWidth(10); // round the corners
        rect.setArcHeight(10);

        label = new Label();
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        updateAppearance();

        getChildren().addAll(rect, label);

        StackPane.setAlignment(label, Pos.TOP_LEFT);
        StackPane.setMargin(label, new Insets(5, 0, 0, 5));

        setPadding(new Insets(5));
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
            label.setText("");
            rect.setFill(Color.RED);
            rect.setStroke(Color.WHITE);
        }
    }
}
