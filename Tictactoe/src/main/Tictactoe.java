package main;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class Tictactoe extends JFrame {
    // Matriz de botones para el tablero
    private JButton[][] buttons = new JButton[3][3];
    // Botón para gestionar el final de partida
    private JButton btnEndGame;
    
    // Símbolos escogidos
    private char playerSymbol;    // El símbolo elegido por el jugador (por ejemplo, “X”)
    @SuppressWarnings("unused")
	private char opponentSymbol;  // El otro símbolo (“O”)
    // Variable para controlar de quién es el turno
    private char currentPlayer;
    
    // Se utilizan dos colas para llevar el orden de los movimientos de cada jugador
    private LinkedList<Point> movesX = new LinkedList<>();
    private LinkedList<Point> movesO = new LinkedList<>();
    
    // Bandera para indicar si la partida terminó
    private boolean gameEnded = false;
    
    // Colores para cada símbolo y para la animación (por ejemplo, X en rojo, O en azul)
    private Color colorX = Color.RED;
    private Color colorO = Color.BLUE;
    private Color highlightColor = Color.YELLOW; // Color de resaltado para la combinación ganadora
    
    // Cantidad máxima de X o O admitidas por jugador
    private Integer maxSymbols = 3;

    public Tictactoe() {
        setTitle("Tres en Raya");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        
        initUI();      // Inicializa el tablero y los controles
        chooseSymbol(); // Pregunta al usuario con qué símbolo jugar
        
        // Definimos que inicie la partida con el símbolo escogido por el usuario
        currentPlayer = playerSymbol;
    }
    
    // Inicializa la interfaz gráfica
    private void initUI() {
        // Panel para el tablero (3x3)
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);
        
        // Se crean los 9 botones
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                JButton btn = new JButton("");
                btn.setFont(font);
                final int row = i;
                final int col = j;
                btn.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Sólo se permiten movimientos si la partida no ha finalizado
                        if (!gameEnded) {
                            handleMove(row, col);
                        }
                    }
                });
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }
        }
        
        // Panel de control para el botón de “Terminar partida”
        JPanel controlPanel = new JPanel();
        btnEndGame = new JButton("Terminar partida");
        btnEndGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameEnded) {
                    // Si la partida ya terminó, se pregunta si se quiere iniciar otra
                    int result = JOptionPane.showConfirmDialog(null, 
                            "¿Desea iniciar otra partida?", 
                            "Nueva Partida", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        resetGame();
                    }
                } else {
                    // Permite terminar la partida en curso
                    int result = JOptionPane.showConfirmDialog(null, 
                            "¿Seguro que desea terminar la partida actual?", 
                            "Terminar Partida", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        gameEnded = true;
                        JOptionPane.showMessageDialog(null, "Partida terminada.");
                    }
                }
            }
        });
        controlPanel.add(btnEndGame);
        
        // Se agregan los paneles al frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
    }
    
    // Método que pregunta al usuario qué símbolo desea jugar
    private void chooseSymbol() {
        Object[] options = {"X", "O"};
        int n = JOptionPane.showOptionDialog(this,
            "¿Con qué símbolo deseas jugar?",
            "Elección de símbolo",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        if(n == JOptionPane.YES_OPTION){
            playerSymbol = 'X';
            opponentSymbol = 'O';
        } else {
            playerSymbol = 'O';
            opponentSymbol = 'X';
        }
    }
    
    // Se procesa el movimiento en la casilla (row, col)
    private void handleMove(int row, int col) {
        // Si la celda ya tiene un símbolo se ignora el clic
        if (!buttons[row][col].getText().equals("")){
            return;
        }
        
        // Se coloca el símbolo del jugador actual en la celda
        buttons[row][col].setText(String.valueOf(currentPlayer));
        if(currentPlayer == 'X'){
            buttons[row][col].setForeground(colorX);
        } else {
            buttons[row][col].setForeground(colorO);
        }
        
        // Se añade la posición a la lista de movimientos correspondiente.
        // Si el jugador ya tiene 3 fichas en el tablero, se elimina la más antigua.
        if (currentPlayer == 'X') {
            movesX.add(new Point(row, col));
            if(movesX.size() > maxSymbols){
                Point oldest = movesX.removeFirst();
                buttons[oldest.x][oldest.y].setText("");
            }
        } else {
            movesO.add(new Point(row, col));
            if(movesO.size() > maxSymbols){
                Point oldest = movesO.removeFirst();
                buttons[oldest.x][oldest.y].setText("");
            }
        }
        
        // Se verifica si el jugador actual, al tener 3 fichas, consigue una línea ganadora.
        if ((currentPlayer == 'X' && movesX.size() == maxSymbols) || (currentPlayer == 'O' && movesO.size() == maxSymbols)) {
            List<Point> winningCombo = checkWinner(currentPlayer);
            if(winningCombo != null){
                gameEnded = true;
                animateWinningCombination(winningCombo);
                return;
            }
        }
        
        // Se cambia el turno al otro jugador.
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }
    
    // Comprueba si el jugador (player) tiene una combinación ganadora.
    // Se recorre la lista de combinaciones ganadoras predefinidas.
    private List<Point> checkWinner(char player) {
        // Se definen todas las combinaciones ganadoras posibles en un tablero de 3×3.
        int[][] winCombos = {
            {0,0, 0,1, 0,2},
            {1,0, 1,1, 1,2},
            {2,0, 2,1, 2,2},
            {0,0, 1,0, 2,0},
            {0,1, 1,1, 2,1},
            {0,2, 1,2, 2,2},
            {0,0, 1,1, 2,2},
            {0,2, 1,1, 2,0}
        };
        
        for (int[] combo : winCombos) {
            String a = buttons[combo[0]][combo[1]].getText();
            String b = buttons[combo[2]][combo[3]].getText();
            String c = buttons[combo[4]][combo[5]].getText();
            if(a.equals(String.valueOf(player)) && b.equals(String.valueOf(player)) && c.equals(String.valueOf(player))){
                List<Point> winningPoints = new ArrayList<>();
                winningPoints.add(new Point(combo[0], combo[1]));
                winningPoints.add(new Point(combo[2], combo[3]));
                winningPoints.add(new Point(combo[4], combo[5]));
                return winningPoints;
            }
        }
        
        return null;
    }
    
    // Anima la combinación ganadora: se hace parpadear el fondo de las celdas ganadoras y se “disimulan” las demás.
    private void animateWinningCombination(List<Point> winningPoints) {
        Timer timer = new Timer(300, null);
        final boolean[] toggle = {false};
        timer.addActionListener(new ActionListener(){
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                toggle[0] = !toggle[0];
                // Resalta las celdas ganadoras alternando el color de fondo
                for(Point p : winningPoints){
                    buttons[p.x][p.y].setBackground(toggle[0] ? highlightColor : Color.WHITE);
                }
                // “Disimula” las demás fichas cambiando su color de texto a un tono claro
                for (int i = 0; i < 3; i++){
                    for (int j = 0; j < 3; j++){
                        if (!winningPoints.contains(new Point(i, j)) && !buttons[i][j].getText().equals("")){
                            buttons[i][j].setForeground(Color.LIGHT_GRAY);
                        }
                    }
                }
                count++;
                if(count > 10){ // Se realizan varias alternancias y luego se detiene la animación
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "¡Ganador: " + currentPlayer + "!");
                }
            }
        });
        timer.start();
    }
    
    // Reinicia la partida (limpia el tablero, las listas de movimientos y reinicia las banderas)
    private void resetGame() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
        movesX.clear();
        movesO.clear();
        gameEnded = false;
        // Se vuelve a preguntar el símbolo si se desea (esto se puede omitir si se prefiere conservar la elección)
        chooseSymbol();
        currentPlayer = playerSymbol;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tictactoe game = new Tictactoe();
            game.setVisible(true);
        });
    }
}
