import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Highscoreanzeige {

    private static final String HIGHSCORE_FILE = "highscore.txt";
    private static final int MAX_HIGHSCORES = 10;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            List<Integer> highScores = loadHighscores();
            createAndShowGUI(highScores);
        });
    }

    private static List<Integer> loadHighscores() {
        List<Integer> highScores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null && highScores.size() < MAX_HIGHSCORES) {
                highScores.add(Integer.parseInt(line.trim()));
            }
            Collections.sort(highScores, Collections.reverseOrder());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Konnte Highscores nicht laden.");
        }
        return highScores;
    }

    private static void createAndShowGUI(List<Integer> highScores) {
        JFrame frame = new JFrame("Highscores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        int rank = 1;
        for (int score : highScores) {
            listModel.addElement(rank++ + ". " + score);
        }

        JList<String> scoreList = new JList<>(listModel);
        frame.add(new JScrollPane(scoreList), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}