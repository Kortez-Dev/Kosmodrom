import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.EventQueue;


public class Highscoreliste {

    private static final String HIGHSCORE_FILE = "highscore.txt";

    public static void main(String[] args) {
        int currentScore = 500; // Beispiel-Score, der gespeichert werden soll
        int highScore = loadHighscore();

        System.out.println("Aktueller Highscore: " + highScore);

        if (currentScore > highScore) {
            System.out.println("Neuer Highscore! " + currentScore);
            saveHighscore(currentScore);
        } else {
            System.out.println("Kein neuer Highscore. Aktueller Score: " + currentScore);
        }
    }

    private static int loadHighscore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Konnte Highscore nicht laden. Standardwert 0 wird verwendet.");
        }
        return 0; // Wenn Datei nicht existiert, leer ist oder ein Fehler auftritt, gebe 0 zurück
    }

    private static void saveHighscore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE))) {
            writer.write(String.valueOf(score));
            System.out.println("Highscore erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern des Highscores.");
            e.printStackTrace();
        }
    }
}