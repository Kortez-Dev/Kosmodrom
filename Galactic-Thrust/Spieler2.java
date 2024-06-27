import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Spieler2 extends JFrame implements ActionListener {
    private JButton beginnespiel;
  
   Kosmodromspiel kosmodromspiel;
  
   private boolean spieler2aktiv = true;
   private static boolean anzeigen = true;
    public Spieler2() {
        setTitle("Spieler2");
        setSize(400, 500); // Höhe erhöht, um Platz für den zusätzlichen Text zu schaffen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLUE);
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Spieler2");
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
        JLabel infoLabel = new JLabel("Spieler 2 ist an der Reihe!");
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

        beginnespiel = new JButton("Beginne Spiel");
        beginnespiel.setFont(new Font("Arial", Font.PLAIN, 20));
        beginnespiel.addActionListener(this);
        beginnespiel.setPreferredSize(new Dimension(300, 75));


        buttonPanel.add(beginnespiel);

        GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
        buttonPanelConstraints.gridx = 0;
        buttonPanelConstraints.gridy = 2;
        buttonPanelConstraints.gridwidth = 2;
        buttonPanelConstraints.insets = new Insets(0, 0, 10, 0); // Abstand unten
        add(buttonPanel, buttonPanelConstraints);

        setVisible(anzeigen);
        
        
    }
  
   public static void setanzeigen(boolean show) {
        anzeigen = show;
    }
  
  public boolean getspieler2aktiv() {
        return spieler2aktiv;
      }
    public void setspieler2aktiv(boolean spieler1) {
        this.spieler2aktiv = spieler2aktiv; 
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == beginnespiel) {
            try {
                ProcessBuilder compiler = new ProcessBuilder("javac", "Kosmodromspiel.java");
                compiler.inheritIO();
                Process compileProcess = compiler.start();
                compileProcess.waitFor();

                ProcessBuilder runner = new ProcessBuilder("java", "Kosmodromspiel");
                runner.inheritIO();
                runner.start();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
            dispose(); 

        } 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Spieler2();
            }
        });
    }
}
