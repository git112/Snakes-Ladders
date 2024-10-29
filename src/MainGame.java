import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGame extends JFrame {
    public final BoardGrid board;
    private final ArrayList<Integer> playPos = new ArrayList<>();
    private int currentPlayer = 0;
    private final int numOfPlayers;

    public MainGame() {
        String input = JOptionPane.showInputDialog("Enter number of players\n(Then click on Roll Dice button to start the game) ");
        numOfPlayers = Integer.parseInt(input);

        for (int i = 0; i < numOfPlayers; i++) {
            playPos.add(0);
        }

        board = new BoardGrid(playPos);

        JButton rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener(new DiceRollListener());
        add(board, BorderLayout.CENTER);
        add(rollDiceButton, BorderLayout.SOUTH);

        setTitle("Snakes and Ladders");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class DiceRollListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int diceRoll = (int) (Math.random() * 6 + 1);
            JOptionPane.showMessageDialog(null, "Player " + (currentPlayer + 1) + " rolled a " + diceRoll);

            int newPos = playPos.get(currentPlayer) + diceRoll;
            if (newPos <= 100) {
                newPos = board.checkSnakesAndLadders(newPos);
                playPos.set(currentPlayer, newPos);
                board.updatePos(playPos);
            }

            if (newPos == 100) {
                JOptionPane.showMessageDialog(null, "Player " + (currentPlayer + 1) + " wins!");
                System.exit(0);
            }

            currentPlayer = (currentPlayer + 1) % numOfPlayers;
        }
    }
    public static void main(String[] args) {
        new MainGame();
    }
}
