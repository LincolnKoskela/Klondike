package klondike.sandbox;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CardSlot extends StackPane {

    /**
     * Construct how a Card slot looks 
     * @param text will be the label inside the card slot
     */
    public CardSlot(String text) {
        Rectangle rect = new Rectangle(70, 100);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.getStyleClass().add("card-slot-label");

        Label label = new Label(text);
        label.getStyleClass().add("card-slot-label");

        getChildren().addAll(rect, label);

        setStyle("-fx-padding: 5");
        rect.setFill(Color.web("#cc8b12ff"));
    }
    
}
