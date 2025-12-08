package klondike.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

public class CardSlot extends StackPane {


    public CardSlot() {
        Rectangle rect = new Rectangle(70, 100); // width, height
        rect.setArcWidth(12); // round the corners
        rect.setArcHeight(12);
        rect.getStyleClass().add("card-slot-rect");


        // center the label on the top of the rect
        getChildren().addAll(rect);

        // spacing from other nodes
        setStyle("-fx-padding: 5;");
    }
    
    /**
     * Constructor that defines how card slot looks
     * @param text -> label the card slot
     */
    public CardSlot(String text) {
        Rectangle rect = new Rectangle(70, 100); // width, height
        rect.setArcWidth(10); // round the corners
        rect.setArcHeight(10);
        rect.getStyleClass().add("card-slot-rect");

        // add a label
        Label label = new Label(text);
        label.getStyleClass().add("card-slot-label");

        // center the label on the top of the rect
        getChildren().addAll(rect, label);

        // spacing from other nodes
        setStyle("-fx-padding: 5;");
    }

    
}
