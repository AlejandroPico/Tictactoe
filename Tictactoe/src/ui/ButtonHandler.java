package ui;

import animation.AnimationHandler;
import game.Board;
import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ButtonHandler implements EventHandler<ActionEvent> {
	
    private Game game;                 // Referencia al juego
    private Board board;               // Referencia al tablero
    private AnimationHandler animation; // Controlador de animaciones
    private Button[][] buttons;        // Matriz de botones que representan el tablero

    // Constructor
    public ButtonHandler(Game game, Button[][] buttons, AnimationHandler animation) {
        this.game = game;              // Inicializa la referencia del juego
        this.board = game.getBoard();  // Obtiene el tablero del juego
        this.animation = animation;     // Inicializa el controlador de animaciones
        this.buttons = buttons;         // Inicializa la matriz de botones
    }

    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource(); // Obtiene el botón que fue clickeado
        int row = -1, col = -1;

        // Encuentra la posición del botón en la matriz
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                }
            }
        }
        
     // Busca la posición del botón
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (row == -1 || col == -1) return;

        if (game.makeMove(row, col)) {
            clickedButton.setText(game.getCurrentPlayer().getSymbol());
            clickedButton.setDisable(true);

            String winner = game.getBoard().checkWinner();
            if (!winner.isEmpty()) {
                showWinner(winner);
                disableAllButtons();
                animation.stopBlinking();
            } else if (game.getBoard().isFull()) {
                showTie();
                disableAllButtons();
                animation.stopBlinking();
            } else {
                if (game.getMoveCount() > 3) {
                    animation.blink(buttons[row][col]);
                }
                game.switchPlayer();
            }
        } else {
            System.out.println("Movimiento inválido");
        }

        // Verifica si el movimiento es válido
        if (game.makeMove(row, col)) {
            // Actualiza la interfaz gráfica
            clickedButton.setText(game.getCurrentPlayer().getSymbol());
            clickedButton.setDisable(true); // Desactiva el botón una vez que se ha hecho clic

            // Verifica si hay un ganador o si el tablero está lleno
            String winner = board.checkWinner();
            if (!winner.isEmpty()) {
                // Muestra el ganador
                System.out.println("¡El ganador es: " + winner + "!");
                // Aquí puedes agregar lógica para mostrar un mensaje en la interfaz
            } else if (board.isFull()) {
                System.out.println("¡Es un empate!");
                // Aquí puedes agregar lógica para mostrar un mensaje en la interfaz
            }

            // Si se intenta hacer un cuarto movimiento
            if (game.getMoveCount() > 3) {
                animation.blink(buttons[row][col]); // Haz parpadear el botón
            }

            // Cambia al siguiente jugador
            game.switchPlayer();
        } else {
            System.out.println("Movimiento inválido. Intente de nuevo.");
        }
    }

    private void showWinner(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Fin del juego!");
        alert.setHeaderText("¡Tenemos un ganador!");
        alert.setContentText("El jugador " + winner + " ha ganado.");
        alert.showAndWait();
    }

    private void showTie() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Fin del juego!");
        alert.setHeaderText("¡Empate!");
        alert.setContentText("El tablero está lleno.");
        alert.showAndWait();
    }

    private void disableAllButtons() {
        for (Button[] row : buttons) {
            for (Button btn : row) {
                btn.setDisable(true);
            }
        }
    }
}
