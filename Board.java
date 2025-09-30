import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * The Board class creates 7 tableau piles, 4 foundation piles, 1 stock and 1 waste. 
 * This class will also handle the initial deal onto the board
 */

public class Board {
    
    private Stock stock;
    private Waste waste;
    private EnumMap<Card.Suit, Foundation> foundations;
    private List<Tableau> tableaus;

    public Board() {
        this.stock = new Stock();
        this.waste = new Waste();

        this.foundations = new EnumMap<>(Card.Suit.class);

        this.tableaus = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            this.tableaus.add(new Tableau());
        }

        
    }
}
