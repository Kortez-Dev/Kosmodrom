
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hintergrund extends JPanel implements ActionListener {
    private Image backgroundImage;
    private Timer timer;
    private int yPos = 0; // Startposition des Bildes

    public Hintergrund() {
        // Hintergrundbild laden
        backgroundImage = new ImageIcon("C:\\Schule\\ber.jpg").getImage();

        // Timer mit Verzögerung (hier: 100ms) initialisieren und starten
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Aktuelle Größe des Panels ermitteln
        int width = getWidth();
        int height = getHeight();

        // Berechnen, wie oft das Bild vertikal wiederholt werden muss
        int imageWidth = backgroundImage.getWidth(this);
        int imageHeight = backgroundImage.getHeight(this);

        // Hintergrundbilder zeichnen
        for (int x = 0; x < width; x += imageWidth) {
            for (int y = yPos - imageHeight; y < height; y += imageHeight) {
                g.drawImage(backgroundImage, x, y, this);
            }
        }

        // Animation für flüssiges Scrolling
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // yPos aktualisieren, um das Bild nach unten zu bewegen
        yPos += 2;

        // yPos zurücksetzen, wenn das Ende erreicht ist
        if (yPos > backgroundImage.getHeight(this)) {
            yPos = 0;
        }

        // Panel neu zeichnen, um die Animation zu aktualisieren
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new Hintergrund());
        frame.setVisible(true);
    }
}
