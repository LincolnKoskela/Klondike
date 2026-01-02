package klondike.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


/**
 * A Cardslot is a Rectangle where a Playing Card would go.
 * Cardslot will be used in a PileCell, stacking PileView 
 * on top of a CardSlot.
 */
public class CardSlot extends StackPane {
    private Rectangle rect;

    public CardSlot(String text) {
        this.rect = new Rectangle(UiMetrics.CARD_W, UiMetrics.CARD_H);
        Label label = new Label(text);

        applySpecs();

        getChildren().addAll(rect, label);

        label.setStyle("-fx-font-size: 15px;");
    }

    /**
     * Features of the card slot. 
     * Arcs, Color, border, fill etc.
     */
    public void applySpecs() {
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.GRAY); // outline of shape
        rect.setStrokeWidth(3);
    }
}
