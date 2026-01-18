package klondike.ui;

import klondike.*;
import java.util.*;

/**
 * TableauView of PileView
 */
public class TableauView extends PileView {

    private final double yOffset = UiMetrics.Y_OFFSET;
    private final BoardView boardView;
    private final int colIndex;

    public TableauView(Pile pile, BoardView boardView, int colIndex) {
        super(pile);
        this.boardView = boardView;
        this.colIndex = colIndex;

        setMinSize(UiMetrics.CARD_W, UiMetrics.CARD_H);
        setPrefSize(UiMetrics.CARD_W, UiMetrics.CARD_H);
        setMaxSize(UiMetrics.CARD_W, Double.MAX_VALUE); // height can grow
        setPickOnBounds(true); // empty slots clickable
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

                if (boardView.hasSelection() && colIndex != boardView.getSelectedSourceCol()) {
                    boardView.onTableauCardClicked(colIndex, row);
                    e.consume();
                    return;
                }

                boolean isTopCard = (cv == cardViews.get(cardViews.size() - 1));

                if (isTopCard && !boardView.hasSelection()) {
                    Tableau tab = boardView.getBoard().getTableau(colIndex);
                    int before = tab.size();

                    boardView.tryTableauToFoundation(colIndex, cv.getCard().getSuit());

                    // if it didn't move to foundation
                    if (tab.size() == before) {
                        boardView.onTableauCardClicked(colIndex, row);
                    }

                    e.consume();
                    return;
                }

                // else wise ... normal selection / deselect logic / not top card ...
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
