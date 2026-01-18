package klondike.ui;

import klondike.*;

/**
 * Foundation View
 */
public class FoundationView extends PileView {

    public FoundationView(Pile pile) {
        super(pile);
    }

    @Override
    protected void layoutCards() {
        if (!cardViews.isEmpty()) {
            cardViews.get(cardViews.size() - 1).relocate(0, 0);
        }

        setPrefHeight(UiMetrics.CARD_H);
    }
}
