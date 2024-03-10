import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class ScoreCounter extends JFrame {
    private int score = 0; // Startwert des Scores
    private JLabel scoreLabel;

    public ScoreCounter() {
        // Fenster-Setup
        setTitle("Score Counter");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout() );

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
