import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScoreCounter extends JFrame {
    private int score = 0; // Startwert des Scores
    private JLabel scoreLabel;
    private Treibstoffleiste treibstoffleiste;


    public ScoreCounter() {
        // Fenster-Setup
        setTitle("Score Counter");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout() );

        // Treibstoffleiste hinzufügen
        treibstoffleiste = new Treibstoffleiste();
        add(treibstoffleiste);


        // Label für die Score-Anzeige
        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);



        // Timer, der jede Sekunde ein Event auslöst
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score += 10; // Score um 10 erhöhen
                scoreLabel.setText("Score: " + score); // Score-Anzeige aktualisieren
            }
        });

        timer.start(); // Timer starten

        // KeyListener hinzufügen, der auf die Leertaste reagiert
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    updateScoreBasedOnFuelLevel();
                    scoreLabel.setText("Score: " + score); // Score-Anzeige aktualisieren
                }
            }
        });

        // Fenster fokussierbar machen, um KeyEvents zu empfangen
        setFocusable(true);
        requestFocusInWindow();
    }

    private void updateScoreBasedOnFuelLevel() {
        int fuelLevel = treibstoffleiste.getFuelLevel();
        // Berechne den Farbbereich, in dem sich der Zeiger befindet
        if (fuelLevel > 80) {
            score -= 100; // Intensives Grün
        } else if (fuelLevel > 60) {
            score -= 50; // Schwaches Grün
        } else if (fuelLevel > 40) {
            score -= 30; // Gelb
        } else if (fuelLevel > 20) {
            score -= 20; // Orange
        } else {
            score -= 10; // Rot
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScoreCounter().setVisible(true); // Fenster sichtbar machen
            }
        });
    }
}
