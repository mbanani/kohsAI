import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A basic Java animation that animates a Kohs Block solution sequence
 *
 * @author Mohamed El Banani
 * @date 6 August 2015
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
public class Animator extends JPanel implements ActionListener {

	Timer tm;
	SolutionFrame solution;
	SolutionFrame currentSol;
	int width;
	int length;
	String title = "Kohs Solution Sequence";
	int blockSize;
	JLabel timeLabel;

	public Animator(int timeInterval, SolutionFrame sol, int size) {
		tm = new Timer(timeInterval, this);
		solution = sol;
		currentSol = sol;
		blockSize = size;
		width = (sol.width) * blockSize + 60;
		length = (sol.length + 1) * blockSize + 100;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillRect(20, 20, solution.width * blockSize, solution.length
				* blockSize);
		int currentFace, x, y;

		for (int i = 0; i < currentSol.width; i++) {
			for (int j = currentSol.length - 1; j >= 0; j--) {
				currentFace = currentSol.currentTable[i][j];
				x = 20 + i * blockSize;
				y = 20 + (currentSol.length - 1 - j) * blockSize;

				if (currentFace == 1) {
					g.setColor(Color.RED);
					g.fillRect(x, y, blockSize, blockSize);
				} else if (currentFace == 2) {
					drawTR(g, x, y);
				} else if (currentFace == 3) {
					drawTL(g, x, y);
				} else if (currentFace == 4) {
					drawBL(g, x, y);
				} else if (currentFace == 5) {
					drawBR(g, x, y);
				} else if (currentFace == 6) {
					g.setColor(Color.WHITE);
					g.fillRect(x, y, blockSize, blockSize);
				}
			}
		}
		String time = "<html><h1>Time: ";
		Integer timeInt = new Integer(currentSol.time);
		time = time + timeInt.toString();
		time = time + "</h1></html>";
		timeLabel = new JLabel(time);
		// Add time and change set a hand block!
		// this.add(timeLabel);
		timeLabel.setText(time);
		timeLabel.setVisible(true);
		timeLabel.setSize(300, 80);
		timeLabel.setLocation(width / 2 - 150, length - 100);
		tm.start();
	}

	private void drawBR(Graphics g, int x, int y) {
		int[] xs = { x, x + blockSize, x };
		int[] ys = { y, y, y + blockSize };
		g.setColor(Color.RED);
		g.fillRect(x, y, blockSize, blockSize);
		g.setColor(Color.WHITE);
		g.fillPolygon(xs, ys, 3);
	}

	private void drawBL(Graphics g, int x, int y) {
		int[] xs = { x, x + blockSize, x + blockSize };
		int[] ys = { y, y, y + blockSize };
		g.setColor(Color.RED);
		g.fillRect(x, y, blockSize, blockSize);
		g.setColor(Color.WHITE);
		g.fillPolygon(xs, ys, 3);
	}

	private void drawTL(Graphics g, int x, int y) {
		int[] xs = { x, x + blockSize, x };
		int[] ys = { y, y, y + blockSize };
		g.setColor(Color.WHITE);
		g.fillRect(x, y, blockSize, blockSize);
		g.setColor(Color.RED);
		g.fillPolygon(xs, ys, 3);
	}

	private void drawTR(Graphics g, int x, int y) {
		int[] xs = { x, x + blockSize, x + blockSize };
		int[] ys = { y, y, y + blockSize };
		g.setColor(Color.WHITE);
		g.fillRect(x, y, blockSize, blockSize);
		g.setColor(Color.RED);
		g.fillPolygon(xs, ys, 3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentSol.next != null) {
			this.remove(timeLabel);
			currentSol = currentSol.next;
			repaint();
			this.add(timeLabel);
		}
	}

	public void animate() {
		JFrame jf = new JFrame();
		jf.setTitle(title);
		jf.setSize(width, length);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(this);
	}

}
