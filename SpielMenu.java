import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class SpielMenu extends JFrame implements ActionListener {
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private JButton quitButton;

    public SpielMenu() {
        setTitle("Mein Spiel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout()); // Verwendung von GridBagLayout

        JLabel titleLabel = new JLabel("Willkommen zum Spiel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // GridBagConstraints für die Titelbeschriftung
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(10, 10, 30, 10); // Abstand oben, unten, links, rechts
        add(titleLabel, titleConstraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10)); // GridLayout für die Buttons, vertikale Anordnung mit Abstand

        singlePlayerButton = new JButton("Einzelspieler");
        singlePlayerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        singlePlayerButton.addActionListener(this);
        singlePlayerButton.setPreferredSize(new Dimension(300, 100)); // Festlegen der Größe des Einzelspieler-Buttons

        multiPlayerButton = new JButton("Mehrspieler");
        multiPlayerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        multiPlayerButton.addActionListener(this);
        multiPlayerButton.setPreferredSize(new Dimension(300, 100)); // Festlegen der Größe des Mehrspieler-Buttons

        quitButton = new JButton("Beenden");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        quitButton.addActionListener(this);
        quitButton.setPreferredSize(new Dimension(300, 100)); // Festlegen der Größe des Beenden-Buttons

        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiPlayerButton);
        buttonPanel.add(quitButton);

        // GridBagConstraints für das Button-Panel
        GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
        buttonPanelConstraints.gridx = 0;
        buttonPanelConstraints.gridy = 1;
        buttonPanelConstraints.gridwidth = 2;
        buttonPanelConstraints.insets = new Insets(0, 0, 10, 0); // Abstand unten
        add(buttonPanel, buttonPanelConstraints);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayerButton) {
            try {
                // Kompiliere und führe die Hintergrundklasse aus
                ProcessBuilder compiler = new ProcessBuilder("javac", "Hintergrund.java");
                compiler.inheritIO();
                Process compileProcess = compiler.start();
                compileProcess.waitFor();

                ProcessBuilder runner = new ProcessBuilder("java", "Hintergrund");
                runner.inheritIO();
                runner.start();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
            dispose(); // Hauptmenü schließen
        } else if (e.getSource() == multiPlayerButton) {
            JOptionPane.showMessageDialog(this, "Mehrspieler gestartet!");
            // Hier Code für den Mehrspieler-Modus einfügen
        } else if (e.getSource() == quitButton) {
            JOptionPane.showMessageDialog(this, "Spiel beenden!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SpielMenu();
            }
        });
    }
}
