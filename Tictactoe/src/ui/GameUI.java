package ui;

import game.Game;
import animation.AnimationHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameUI extends Application {
	
    private Game game;                     // Instancia del juego
    private Button[][] buttons;            // Matriz de botones para el tablero
    private AnimationHandler animationHandler; // Controlador de animaciones

    @Override
    public void start(Stage primaryStage) {
    	game = new Game();
        buttons = new Button[3][3];
        animationHandler = new AnimationHandler();

        GridPane grid = new GridPane();
        initializeButtons(grid);

        Button restartBtn = new Button("Reiniciar");
        restartBtn.setOnAction(e -> restartGame());

        VBox root = new VBox(10, new Label("Tres en Raya"), grid, restartBtn);
        Scene scene = new Scene(root, 300, 350);

        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private void restartGame() {
        game = new Game();
        animationHandler.stopBlinking();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                buttons[i][j].setVisible(true);
            }
        }
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
