import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class ScoreCounter extends JPanel {
    private int score = 0; // Startwert des Scores
    private JLabel scoreLabel;
  
    boolean spiellauft = false;
                                   
  
    public ScoreCounter() {
        setLayout(new FlowLayout() );


        // Label für die Score-Anzeige
        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);

        // Timer, der jede Sekunde ein Event auslöst
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (spiellauft) {
              
                score += 10; // Score um 10 erhöhen
                scoreLabel.setText("Score: " + score); // Score-Anzeige aktualisieren
          }
            }
        });

        timer.start(); // Timer starten

    }

    public void scoreaktuallisisiert(String farbe) {
        String gespeicherteFarbe = farbe;
        int abzug= 0;
        System.out.println("Klappt");
        if(spiellauft){
          
        // Berechne den Farbbereich, in dem sich der Zeiger befindet
        if (gespeicherteFarbe == "Dunkelgrün") {
            score -= 50; // Intensives Grün
        } else if (gespeicherteFarbe == "Grün") {
            score -= 25; // Schwaches Grün
        } else if (gespeicherteFarbe == "Gelb") {
            score -= 15; // Gelb
        } else if (gespeicherteFarbe == "Orange") {
            score -= 20; // Orange
        } else if(gespeicherteFarbe == "Rot"){
            score -= 10; // Rot
        }
        else if (gespeicherteFarbe == "Schwarz"){
        score -=100;
        
        }

    }
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
  
    public void setSpiellauft(boolean lauft) {
    this.spiellauft = lauft;
}

}
