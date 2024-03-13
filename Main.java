import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Treibstoff treibstoff;
    private ScorePanel scorePanel;
    private Hintergrund hintergrund;

    public Main() {
        // Fenster-Setup
        setTitle("Spiel Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        //Hintergrund initialisieren und zum Layout hinzufügen
        hintergrund = new Hintergrund();
        add(hintergrund,BorderLayout.CENTER);
        setVisible(true);


        // Treibstoffleiste initialisieren und zum Layout hinzufügen
        treibstoff = new Treibstoff();
        add(treibstoff, BorderLayout.EAST);

        // ScorePanel (angepasste Version von ScoreCounter) initialisieren und zum Layout hinzufügen
        scorePanel = new ScorePanel();
        add(scorePanel, BorderLayout.CENTER);
        // Fenster sichtbar machen

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}

// ScoreCounter als JPanel anstatt JFrame
class ScorePanel extends JPanel {
    private int score = 0;
    private JLabel scoreLabel;

    public ScorePanel() {
        setLayout(new FlowLayout());
        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);

        // Timer, der jede Sekunde den Score erhöht und aktualisiert
        new Timer(1000, e -> {
            score += 10;
            scoreLabel.setText("Score: " + score);
        }).start();
    }
}
