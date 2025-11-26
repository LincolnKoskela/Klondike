package klondike;
/**
 * This class represents a frozen picture of the board at a given moment.
 * All data / no logic.
 * GameState is just a container.
 */
import java.util.*;

public class GameState {

    private Stock stock;
    private Waste waste;
    private EnumMap<Card.Suit, Foundation> foundations;
    private List<Tableau> tableaus;

    public GameState(Stock stock, Waste waste, 
    EnumMap<Card.Suit, Foundation> foundations, List<Tableau> tableaus) {
        this.stock = stock;
        this.waste = waste;
        this.foundations = foundations;
        this.tableaus = tableaus;
    }

    // getters
    public Stock getStock() {return stock;}
    public Waste getWaste() {return waste;}
    public EnumMap<Card.Suit, Foundation> getFoundation() {return foundations;}
    public List<Tableau> getTableaus() {return tableaus;}
}
