import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Treibstoffleiste extends JPanel {
    private int fuelLevel = 100; // Prozent, 100 ist voll, 0 ist leer
    private String currentColorZone = "Unbestimmt"; // Variable zum Speichern des aktuellen Farbbereichs

    public Treibstoffleiste() {
        // Setze die bevorzugte Größe des Panels, kann angepasst werden
        this.setPreferredSize(new Dimension(100, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Größe und Position der Treibstoffleiste
        int width = 30; // Breite der Treibstoffleiste
        int height = 250; // Höhe der Treibstoffleiste
        int x = this.getWidth() - width - 10; // 10 Pixel Abstand vom rechten Rand
        int y = (this.getHeight() - height) / 2; // Vertikal zentriert

        // Zeichne das durchgehende Rechteck für die Treibstoffleiste
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Farbbereiche definieren
        Color[] colors = {
                new Color(0, 255, 0),    // Intensives Grün
                new Color(144, 238, 144),// Schwaches Grün
                Color.YELLOW,            // Gelb
                new Color(255, 165, 0),  // Orange
                Color.RED                // Rot
        };

        // Zeichne die Farbbereiche innerhalb der Treibstoffleiste
        for (int i = 0; i < colors.length; i++) {
            g.setColor(colors[i]);
            g.fillRect(x + 1, y + i * (height / colors.length) + 1, width - 1, height / colors.length - 1);
        }

        // Berechne die Position des Zeigers basierend auf dem fuelLevel
        int pointerY = y + height - (int) (height * (fuelLevel / 100.0));

        // Zeichne den schwarzen Strich als Zeiger
        g.setColor(Color.BLACK);
        g.drawLine(x, pointerY, x + width, pointerY);
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
        repaint();
    }
    public int getFuelLevel() {
        return fuelLevel;
    }
    private void checkColorZone() {
        int zone = (100 - fuelLevel) / 20; // Berechnet den Farbbereich basierend auf dem fuelLevel
        switch (zone) {
            case 0:
                currentColorZone = "Intensives Grün";
                break;
            case 1:
                currentColorZone = "Schwaches Grün";
                break;
            case 2:
                currentColorZone = "Gelb";
                break;
            case 3:
                currentColorZone = "Orange";
                break;
            case 4:
            case 5: // Fängt den Fall ab, dass fuelLevel genau 0 ist
                currentColorZone = "Rot";
                break;
            default:
                currentColorZone = "Unbestimmt";
                break;
        }
        System.out.println("Aktueller Farbbereich: " + currentColorZone);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Treibstoffleiste");
            Treibstoffleiste treibstoffleiste = new Treibstoffleiste();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(treibstoffleiste);
            frame.pack();
            frame.setLocationRelativeTo(null); // Zentriert das Fenster
            frame.setVisible(true);

            // Timer, um die Treibstoffleiste zu aktualisieren
            new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (treibstoffleiste.fuelLevel > 0) {
                        treibstoffleiste.setFuelLevel(treibstoffleiste.fuelLevel - 10);
                    }
                }
            }).start();
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        treibstoffleiste.checkColorZone();
                    }
                }
        });
        });
    }
}
