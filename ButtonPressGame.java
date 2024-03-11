import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPressGame extends JFrame {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int HALF_CIRCLE_RADIUS = 150;
    private static final int POINTER_LENGTH = 100;
    private static final int POINTER_WIDTH = 10;
    private static final int CENTER_X = SCREEN_WIDTH / 2;
    private static final int CENTER_Y = SCREEN_HEIGHT / 2;

    private JPanel gamePanel;
    private JButton pressButton;
    private Timer timer;
    private boolean animationInProgress;
    private double pointerAngle;
    private boolean increasing;

    public ButtonPressGame() {
        setTitle("Drücke im richtigen Moment!");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHalfCircle(g);
                if (animationInProgress) {
                    drawPointer(g);
                }
            }
        };
        gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(gamePanel, BorderLayout.CENTER);

        pressButton = new JButton("Press Me!");
        pressButton.setPreferredSize(new Dimension(150, 50));
        pressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animationInProgress) {
                    if (pointerAngle >= -210 && pointerAngle <= -150) {
                        JOptionPane.showMessageDialog(ButtonPressGame.this, "Gewonnen!");
                    } else {
                        JOptionPane.showMessageDialog(ButtonPressGame.this, "Verloren!");
                    }
                    resetGame();
                }
            }
        });
        add(pressButton, BorderLayout.SOUTH);

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animationInProgress) {
                    movePointer();
                    gamePanel.repaint();
                }
            }
        });
        timer.setRepeats(true);
    }

    private void drawHalfCircle(Graphics g) {
        int x = CENTER_X - HALF_CIRCLE_RADIUS;
        int y = CENTER_Y - HALF_CIRCLE_RADIUS;

        // Zeichne den linken blauen Teil des Halbkreises (Grad 0 bis 60)
        g.setColor(Color.BLUE);
        g.fillArc(x, y, HALF_CIRCLE_RADIUS * 2, HALF_CIRCLE_RADIUS * 2, 0, 60); // Bereich von 0 Grad mit 60 Grad Weite

        // Zeichne den grünen Teil des Halbkreises (Grad 60 bis 120)
        g.setColor(Color.GREEN);
        g.fillArc(x, y, HALF_CIRCLE_RADIUS * 2, HALF_CIRCLE_RADIUS * 2, 60, 60); // Bereich von 60 Grad mit 60 Grad Weite

        // Zeichne den rechten blauen Teil des Halbkreises (Grad 120 bis 180)
        g.setColor(Color.BLUE);
        g.fillArc(x, y, HALF_CIRCLE_RADIUS * 2, HALF_CIRCLE_RADIUS * 2, 120, 60); // Bereich von 120 Grad mit 60 Grad Weite
    }

    private void drawPointer(Graphics g) {
        int x2 = (int) (CENTER_X - POINTER_LENGTH * Math.sin(Math.toRadians(pointerAngle)));
        int y2 = (int) (CENTER_Y + POINTER_LENGTH * Math.cos(Math.toRadians(pointerAngle)));
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
            if (pointerAngle <= -270)
                increasing = false;
        } else {
            pointerAngle += 3;
            if (pointerAngle >= -90)
                increasing = true;
        }
    }

    private void resetGame() {
        animationInProgress = false;
        pointerAngle = -180;
        increasing = true;
        gamePanel.repaint();
    }

    public void startGame() {
        setVisible(true);
        animationInProgress = true;
        pointerAngle = -180;
        increasing = true;
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ButtonPressGame game = new ButtonPressGame();
                game.startGame();
            }
        });
    }
}
