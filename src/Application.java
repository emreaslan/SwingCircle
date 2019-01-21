import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Application extends JFrame {
	public double angdeg;
	public JPanel panel;
	public Timer timer;

	public Application() {
		angdeg = 0;
		timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				angdeg += 1.0;
				panel.repaint();

				if (angdeg >= 360) {
					timer.stop();
				} else {
					timer.restart();
				}

//				timer.restart();
			}
		});
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setRenderingHints(rh);
				g2.setRenderingHints(rh2);

				// double x = this.getWidth(), y = this.getHeight(), w =
				// this.getWidth(), h = this.getHeight();
				// double x = 300, y = 300, w = 300, h = 300;
				double x, y, w, h;
				x = w = this.getWidth();
				y = h = this.getHeight();
				if (x>y)
					x = y;
				else
					y = x;	
				System.out.println(x + " " + y);

				g2.setColor(Color.BLACK);
				double a = 10;
				Shape big = new Ellipse2D.Double(0, 0, x, y);
				g2.fill(big);

				g2.setColor(Color.RED);

				int xT = (int) (x / 2);
				int xTl = xT - (int) a;
				int xTr = xT + (int) a;
				int yT = (int) 0;
				int yTl, yTr;
				yTl = yTr = (int) (3 * y / 4);
				Polygon indicatorTriangle = new Polygon(new int[] { xTl, xT, xTr }, new int[] { yTl, yT, yTr }, 3);
				// g2.fillPolygon(indicatorTriangle);
				AffineTransform oldTransform = g2.getTransform();
				AffineTransform at = new AffineTransform();
				at.rotate(Math.toRadians(angdeg), w / 2, h / 2);
				double offset = (w - h) / 2;
				at.translate(offset, offset);
				g2.setTransform(at);
				g2.fillPolygon(indicatorTriangle);

				g2.setTransform(oldTransform);
				g2.setColor(Color.GRAY);
				Shape small = new Ellipse2D.Double(x / 2 - x / 16, y / 2 - y / 16, x / 8, h / 8);
				g2.fill(small);
			}
		};

		setSize(new Dimension(356, 378));
		setTitle("Test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(panel);
		timer.start();
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Application app = new Application();
				app.setVisible(true);
			}
		});
	}

}
