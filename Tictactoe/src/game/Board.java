package game;

public class Board {
    private String[][] board; // Matriz 3x3 para representar el tablero
    private final int SIZE = 3; // Tamaño del tablero

    // Constructor
    public Board() {
        board = new String[SIZE][SIZE]; // Inicializa el tablero
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ""; // Inicializa todas las posiciones como vacías
            }
        }
    }

    // Método para mostrar el tablero
    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j].isEmpty() ? "-" : board[i][j]); // Muestra "-" si la posición está vacía
                if (j < SIZE - 1) {
                    System.out.print(" | "); // Separador entre columnas
                }
            }
            System.out.println();
            if (i < SIZE - 1) {
                System.out.println("---------"); // Separador entre filas
            }
        }
    }

    // Método para hacer un movimiento
    public boolean makeMove(int x, int y, String symbol) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y].isEmpty()) {
            board[x][y] = symbol; // Coloca el símbolo en la posición
            return true; // Movimiento exitoso
        }
        return false; // Movimiento inválido
    }

    // Método para verificar si el tablero está lleno
    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].isEmpty()) {
                    return false; // Si hay alguna posición vacía, el tablero no está lleno
                }
            }
        }
        return true; // Todas las posiciones están ocupadas
    }

    // Método para verificar si hay un ganador
    public String checkWinner() {
        // Comprobar filas
        for (int i = 0; i < SIZE; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return board[i][0]; // Ganador en la fila
            }
        }

        // Comprobar columnas
        for (int i = 0; i < SIZE; i++) {
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return board[0][i]; // Ganador en la columna
            }
        }

        // Comprobar diagonales
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return board[0][0]; // Ganador en la diagonal
        }
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return board[0][2]; // Ganador en la otra diagonal
        }

        return ""; // No hay ganador
    }
}
