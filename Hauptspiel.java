import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Hauptspiel extends JPanel implements ActionListener {
    private Image startBackgroundImage;
    private Image mainBackgroundImage;
    private Image hinwelBackgroundImage;
    private Timer timer;
    private int yPos = 0;
    private int panelWidth = 700;
    private int panelHeight = 1000;

    boolean gewonnen = false;

    private static final int HALF_CIRCLE_RADIUS = 150;
    private static final int POINTER_LENGTH = 100;
    private static final int POINTER_WIDTH = 10;
    private static final int CENTER_X = 350;
    private static final int CENTER_Y = 850;
    private double pointerAngle;
    private boolean increasing;

    public Hauptspiel() {
        ImageIcon startImageIcon = new ImageIcon("hinstart(1).png");
        ImageIcon mainImageIcon = new ImageIcon("hinwel(1).png");
        ImageIcon hinwelImageIcon = new ImageIcon("hinwel(1).png");
        startBackgroundImage = startImageIcon.getImage();
        mainBackgroundImage = mainImageIcon.getImage();
        hinwelBackgroundImage = hinwelImageIcon.getImage();

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Timer für Hintergrundanimation initialisieren
        timer = new Timer(50, this);
        timer.start();

        // Knopf erstellen
        JButton button = new JButton("");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Überprüfen, ob der Zeiger im grünen Bereich ist
                if (pointerAngle >= -30 && pointerAngle <= 30) {
                    pendelwin();
                } else {
                    // Öffne das GameOverScreen-Programm
                    try {
                        ProcessBuilder pb = new ProcessBuilder("java", "GameOverScreen");
                        pb.start();
                        // Schließe das Hauptspiel-Fenster
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Hauptspiel.this);
                        frame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        add(button);
    }

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
        
        // Zeichne das Hinwel-Bild nur, wenn nicht gewonnen wurde
        if (!gewonnen) {
            g.drawImage(hinwelBackgroundImage, 0, yPos - (panelHeight * 2), panelWidth, panelHeight, this);
        }

        // Halbkreis und Zeiger zeichnen, nur wenn nicht gewonnen
        if (!gewonnen) {
            drawHalfCircle(g);
            drawPointer(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePointer();
        repaint();
        if (gewonnen) {
            yPos += 5;
            if (yPos >= panelHeight * 2) {
                yPos = panelHeight;
            }
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
            pointerAngle -= 3;
            if (pointerAngle <= -90)
                increasing = false;
        } else {
            pointerAngle += 3;
            if (pointerAngle >= 90)
                increasing = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 1000);
            frame.setLocationRelativeTo(null);
            frame.add(new Hauptspiel());
            frame.setVisible(true);
        });
    }
}
