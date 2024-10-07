package game;

public class Player {
    private String symbol; // "X" o "O"
    private String name;   // Nombre del jugador

    // Constructor
    public Player(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    // Getter para el símbolo
    public String getSymbol() {
        return symbol;
    }

    // Getter para el nombre
    public String getName() {
        return name;
    }

    // Método para cambiar el símbolo (opcional)
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    // Método para cambiar el nombre (opcional)
    public void setName(String name) {
        this.name = name;
    }
}
