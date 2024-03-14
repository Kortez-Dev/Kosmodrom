import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Gewonnen extends JFrame implements ActionListener {
    private JButton singlePlayerButton;
    private JButton quitButton;

    public Gewonnen() {
        setTitle("Gewonnen");
        setSize(400, 500); // Höhe erhöht, um Platz für den zusätzlichen Text zu schaffen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.GREEN);
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Gewonnen");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = 2;
        titleConstraints.insets = new Insets(10, 10, 10, 10); // Abstand oben, unten, links, rechts
        add(titleLabel, titleConstraints);

        // Text über dem Button-Panel hinzufügen
        JLabel infoLabel = new JLabel("Glückwunsch! Sie haben das Spiel gewonnen!");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints infoConstraints = new GridBagConstraints();
        infoConstraints.gridx = 0;
        infoConstraints.gridy = 1;
        infoConstraints.gridwidth = 2;
        infoConstraints.insets = new Insets(10, 10, 30, 10); // Abstand oben, unten, links, rechts
        add(infoLabel, infoConstraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 0, 10));

        singlePlayerButton = new JButton("Zurück zum Hauptmenu");
        singlePlayerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        singlePlayerButton.addActionListener(this);
        singlePlayerButton.setPreferredSize(new Dimension(300, 75));

        quitButton = new JButton("Beenden");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        quitButton.addActionListener(this);
        quitButton.setPreferredSize(new Dimension(300, 75));

        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(quitButton);

        GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
        buttonPanelConstraints.gridx = 0;
        buttonPanelConstraints.gridy = 2;
        buttonPanelConstraints.gridwidth = 2;
        buttonPanelConstraints.insets = new Insets(0, 0, 10, 0); // Abstand unten
        add(buttonPanel, buttonPanelConstraints);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayerButton) {
            try {
                ProcessBuilder compiler = new ProcessBuilder("javac", "SpielMenu.java");
                compiler.inheritIO();
                Process compileProcess = compiler.start();
                compileProcess.waitFor();

                ProcessBuilder runner = new ProcessBuilder("java", "SpielMenu");
                runner.inheritIO();
                runner.start();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
            dispose(); 

        } else if (e.getSource() == quitButton) {
            JOptionPane.showMessageDialog(this, "Spiel beenden!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gewonnen();
            }
        });
    }
}
