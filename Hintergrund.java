import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Hintergrund extends JPanel implements ActionListener {

    private BufferedImage hintergrundBild;
    private Timer timer;
    private int yVersatz = 0;

    public Hintergrund() {
        // Lade das Bild. Ändere den Pfad zum Bild entsprechend.
        hintergrundBild = ladeBild("C:\\Schule\\ber.jpg");

        // Setze einen Timer, der das actionPerformed Event alle 100 ms auslöst
        timer = new Timer(100, this);
        timer.start();
    }

    private BufferedImage ladeBild(String pfad) {
        ImageIcon icon = new ImageIcon(pfad);
        Image tmpBild = icon.getImage();
        BufferedImage bImage = new BufferedImage(tmpBild.getWidth(null), tmpBild.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(tmpBild, 0, 0, null);
        bGr.dispose();

        return bImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Berechne neue Breite und Höhe, um das Bild an die Fenstergröße anzupassen
        int neueBreite = this.getWidth();
        int neueHoehe = (int)(((double)neueBreite / hintergrundBild.getWidth()) * hintergrundBild.getHeight());

        // Zeichne das Bild mehrmals, um den Hintergrund nahtlos zu füllen
        for (int y = -neueHoehe + yVersatz; y < getHeight(); y += neueHoehe) {
            g.drawImage(hintergrundBild, 0, y, neueBreite, neueHoehe, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Bewege das Bild nach unten
        yVersatz += 1;

        // Setze den Versatz zurück, wenn das Ende des Bildes erreicht ist, um ein nahtloses Scrollen zu ermöglichen
        if (yVersatz >= hintergrundBild.getHeight()) {
            yVersatz = 0;
        }

        // Fordere eine Neumalung des Panels an, um das aktualisierte Bild anzuzeigen
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hintergrund Beispiel");
        Hintergrund hintergrund = new Hintergrund();
        frame.setContentPane(hintergrund);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
