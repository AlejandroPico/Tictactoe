package game;

public class Game {
	
    private Board board;          // Instancia del tablero
    private Player playerX;       // Jugador X
    private Player playerO;       // Jugador O
    private Player currentPlayer; // Jugador actual
    private int moveCount;        // Contador de movimientos

    // Constructor
    public Game() {
        board = new Board();                // Crea un nuevo tablero
        playerX = new Player("X", "Jugador 1"); // Crea el jugador X
        playerO = new Player("O", "Jugador 2"); // Crea el jugador O
        currentPlayer = playerX;            // Establece el jugador actual como X
        moveCount = 0;                      // Inicializa el contador de movimientos
    }

    // Método para obtener el tablero del juego
    public Board getBoard() {
        return board;
    }

    // Método para obtener el jugador actual
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Método para realizar un movimiento
    public boolean makeMove(int row, int col) {
        if (board.makeMove(row, col, currentPlayer.getSymbol())) {
            moveCount++; // Incrementa el contador de movimientos
            return true; // Movimiento válido
        }
        return false; // Movimiento inválido
    }

    // Método para cambiar al siguiente jugador
    public void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    // Método para obtener la cantidad de movimientos realizados
    public int getMoveCount() {
        return moveCount;
    }

    // Método para reiniciar el contador de movimientos (opcional, si decides usarlo)
    public void resetMoveCount() {
        moveCount = 0;
    }
}
