package game;

import java.util.Scanner;

public class Game {
    private Board board;          // Instancia del tablero
    private Player playerX;       // Jugador X
    private Player playerO;       // Jugador O
    private Player currentPlayer;  // Jugador actual
    private int moveCount;        // Contador de movimientos

    // Constructor
    public Game() {
        board = new Board();              // Crea un nuevo tablero
        playerX = new Player("X", "Jugador 1"); // Crea el jugador X
        playerO = new Player("O", "Jugador 2"); // Crea el jugador O
        currentPlayer = playerX;          // Establece el jugador actual como X
        moveCount = 0;                    // Inicializa el contador de movimientos
    }

    // Método para iniciar el juego
    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Turno de: " + currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")");
            board.display();

            // Solicita al jugador que ingrese la posición
            System.out.print("Ingrese fila (0-2) y columna (0-2) separados por un espacio: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Realiza el movimiento
            if (board.makeMove(row, col, currentPlayer.getSymbol())) {
                moveCount++;

                // Verifica si hay un ganador
                String winner = board.checkWinner();
                if (!winner.isEmpty()) {
                    board.display();
                    System.out.println("¡El ganador es: " + winner + "!");
                    break;
                }

                // Verifica si el tablero está lleno
                if (board.isFull()) {
                    board.display();
                    System.out.println("¡Es un empate!");
                    break;
                }

                // Cambia al siguiente jugador
                currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
            } else {
                System.out.println("Movimiento inválido. Intente de nuevo.");
            }
        }
        scanner.close();
    }
}
