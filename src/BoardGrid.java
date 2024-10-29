import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardGrid extends JPanel {
    private static final int SIZE = 10;
    private ArrayList<Integer> playerPos;
    private final Map<Integer, Integer> snakesAndLadders;
    private final Color[] playerColors = {
        Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.CYAN, Color.YELLOW
    };
    private Image backgroundImage;
    private int cellSize; 

    public BoardGrid(ArrayList<Integer> playPos) {
        this.playerPos = playPos;
        snakesAndLadders = new HashMap<>();
        initializeBoard();
        initializeSnakesAndLadders();
        loadImage();
    }

    private void loadImage() {
        try {
            backgroundImage = ImageIO.read(new File("images\\background.png"));
            cellSize = backgroundImage.getWidth(null) / SIZE;
        } catch (IOException e) {
            System.out.println("Background image could not be loaded.");
        }
    }

    private void initializeBoard() {
        setLayout(null); 
        for (int row = SIZE - 1; row >= 0; row--) {
            for (int col = 0; col < SIZE; col++) {
                int pos = row * SIZE + (row % 2 == 0 ? col + 1 : SIZE - col);
                JLabel cell = new JLabel(String.valueOf(pos), SwingConstants.CENTER);
                cell.setBounds(col * cellSize, (SIZE - 1 - row) * cellSize, cellSize, cellSize);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(cell);
            }
        }
    }

    private void initializeSnakesAndLadders() {
        // Define snakes (start -> end)
        snakesAndLadders.put(28, 10);
        snakesAndLadders.put(37, 3);
        snakesAndLadders.put(47, 16);
        snakesAndLadders.put(75, 32);
        snakesAndLadders.put(94, 71);
        snakesAndLadders.put(96, 42);

        // Define ladders (start -> end)
        snakesAndLadders.put(12, 50);
        snakesAndLadders.put(4, 56);
        snakesAndLadders.put(14, 55);
        snakesAndLadders.put(22, 58);
        snakesAndLadders.put(41, 79);
        snakesAndLadders.put(54, 88);
    }

    public void updatePos(ArrayList<Integer> playerPos) {
        this.playerPos = playerPos;
        revalidate();
        repaint();
    }

    private void drawPlayers(Graphics g) {
        int ovalSize = cellSize / 4; 
    
        for (int i = 0; i < playerPos.size(); i++) {
            int pos = playerPos.get(i);
            if (pos == 0) continue; // Player not yet started
    
            int row = SIZE - 1 - (pos - 1) / SIZE;
            int col = ((pos - 1) / SIZE) % 2 == 0 ? (pos - 1) % SIZE : SIZE - 1 - (pos - 1) % SIZE;
    
            int x = col * cellSize + (cellSize - ovalSize) / 2;
            int y = row * cellSize + (cellSize - ovalSize) / 2;
    
            g.setColor(playerColors[i % playerColors.length]);
            g.fillOval(x, y, ovalSize, ovalSize);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null), this);
        }

        // Draw grid lines based on cell size
        g.setColor(Color.BLACK);
        for (int i = 0; i <= SIZE; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, SIZE * cellSize);
            g.drawLine(0, i * cellSize, SIZE * cellSize, i * cellSize);
        }

        g.setFont(new Font("Arial", Font.PLAIN, cellSize / 6)); 
    for (int row = SIZE - 1; row >= 0; row--) {
        for (int col = 0; col < SIZE; col++) {
            int pos = row * SIZE + (row % 2 == 0 ? col + 1 : SIZE - col);
            int x = col * cellSize + cellSize / 4;
            int y = (SIZE - 1 - row) * cellSize + (3 * cellSize) / 4;

            g.drawString(String.valueOf(pos), x, y);
        }
    }
        
        drawPlayers(g);
    }

    public int checkSnakesAndLadders(int position) {
        return snakesAndLadders.getOrDefault(position, position);
    }
}
