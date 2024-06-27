import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Main extends JFrame {
    private Treibstoff treibstoff;
    private ScoreCounter scoreCounter;
    boolean minimum = true;

    int score = 0;

    public Main() {
        // Fenster-Setup
        setTitle("Spiel Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Treibstoffleiste initialisieren und zum Layout hinzufügen
        treibstoff = new Treibstoff();
        add(treibstoff, BorderLayout.EAST);

        // ScorePanel (angepasste Version von ScoreCounter) initialisieren und zum Layout hinzufügen
        scoreCounter = new ScoreCounter();
        add(scoreCounter, BorderLayout.CENTER);

        // KeyListener für Treibstoff
        treibstoff.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                treibstoffKeyHandler(evt);
            }
        });

        // Fenster sichtbar machen
        setVisible(true);

        // Anfordern des Fokus, damit KeyEvents funktionieren
        treibstoff.setFocusable(true);
        treibstoff.requestFocusInWindow();
                        s
        Timer checkTimer = new Timer(100, e -> aktualisiereScoreWennAmUnterstenPunkt());
        checkTimer.start();

    }

    private void treibstoffKeyHandler(java.awt.event.KeyEvent evt) {
        // Reagiert auf Tastendruck
        // Aktualisiert den Score basierend auf der Farbe des Treibstoff-Objekts
        if (evt.getKeyCode() == KeyEvent.VK_E) {
           scoreCounter.scoreaktuallisisiert(treibstoff.getGespeicherteFarbe());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
    public void aktualisiereScoreWennAmUnterstenPunkt() {
        // Überprüft, ob der Zeiger am untersten Punkt ist und aktualisiert den Score


    if (treibstoff.isAmUnterstenPunkt()) {
    if (minimum) {
        scoreCounter.scoreaktuallisisiert(treibstoff.getGespeicherteFarbe());
        minimum = false;
    }
}
        if (treibstoff.isAmOberstenPunkt()) {
            minimum = true;
        }

    }
}