import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class ScoreCounter extends JFrame {
    private int score = 0; // Startwert des Scores
    private JLabel scoreLabel;
    private Treibstoff treibstoff;


    public ScoreCounter() {
        // Fenster-Setup
        setTitle("Score Counter");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout() );

        // Treibstoffleiste hinzufügen
        treibstoff = new Treibstoff();
        add(treibstoff);


        // Label für die Score-Anzeige
        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);



        // Timer, der jede Sekunde ein Event auslöst
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score += 20; // Score um 10 erhöhen
                scoreLabel.setText("Score: " + score); // Score-Anzeige aktualisieren
            }
        });

        timer.start(); // Timer starten

        // KeyListener hinzufügen, der auf die Leertaste reagiert
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    scoreaktuallisisiert();
                    scoreLabel.setText("Score: " + score); // Score-Anzeige aktualisieren
                }
            }
        });

        // Fenster fokussierbar machen, um KeyEvents zu empfangen
        setFocusable(true);
        requestFocusInWindow();
    }

    private void scoreaktuallisisiert() {
        Color gespeicherteFarbe = treibstoff.getGespeicherteFarbe();
        // Berechne den Farbbereich, in dem sich der Zeiger befindet
        if (gespeicherteFarbe == Color.GREEN.darker()) {
            score -= 50; // Intensives Grün
        } else if (gespeicherteFarbe == Color.GREEN) {
            score -= 25; // Schwaches Grün
        } else if (gespeicherteFarbe == Color.YELLOW) {
            score -= 15; // Gelb
        } else if (gespeicherteFarbe == Color.ORANGE) {
            score -= 20; // Orange
        } else if(gespeicherteFarbe == Color.RED){
            score -= 10; // Rot
        }
         else if (gespeicherteFarbe == Color.BLACK){
             score -=100;
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
