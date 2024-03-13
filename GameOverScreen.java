import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverScreen extends JFrame {
    private JButton closeButton;

    public GameOverScreen() {
        setTitle("Game Over");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.RED);
        setLayout(new GridBagLayout());

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // GridBagConstraints für die Game Over Beschriftung
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 2;
        labelConstraints.insets = new Insets(10, 10, 30, 10); // Abstand oben, unten, links, rechts
        add(gameOverLabel, labelConstraints);

        closeButton = new JButton("Spiel beenden");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Spiel beenden, wenn der Button geklickt wird
            }
        });

        // GridBagConstraints für den Schließen-Button
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.insets = new Insets(0, 0, 10, 0); // Abstand unten
        add(closeButton, buttonConstraints);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameOverScreen();
            }
        });
    }
}
