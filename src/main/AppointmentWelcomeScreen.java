import java.awt.*;
import javax.swing.*;

public class AppointmentWelcomeScreen extends JFrame {
	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 800;

	private JPanel panel;

	public AppointmentWelcomeScreen() {

		setTitle("Διαχείριση Ραντεβού σε Ιατρείο");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 50, 200), 0, getHeight(),
						new Color(0, 0, 50, 100));
				g2d.setPaint(gradient);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(panel);

		Title();
		Description();
		Button();

		setVisible(true);
	}

	public void Title() {
		JLabel title = new JLabel("DocOptimizer", JLabel.CENTER);
		title.setForeground(Color.black);
		title.setFont(new Font("Arial", Font.BOLD, 50));
		title.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		panel.add(Box.createVerticalStrut(120));
	}

	public void Description() {
		JLabel description = new JLabel("Μπορείτε τώρα να κλείσετε ραντεβού γρήγορα και εύκολα", JLabel.CENTER);
		description.setForeground(Color.black);
		description.setFont(new Font("Arial", Font.PLAIN, 20));
		description.setBorder(BorderFactory.createEmptyBorder(90, 0, 0, 0));
		description.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(description);

		panel.add(Box.createVerticalStrut(50));
	}

	public void Button() {
		JButton enterButton = new JButton("Είσοδος στην εφαρμογή");
		enterButton.setFont(new Font("Arial", Font.PLAIN, 20));
		enterButton.setBackground(new Color(216, 167, 210));
		enterButton.setForeground(Color.BLACK);
		enterButton.setPreferredSize(new Dimension(120, 50));
		enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(enterButton);

		enterButton.addActionListener(_ -> {
			dispose();
			new AppointmentInfo();
		});
	}

	public static void main(String[] args) {
		new AppointmentWelcomeScreen();
	}
}
