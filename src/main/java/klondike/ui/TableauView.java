package klondike.ui;

import klondike.*;
import java.util.*;

public class TableauView extends PileView {

    private final double yOffset = UiMetrics.Y_OFFSET;
    private final BoardView boardView;
    private final int colIndex;

    public TableauView(Pile pile, BoardView boardView, int colIndex) {
        super(pile);
        this.boardView = boardView;
        this.colIndex = colIndex;
    }

    @Override
    protected void layoutCards() {
        for (int i = 0; i < cardViews.size(); i++) {
            final int row = i;
            CardView cv = cardViews.get(i);
            cv.relocate(0, i * yOffset);

            /* adding this clicker makes each individual card inside
            a tableau clickable. Without this, when clicking tableaus,
            we'd only be able to click a column, not an individual cardview
            inside a given tab column */
            cv.setOnMouseClicked(e -> {
                if (!cv.getCard().isFaceUp()) return;

                boardView.onTableauCardClicked(colIndex, row);

                e.consume();
            });


        }

        setPrefHeight(
            UiMetrics.CARD_H + Math.max(0, cardViews.size() - 1) * yOffset
        );
    }

    public List<CardView> getCardViews() {
        return cardViews;
    }
    
}
