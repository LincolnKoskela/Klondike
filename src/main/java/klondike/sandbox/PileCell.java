package klondike.sandbox;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

/**
 * This class represents a spot on the board where a pile lives
 */
public class PileCell extends StackPane {

    private final PileView pileView;

    public PileCell(CardSlot slot, PileView pileView) {
        this.pileView = pileView;

        getChildren().addAll(slot, pileView);
        setAlignment(Pos.TOP_LEFT);
        setPickOnBounds(false); // makes area clickable

        setPrefSize(UiMetrics.CARD_W, UiMetrics.CARD_H);
    }  

    public PileView getPileView() {
        return pileView;
    }
}
