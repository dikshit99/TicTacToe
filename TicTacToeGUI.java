import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;
    private JButton resetButton;
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;
    private JLabel scoreLabel;
    
    public TicTacToeGUI() {
        setTitle("Tic Tac Toe Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        // Create game board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(50, 50, 50));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Initialize buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }
        
        // Create status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setBackground(new Color(240, 240, 240));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(new Color(0, 100, 200));
        
        scoreLabel = new JLabel("X: 0  |  O: 0  |  Draws: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        resetButton = new JButton("New Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(new Color(0, 150, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> resetGame());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));
        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.add(scoreLabel, BorderLayout.SOUTH);
        
        statusPanel.add(topPanel, BorderLayout.CENTER);
        statusPanel.add(resetButton, BorderLayout.SOUTH);
        
        // Add panels to frame
        add(boardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        
        if (!clickedButton.getText().equals("")) {
            return; // Button already clicked
        }
        
        clickedButton.setText(String.valueOf(currentPlayer));
        
        if (currentPlayer == 'X') {
            clickedButton.setForeground(new Color(0, 100, 200));
        } else {
            clickedButton.setForeground(new Color(200, 0, 0));
        }
        
        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            statusLabel.setForeground(new Color(0, 150, 0));
            if (currentPlayer == 'X') {
                xWins++;
            } else {
                oWins++;
            }
            updateScore();
            disableButtons();
            return;
        }
        
        if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
            statusLabel.setForeground(new Color(150, 150, 0));
            draws++;
            updateScore();
            return;
        }
        
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Player " + currentPlayer + "'s Turn");
        statusLabel.setForeground(new Color(0, 100, 200));
    }
    
    private boolean checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                highlightWinningButtons(buttons[i][0], buttons[i][1], buttons[i][2]);
                return true;
            }
        }
        
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                highlightWinningButtons(buttons[0][i], buttons[1][i], buttons[2][i]);
                return true;
            }
        }
        
        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            highlightWinningButtons(buttons[0][0], buttons[1][1], buttons[2][2]);
            return true;
        }
        
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            highlightWinningButtons(buttons[0][2], buttons[1][1], buttons[2][0]);
            return true;
        }
        
        return false;
    }
    
    private void highlightWinningButtons(JButton b1, JButton b2, JButton b3) {
        Color winColor = new Color(144, 238, 144);
        b1.setBackground(winColor);
        b2.setBackground(winColor);
        b3.setBackground(winColor);
    }
    
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
    
    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player X's Turn");
        statusLabel.setForeground(new Color(0, 100, 200));
    }
    
    private void updateScore() {
        scoreLabel.setText("X: " + xWins + "  |  O: " + oWins + "  |  Draws: " + draws);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}
