import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tanks extends JFrame implements KeyListener {
    private String imagePath = "C:\\Schule\\ber.jpg"; // Pfad zum Bild
    private ImageIcon imageIcon;
    private int imageY = 0; // Startposition der Bilder in Y-Richtung
    private int startX; // Startposition der Bilder in X-Richtung
    private int frameWidth = 2000; // Breite des Fensters
    private int frameHeight = 1000; // Höhe des Fensters
    private boolean moving = false; // Zustand der Bewegung

    public Tanks() {
        this.setTitle("Bild Bewegung Beispiel");
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);

        // Bild laden
        imageIcon = new ImageIcon(imagePath);

        // Berechne die Start-X-Position, um die Bilder zentral zu positionieren
        int totalWidth = 3 * imageIcon.getIconWidth() + 2 * 10; // Gesamtbreite aller Bilder plus Abstand
        startX = (frameWidth - totalWidth) / 2; // Zentriere horizontal

        // JPanel, das die Bilder zeichnet
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Zeichne das erste Bild an der berechneten X- und Y-Position
                g.drawImage(imageIcon.getImage(), startX, imageY, this);
                // Zeichne die Kopie des Bildes mit 10 Pixel Abstand vom ersten
                g.drawImage(imageIcon.getImage(), startX + imageIcon.getIconWidth() + 10, imageY, this);
                // Zeichne das dritte Bild mit 10 Pixel Abstand von der zweiten Kopie
                g.drawImage(imageIcon.getImage(), startX + 2 * (imageIcon.getIconWidth() + 10), imageY, this);
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
        new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moving) {
                    imageY += 10;
                    if (imageY > frameHeight - imageIcon.getIconHeight()) {
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
