package animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AnimationHandler {
    
    private Timeline timeline; // Para manejar la animación del parpadeo

    // Método para hacer parpadear un botón (símbolo en el tablero)
    public void blink(Button button) {
        // Crear una animación de parpadeo
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> button.setVisible(!button.isVisible())),
            new KeyFrame(Duration.seconds(1.0), e -> button.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repetir indefinidamente
        timeline.play(); // Iniciar la animación
    }

    // Método para detener la animación
    public void stopBlinking() {
        if (timeline != null) {
            timeline.stop(); // Detener la animación si está en ejecución
        }
    }
}
