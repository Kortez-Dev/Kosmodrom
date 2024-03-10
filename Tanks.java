import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tanks extends JFrame implements KeyListener {
    private String imagePath = "C:\\Schule\\ber.jpg"; // Aktualisierter Pfad zum Bild
    private ImageIcon imageIcon;
    private int imageY = 0; // Startposition der Bilder
    private int frameWidth = 1000; // Breite des Fensters
    private int frameHeight = 1000; // Höhe des Fensters
    private boolean moving = false; // Zustand der Bewegung

    public Tanks() {
        this.setTitle("Bild Bewegung Beispiel");
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);

        // Bild laden
        imageIcon = new ImageIcon(imagePath);

        // JPanel, das die Bilder zeichnet
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Zeichne das erste Bild an der aktuellen Y-Position
                g.drawImage(imageIcon.getImage(), 50, imageY, this);
                // Zeichne die Kopie des Bildes direkt neben dem Original
                g.drawImage(imageIcon.getImage(), 50 + imageIcon.getIconWidth() + 10, imageY, this); // 10 Pixel Abstand
            }
        };

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !moving) {
            moving = true; // Starte die Bewegung, wenn die Leertaste gedrückt wird und nicht bereits in Bewegung
            moveImages();
        }
    }

    private void moveImages() {
        new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moving) {
                    imageY += 5;
                    if (imageY > frameHeight+1000 - imageIcon.getIconHeight()) {
                        imageY = 0; // Setze die Bilder zurück auf die ursprüngliche Position
                        moving = false; // Stoppe die Bewegung
                        ((Timer)e.getSource()).stop(); // Stoppe den Timer
                    }
                    repaint(); // Panel neu zeichnen, um die Bilder an der neuen Position zu zeigen
                }
            }
        }).start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new Tanks();
    }
}
