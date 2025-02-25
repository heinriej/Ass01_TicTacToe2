import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TicTacToeButton extends JButton {
    private int row, col;
    private char state; // 'X', 'O', or ' '

    public TicTacToeButton(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = ' ';
        setFont(new Font("Arial", Font.BOLD, 40));
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public char getState() { return state; }

    public boolean isEmpty() { return state == ' '; }

    public void setState(char player) {
        if (isEmpty()) {
            this.state = player;
            setText(String.valueOf(player));
        }
    }
}

class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons = new TicTacToeButton[3][3];
    private char currentPlayer = 'X';
    private int moveCount = 0;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToeButton button = (TicTacToeButton) e.getSource();
                if (!button.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Illegal move! Try again.");
                    return;
                }

                button.setState(currentPlayer);
                moveCount++;

                if (checkWin(currentPlayer)) {
                    int response = JOptionPane.showConfirmDialog(null, currentPlayer + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                    return;
                }

                if (moveCount == 9) {
                    int response = JOptionPane.showConfirmDialog(null, "It's a tie! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                    return;
                }

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new TicTacToeButton(i, j);
                buttons[i][j].addActionListener(buttonListener);
                add(buttons[i][j]);
            }
        }

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getState() == player && buttons[i][1].getState() == player && buttons[i][2].getState() == player)
                return true;
            if (buttons[0][i].getState() == player && buttons[1][i].getState() == player && buttons[2][i].getState() == player)
                return true;
        }
        if (buttons[0][0].getState() == player && buttons[1][1].getState() == player && buttons[2][2].getState() == player)
            return true;
        if (buttons[0][2].getState() == player && buttons[1][1].getState() == player && buttons[2][0].getState() == player)
            return true;
        return false;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setState(' ');
            }
        }
        currentPlayer = 'X';
        moveCount = 0;
    }
}