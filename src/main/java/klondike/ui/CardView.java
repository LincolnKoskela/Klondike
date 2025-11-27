package klondike.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import klondike.Card;

public class CardView extends StackPane {

    private Card card;
    private Rectangle rect;
    private Label label;
    private Runnable onSelect;

    public CardView(Card card) {
        this.card = card;

        rect = new Rectangle(70, 100);
        rect.setArcWidth(10); // round the corners
        rect.setArcHeight(10);

        label = new Label();
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        updateAppearance();

        getChildren().addAll(rect, label);

        setStyle("-fx-padding: 5;");

        // add clicking ability
        setOnMouseClicked(e -> {
            if (onSelect != null) onSelect.run();
        });

        // add glow around the clicked card
        setOnMouseClicked(e -> {
            setStyle("-fx-border-color: gold; -fx-border-width: 3");
        }) ;
    }

    public void setOnSelect(Runnable r) {
        this.onSelect = r;
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
