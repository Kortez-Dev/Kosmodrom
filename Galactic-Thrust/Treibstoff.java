import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Treibstoff extends JPanel implements KeyListener {
    private final int breiteLeiste = 20; // Breite der Treibstoffleiste
    private final int hoeheLeiste = 300; // Höhe der Treibstoffleiste
    private final Color[] farben = {Color.GREEN.darker(), Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
    // Mapping von Color zu String
    private final String[] farbnamen = {"Dunkelgrün", "Grün", "Gelb", "Orange", "Rot"};
    private int zeigerPosition = 0; // Start an der Spitze
    private boolean istPausiert = false;
    private String gespeicherteFarbe; // Typ zu String geändert
    private boolean amUnterstenPunkt = false; // Speichert, ob der Zeiger am untersten Punkt ist
    private boolean spiellauft = false;

    public Treibstoff() {
        setPreferredSize(new Dimension(140, 320)); // Etwas größer, um Platz für die Umrandung zu schaffen
        setFocusable(true);
        addKeyListener(this);
        Timer timer = new Timer(100, e -> {;
            if (spiellauft && !istPausiert) {
                zeigerPosition += 2; // Geschwindigkeit anpassen
                if (zeigerPosition >= hoeheLeiste) {
                    zeigerPosition = hoeheLeiste; // Sorgt dafür, dass der Zeiger am untersten Punkt pausiert
                    if (!amUnterstenPunkt) {
                        amUnterstenPunkt = true;
                        gespeicherteFarbe = "Schwarz";
                        pauseForThreeSeconds(); // Pausiert den Zeiger für 3 Sekunden
                    }
                } else {
                    amUnterstenPunkt = false; // Der Zeiger ist nicht am untersten Punkt

                }
            }
            repaint();
        });
        timer.start();
    }
    public void setGespeicherteFarbe(Color farbe) {
        int index = java.util.Arrays.asList(farben).indexOf(farbe);
        if(gespeicherteFarbe=="Schwarz") {
           this.gespeicherteFarbe = "Schwarz";
        }
        else {
             if (index != -1) {
                this.gespeicherteFarbe = farbnamen[index];
            }
        }
        }

    public String getGespeicherteFarbe() {
        return gespeicherteFarbe;
    }

    public boolean isAmUnterstenPunkt() {
        return this.amUnterstenPunkt;
    }



    private void pauseForThreeSeconds() {
        istPausiert = true;
        new Timer(3000, ev -> {
            istPausiert = false;
            zeigerPosition = 0; // Zeigerposition zurücksetzen
            amUnterstenPunkt = false; // Am untersten Punkt zurücksetzen, da der Zeiger oben beginnt
            ((Timer)ev.getSource()).stop(); // Den Timer stoppen
        }).start();
    }
    public boolean isAmOberstenPunkt() {
        // Gibt true zurück, wenn der Zeiger sich auf der höchsten Stelle befindet
        return zeigerPosition == 0;
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
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (!istPausiert) {
                istPausiert = true;
                int farbIndex = zeigerPosition / (hoeheLeiste / farben.length);
                gespeicherteFarbe = farbnamen[farbIndex]; // Farbe als String speichern
                pauseForThreeSeconds(); // Pausiert den Zeiger für 3 Sekunden
            }
        }                                                                    
    }
  public void keygedruckt()
  {
  System.out.println("Es klapppit");
   if (!istPausiert) {
                istPausiert = true;
                int farbIndex = zeigerPosition / (hoeheLeiste / farben.length);
                gespeicherteFarbe = farbnamen[farbIndex]; // Farbe als String speichern
                pauseForThreeSeconds(); // Pausiert den Zeiger für 3 Sekunden
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
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            treibstoff.requestFocusInWindow();
        });
    }
  public void setSpiellauft(boolean lauft) {
    this.spiellauft = lauft;
}
  
}