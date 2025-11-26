package klondike;
public class TextViews {
    public static String welcomeMenu() {
        return "Welcome to Klondike Solitaire!\n"
                + "1. Play Game\n"
                + "2. Controls\n"
                + "3. Quit";
    }

    public static void bye() {
        System.out.println("Bye for now!");
    }
    
    public static void printInvalid() {
        System.out.println("Invalid input");
    }

    public static String howToPlay() {
        StringBuilder sb = new StringBuilder();

        sb.append("How To Play on Console: \n");
        sb.append("-----------------------------------\n");
        sb.append("x - draw card\n");
        sb.append("t - play card waste to tableau\n");
        sb.append("f - play card waste to foundation\n");
        sb.append("a - play card from tableau to tableau\n");
        sb.append("w - play card foundation to tableau\n");
        sb.append("e - tableau to foundation\n");
        sb.append("r - recycle -> push cards from waste back into stock\n");
        sb.append("q - quit\n");
        sb.append("------------------------------------\n");
        sb.append("\n");

        sb.append("Select a Foundation: \n");
        sb.append("d  - DIAMOND\n");
        sb.append("h - HEARTS\n");
        sb.append("c - CLUBS\n");
        sb.append("s - SPADES\n");
        return sb.toString();
    }   
    
    public static String rules() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rules to the game: \n");
        sb.append("Draw a card from stock to the waste pile.");
        sb.append("The card in the waste can be played into either the foundations, \n");
        sb.append("or the Tableau columns.\n");

        sb.append("Initially, cards on the tableau are randomly dealt, and your goal is to sort the tableau.");
        sb.append("Cards on the tableau are sorted from 1 lower rank, and alternating color. \n");
        sb.append("Between black (CLUBS AND SPADES) and red (HEARTS AND DIAMONDS)\n");
        sb.append("Cards on the foundation is where you sort cards in ascending order from ACE (1) -> KING (13)\n");
        sb.append("Foundations are sorted by SUITS. There is 4 foundations, one for each SUIT.\n");

        return sb.toString();
    }
}
