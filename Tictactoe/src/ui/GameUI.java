package ui;

import game.Game;
import animation.AnimationHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameUI extends Application {
	
    private Game game;                     // Instancia del juego
    private Button[][] buttons;            // Matriz de botones para el tablero
    private AnimationHandler animationHandler; // Controlador de animaciones

    @Override
    public void start(Stage primaryStage) {
        game = new Game();                  // Crea una nueva instancia del juego
        buttons = new Button[3][3];        // Inicializa la matriz de botones
        animationHandler = new AnimationHandler(); // Crea el controlador de animaciones

        GridPane grid = new GridPane();    // Crea un GridPane para organizar los botones
        initializeButtons(grid);            // Inicializa los botones en el GridPane

        Scene scene = new Scene(grid, 300, 300); // Crea una nueva escena
        primaryStage.setTitle("Juego del Tres en Raya");
        primaryStage.setScene(scene);
        primaryStage.show();                // Muestra la ventana
    }

    // Método para inicializar los botones y agregarlos al GridPane
    private void initializeButtons(GridPane grid) {
        ButtonHandler buttonHandler = new ButtonHandler(game, buttons, animationHandler); // Crea el manejador de botones

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new Button();   // Crea un nuevo botón
                buttons[i][j].setFont(Font.font(24)); // Configura el tamaño de la fuente
                buttons[i][j].setMinSize(100, 100); // Establece el tamaño mínimo del botón
                buttons[i][j].setOnAction(buttonHandler); // Asocia el evento del botón
                grid.add(buttons[i][j], j, i); // Agrega el botón al GridPane
            }
        }
    }

    public static void main(String[] args) {
        launch(args);                        // Lanza la aplicación JavaFX
    }
}
