package klondike.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

/**
 * Stackpane layed CardSlot, and PileView on top
 */
public class PileCell extends StackPane{
    private PileView pileView;

    public PileCell(CardSlot slot, PileView pileView) {
        this.pileView = pileView;

        getChildren().addAll(slot, pileView);
        setAlignment(Pos.TOP_LEFT);
        setPickOnBounds(true); // empties clickable
        setPrefSize(UiMetrics.CARD_W, UiMetrics.CARD_H);
        
    }

    public PileView getPileView() {
        return pileView;
    }
}
