import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Tanks extends JFrame {
    private JLabel[] bildLabels = new JLabel[3];
    private int sichtbareBilder = 3;

    public Tanks() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tanks");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        this.setSize(800, 600); // Beispielgröße; kann nach Bedarf angepasst werden
        this.setLocationRelativeTo(null);

        // Layout und Größenanpassungen erfolgen, bevor Bilder geladen werden
        this.setVisible(true);

        Dimension frameGroesse = this.getSize();
        int zielBreite = (int) (frameGroesse.width * 0.1);
        int zielHoehe = (int) (frameGroesse.height * 0.2);

        for (int i = 0; i < bildLabels.length; i++) {
            ImageIcon originalBildIcon = new ImageIcon("C:\\Schule\\Klasse-12\\Informatik\\Informatik Projekt\\Programmierung\\Informatik-Projekt\\src\\tank.png"); // Angepasster Pfad zum Bild
            ImageIcon skaliertesBildIcon = skaliereBildIcon(originalBildIcon, zielBreite, zielHoehe);
            bildLabels[i] = new JLabel(skaliertesBildIcon);
            this.add(bildLabels[i]);
        }

        // KeyListener, um auf Leertastendruck zu reagieren
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    naechstesBildVerbergen();
                }
            }
        });
    }

    private void naechstesBildVerbergen() {
        if (sichtbareBilder > 0) {
            bildLabels[3 - sichtbareBilder].setVisible(false);
            sichtbareBilder--;
        }
    }

    private ImageIcon skaliereBildIcon(ImageIcon originalIcon, int zielBreite, int zielHoehe) {
        Image originalBild = originalIcon.getImage();
        Image skaliertesBild = originalBild.getScaledInstance(zielBreite, zielHoehe, Image.SCALE_SMOOTH);
        return new ImageIcon(skaliertesBild);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tanks::new);
    }
}
