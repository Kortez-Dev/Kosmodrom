import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Treibstoff extends JPanel implements KeyListener {
    private final int breiteLeiste = 20; // Breite der Treibstoffleiste
    private final int hoeheLeiste = 300; // Höhe der Treibstoffleiste
    private final Color[] farben = {Color.GREEN.darker(), Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
    private int zeigerPosition = 0; // Start an der Spitze
    private boolean istPausiert = false;
    private Color gespeicherteFarbe;

    public Treibstoff() {
        setPreferredSize(new Dimension(140, 320)); // Etwas größer, um Platz für die Umrandung zu schaffen
        setFocusable(true);
        addKeyListener(this);
        Timer timer = new Timer(100, e -> {
            if (!istPausiert) {
                zeigerPosition += 2; // Geschwindigkeit anpassen
                if (zeigerPosition > hoeheLeiste) {
                    zeigerPosition = 0; // Zurück an den Anfang
                }
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Zentriert die Treibstoffleiste vertikal und positioniert sie rechts innerhalb des Panels
        int xPosition = this.getWidth() - breiteLeiste - 30; // Rechtsbündig mit Abstand
        int yPosition = (this.getHeight() - hoeheLeiste) / 2; // Vertikal zentriert

        // Zeichnet die Farbsegmente der Treibstoffleiste
        int abschnittHoehe = hoeheLeiste / farben.length;
        for (int i = 0; i < farben.length; i++) {
            g.setColor(farben[i]);
            g.fillRect(xPosition, yPosition + i * abschnittHoehe, breiteLeiste, abschnittHoehe);
        }

        // Zeichnet die schwarze Umrandung um die Treibstoffleiste
        g.setColor(Color.BLACK);
        g.drawRect(xPosition - 1, yPosition - 1, breiteLeiste + 2, hoeheLeiste + 2);

        // Zeichnet den Zeiger
        g.drawLine(xPosition - 5, yPosition + zeigerPosition, xPosition + breiteLeiste + 5, yPosition + zeigerPosition);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!istPausiert) {
                istPausiert = true;
                gespeicherteFarbe = farben[zeigerPosition / (hoeheLeiste / farben.length)];
                new Timer(3000, ev -> {
                    istPausiert = false;
                    zeigerPosition = 0; // Zeigerposition zurücksetzen
                    ((Timer)ev.getSource()).stop(); // Den Timer stoppen
                }).start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Treibstoffanzeige");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Treibstoff treibstoff = new Treibstoff();
            frame.add(treibstoff);
            frame.pack(); // Passt die Frame-Größe an
            frame.setLocationRelativeTo(null); // Zentriert das Fenster auf dem Bildschirm
            frame.setVisible(true);
            treibstoff.requestFocusInWindow(); // Um Tasteneingaben zu empfangen
        });
    }
}
