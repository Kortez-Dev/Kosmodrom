import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kosmodromspiel extends JPanel implements ActionListener {
    private Image startBackgroundImage;
    private Image mainBackgroundImage;
    private Image hinwelBackgroundImage;
    private Image raketenImage;
    private Timer timer;
    private int yPos = 0;
    private int panelWidth = 700;
    private int panelHeight = 1000;
    private Treibstoff treibstoff;
    private ScoreCounter scoreCounter;
    private Spieler1 spieler1;
    private Spieler2 spieler2;
    boolean minimum = true;
    boolean spieler1aktiv = false;
    boolean spieler2aktiv = false;          
    

    private boolean pendelImGrünenBereich = false;
    private boolean gewonnen = false;

    private static final int HALF_CIRCLE_RADIUS = 150;
    private static final int POINTER_LENGTH = 100;
    private static final int POINTER_WIDTH = 10;
    private static final int CENTER_X = 350;
    private static final int CENTER_Y = 850;
    private double pointerAngle;
    private boolean increasing;
    private boolean spiellauft = false;

    private static final int NUM_TANKS = 3;
    private JLabel[] tankLabels = new JLabel[NUM_TANKS];
    private JLabel spitzeLabel;

    private int currentTankIndex = 0;
    int score = 0;

    private static final String HIGHSCORE_FILE = "highscore.txt";

    public Kosmodromspiel() {
        ImageIcon startImageIcon = new ImageIcon("hafenbild.png");
        ImageIcon mainImageIcon = new ImageIcon("hinwel(1).png");
        ImageIcon hinwelImageIcon = new ImageIcon("hinwel(1).png");
        ImageIcon raketenIcon = new ImageIcon("rakete.png");
        startBackgroundImage = startImageIcon.getImage();
        mainBackgroundImage = mainImageIcon.getImage();
        hinwelBackgroundImage = hinwelImageIcon.getImage();
        raketenImage = raketenIcon.getImage();

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Timer für Hintergrundanimation initialisieren
        timer = new Timer(50, this);
        timer.start();

        // KeyBindings für die Leertaste und die Taste "e" hinzufügen
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        KeyStroke spaceKey = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        inputMap.put(spaceKey, "gameOver");
        actionMap.put("gameOver", new AbstractAction() {;
            @Override
            public void actionPerformed(ActionEvent e) {  
            if(!spiellauft){
              
                if (!pendelImGrünenBereich) {
                    // Öffne das GameOverScreen-Programm
                    try {
                        ProcessBuilder pb = new ProcessBuilder("java", "GameOverScreen.java");
                        pb.start();
                        // Schließe das Hauptspiel-Fenster
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Kosmodromspiel.this);
                        frame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    pendelwin();
                    spiellauft = true;
                    treibstoff.setVisible(true);
                    scoreCounter.setVisible(true);
                    treibstoff.setSpiellauft(spiellauft);
                    scoreCounter.setSpiellauft(spiellauft);
          
                    
                }
            }
        }
        });  
           
        KeyStroke eKey = KeyStroke.getKeyStroke(KeyEvent.VK_E, 0);
        inputMap.put(eKey,"removeNextTank");
        actionMap.put("removeNextTank", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeNextTank();
                keypres();
                
            }
        });

        // Tank Labels initialisieren
        JPanel centralPanel = new JPanel(new BorderLayout());
        JPanel tankPanel = new JPanel();
        tankPanel.setLayout(new BoxLayout(tankPanel, BoxLayout.Y_AXIS));

        ImageIcon spitzeIcon = new ImageIcon(getClass().getResource("/spitze.png"));
        ImageIcon scaledSpitzeIcon = scaleImageIcon(spitzeIcon, 50, 50);
        spitzeLabel = new JLabel(scaledSpitzeIcon);
        spitzeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create tank labels
        for (int i = 0; i < tankLabels.length; i++) {
            ImageIcon originalTankIcon = new ImageIcon(getClass().getResource("/tank.png"));
            ImageIcon scaledTankIcon = scaleImageIcon(originalTankIcon, 50, 50);
            tankLabels[i] = new JLabel(scaledTankIcon);
            tankLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT); // Center align tank labels
            tankPanel.add(tankLabels[i]);
        }

        centralPanel.add(tankPanel, BorderLayout.CENTER);
        centralPanel.add(spitzeLabel, BorderLayout.NORTH);

        // Center the tank panel
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(centralPanel, BorderLayout.CENTER);

        add(containerPanel);
    

        // Treibstoffleiste initialisieren und zum Layout hinzufügen
        treibstoff = new Treibstoff();
        add(treibstoff, BorderLayout.EAST);

           // Anfordern des Fokus, damit KeyEvents funktionieren
        treibstoff.setFocusable(true);
        treibstoff.requestFocusInWindow();
    
        // KeyListener für Treibstoff
        treibstoff.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
            treibstoff.setFocusable(true);
            treibstoff.requestFocusInWindow();
                keypres();
            }
        });

        // Fenster sichtbar machen
        setVisible(true);
        treibstoff.setVisible(false);

       
    
        scoreCounter = new ScoreCounter();
        add(scoreCounter, BorderLayout.CENTER);
        
        scoreCounter.setVisible(false);
    


    

        Timer checkTimer = new Timer(100, e -> aktualisiereScoreWennAmUnterstenPunkt());
        checkTimer.start();
    
        Spieler1.setanzeigen(false);
        spieler1 = new Spieler1(); 
        Spieler2.setanzeigen(false);    
        spieler2 = new Spieler2();    
    }
    
  
  public void keypres() {

  treibstoff.keygedruckt();
    scoreCounter.scoreaktuallisisiert(treibstoff.getGespeicherteFarbe());

    }
    public void keyres(KeyEvent e) {}

    public void keytyp(KeyEvent e) { }
  
  
  


    // Methode, die aufgerufen wird, wenn der Spieler gewonnen hat
    public void pendelwin() {   
        gewonnen = true;  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Hintergrundbilder zeichnen
        g.drawImage(startBackgroundImage, 0, yPos, panelWidth, panelHeight, this);
        g.drawImage(mainBackgroundImage, 0, yPos - panelHeight, panelWidth, panelHeight, this);
        g.drawImage(hinwelBackgroundImage, 0, yPos - (panelHeight * 2), panelWidth, panelHeight, this);

        // Rakete zeichnen
        if (gewonnen) {
            int raketenX = (panelWidth - raketenImage.getWidth(this)) / 2;
            int raketenY = (panelHeight - raketenImage.getHeight(this)) / 2;
            g.drawImage(raketenImage, raketenX, raketenY, this);
        }

        // Halbkreis und Zeiger zeichnen, nur wenn nicht gewonnen
        if (!gewonnen) {
            drawHalfCircle(g);
            drawPointer(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gewonnen) {
            moveBackground();
        }
        movePointer();
        repaint();
        if (gewonnen) {
            yPos += 5;
            if (yPos >= panelHeight * 2) {
                yPos = panelHeight;
            }
        }
    }

    private void moveBackground() {
        yPos += 5;
        if (yPos >= panelHeight * 2) {
            yPos = panelHeight;
        }
    }

    private void drawHalfCircle(Graphics g) {
        int x = CENTER_X - HALF_CIRCLE_RADIUS;
        int y = CENTER_Y - HALF_CIRCLE_RADIUS;

        // Zeichne den linken blauen Teil des Halbkreises (Grad 0 bis 60)
        g.setColor(Color.BLUE);
        g.fillArc(x, y, HALF_CIRCLE_RADIUS * 2, HALF_CIRCLE_RADIUS * 2, 0, 60);

        // Zeichne den grünen Teil des Halbkreises (Grad 60 bis 120)
        g.setColor(Color.GREEN);
        g.fillArc(x, y, HALF_CIRCLE_RADIUS * 2, HALF_CIRCLE_RADIUS * 2, 60, 60);

        // Zeichne den rechten blauen Teil des Halbkreises (Grad 120 bis 180)
        g.setColor(Color.BLUE);
        g.fillArc(x, y, HALF_CIRCLE_RADIUS * 2, HALF_CIRCLE_RADIUS * 2, 120, 60);
    }

    private void drawPointer(Graphics g) {
        int x2 = (int) (CENTER_X + POINTER_LENGTH * Math.sin(Math.toRadians(pointerAngle)));
        int y2 = (int) (CENTER_Y - POINTER_LENGTH * Math.cos(Math.toRadians(pointerAngle)));
        int x3 = (int) (CENTER_X + POINTER_WIDTH / 2 * Math.sin(Math.toRadians(pointerAngle + 90)));
        int y3 = (int) (CENTER_Y - POINTER_WIDTH / 2 * Math.cos(Math.toRadians(pointerAngle + 90)));
        int x4 = (int) (CENTER_X - POINTER_WIDTH / 2 * Math.sin(Math.toRadians(pointerAngle + 90)));
        int y4 = (int) (CENTER_Y + POINTER_WIDTH / 2 * Math.cos(Math.toRadians(pointerAngle + 90)));

        int[] xPoints = {CENTER_X, x2, x3, x4};
        int[] yPoints = {CENTER_Y, y2, y3, y4};

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xPoints, yPoints, 4);
        g2d.dispose();
    }

    private void movePointer() {
        if (increasing) {
            pointerAngle -= 12;
            if (pointerAngle <= -90)
                increasing = false;
        } else {
            pointerAngle += 12;
            if (pointerAngle >= 90)
                increasing = true;
        }
        
        // Überprüfen, ob der Zeiger im grünen Bereich ist
        pendelImGrünenBereich = (pointerAngle >= -30 && pointerAngle <= 30);
    }

    private void removeNextTank() {
    if(spiellauft){
    spieler1aktiv = spieler1.getspieler1aktiv();
      spieler2aktiv = spieler2.getspieler2aktiv();
        if (currentTankIndex < NUM_TANKS) {
            tankLabels[currentTankIndex].setVisible(false);
            currentTankIndex++;
            adjustTankPanelLayout();
      if (currentTankIndex >= 3) {
        
        if (spieler1aktiv) {
          try {
         ProcessBuilder prb = new ProcessBuilder("java", "Spieler2.java");
          prb.start();
          // Schließe das Hauptspiel-Fenster
          JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Kosmodromspiel.this);
          frame.dispose(); 
              }
            catch(IOException ex) {
        ex.printStackTrace();
        }
        } else if(!spieler1aktiv && !spieler2aktiv) {
                try {
          ProcessBuilder pb = new ProcessBuilder("java", "Gewonnen.java");
          pb.start();
          // Schließe das Hauptspiel-Fenster
          JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Kosmodromspiel.this);
          frame.dispose();
      }
       catch(IOException ex) {
        ex.printStackTrace();
        }
        } else if (spieler2aktiv) {
                  try {
         ProcessBuilder prb = new ProcessBuilder("java", "Gewonnen.java");
          prb.start();
          // Schließe das Hauptspiel-Fenster
          JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Kosmodromspiel.this);
          frame.dispose(); 
              }
            catch(IOException ex) {
        ex.printStackTrace();
        }  
          }

        }
    }
    }
  }

    private void adjustTankPanelLayout() {
        JPanel tankPanel = (JPanel) ((JPanel) getComponent(0)).getComponent(0);
        tankPanel.revalidate();
        tankPanel.repaint();
    }

    private ImageIcon scaleImageIcon(ImageIcon originalIcon, int targetWidth, int targetHeight) {
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
  
    private static void saveHighscore(int score) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            List<Integer> highscores = new ArrayList<>();
            boolean scoreAdded = false;
            while ((line = reader.readLine()) != null) {
                int oldScore = Integer.parseInt(line.trim());
                if (score > oldScore && !scoreAdded) {
                    highscores.add(score);
                    scoreAdded = true;
                }
                if (!highscores.contains(oldScore)) {
                    highscores.add(oldScore);
                }
            }
            if (!scoreAdded) {
                highscores.add(score);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE))) {
                for (Integer highscore : highscores) {
                    writer.write(String.valueOf(highscore));
                    writer.newLine();
                }
            }
            System.out.println("Highscore erfolgreich gespeichert.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Fehler beim Speichern des Highscores.");
            e.printStackTrace();
        }
    }   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 1000);
            frame.setLocationRelativeTo(null);
            Kosmodromspiel gamePanel = new Kosmodromspiel();
            frame.add(gamePanel);
            // Hinzufügen eines WindowListeners, um sicherzustellen, dass das Panel immer den Fokus hat
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowActivated(WindowEvent e) {
                    gamePanel.requestFocusInWindow();
                }
            });
            frame.setVisible(true);
        });
    
        scoreliste();
    }
                   
  public void aktualisiereScoreWennAmUnterstenPunkt() {
        // Überprüft, ob der Zeiger am untersten Punkt ist und aktualisiert den Score


    if (treibstoff.isAmUnterstenPunkt()) {
    if (minimum) {
        scoreCounter.scoreaktuallisisiert(treibstoff.getGespeicherteFarbe());
        minimum = false;
        removeNextTank();
    }
}
        if (treibstoff.isAmOberstenPunkt()) {
            minimum = true;
        }

    }
  
  
  
    public static void scoreliste() {
        int currentScore = 700; // Beispiel-Score, der gespeichert werden soll
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
            String line;
            int highScore = 0;
            while ((line = reader.readLine()) != null) {
                int score = Integer.parseInt(line.trim());
                if (score > highScore) {
                    highScore = score;
                }
            }
            return highScore;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Konnte Highscore nicht laden. Standardwert 0 wird verwendet.");
            return 0;
        }
    }
}