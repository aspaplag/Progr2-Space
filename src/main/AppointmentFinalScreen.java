import java.awt.*;
import javax.swing.*;

public class AppointmentFinalScreen extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int LABEL_FONT_SIZE = 22;
    private static final int TITLE_FONT_SIZE = 28;
    private static final Color BACKGROUND_COLOR = new Color(230, 245, 255);
    private GridBagConstraints gbc;

    public AppointmentFinalScreen(String selectedDate, String selectedTime) {
        setTitle("Επιβεβαίωση Ραντεβού");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(BACKGROUND_COLOR);

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        showMessage(selectedDate, selectedTime);

        setVisible(true);
    }

    public void showMessage(String date, String time) {

        addConfirmationLabel();
        addDateLabel(date);
        addTimeLabel(time);

    }

    private void addConfirmationLabel() {

        JLabel confirmationLabel = new JLabel("Το ραντεβού σας έχει επιβεβαιωθεί για:");
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        confirmationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.insets = new Insets(30, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        add(confirmationLabel, gbc);

    }

    private void addDateLabel(String date) {

        JLabel dateLabel = new JLabel("Ημερομηνία: " + date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 5, 20);
        add(dateLabel, gbc);

    }

    private void addTimeLabel(String time) {

        JLabel timeLabel = new JLabel("Ώρα: " + time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        add(timeLabel, gbc);

    }

}
