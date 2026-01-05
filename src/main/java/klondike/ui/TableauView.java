package klondike.ui;

import klondike.*;

public class TableauView extends PileView {

    private final double yOffset = UiMetrics.Y_OFFSET;

    public TableauView(Pile pile) {
        super(pile);
    }

    @Override
    protected void layoutCards() {
        for (int i = 0; i < cardViews.size(); i++) {
            cardViews.get(i).relocate(0, i * yOffset);
        }

        setPrefHeight(
            UiMetrics.CARD_H + Math.max(0, cardViews.size() - 1) * yOffset
        );
    }
    
}
