package space;

import javax.swing.*;
import java.awt.*;

class OldPatientWindow extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    // private static final int FIELD_WIDTH = 20;
    // private static final int BUTTON_WIDTH = 120;
    // private static final int BUTTON_HEIGHT = 50;
    private String selectedDate;
    private String selectedTime;

    private JTextField amkaField; // Δηλώνουμε την amkaField ως πεδίο της κλάσης

    public OldPatientWindow(String date, String time) {

        this.selectedDate = date;
        this.selectedTime = time;

        setTitle("AMKA");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color lightBlue = new Color(230, 245, 255);
        getContentPane().setBackground(lightBlue);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        createAmkaLabel(gbc);

        createAmkaField(gbc);

        createSubmitButton(gbc);

        setVisible(true);

    }

    private void createAmkaLabel(GridBagConstraints gbc) {

        JLabel amkaLabel = new JLabel("Παρακαλώ εισάγετε το ΑΜΚΑ σας:");
        amkaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        amkaLabel.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(amkaLabel, gbc);

    }

    private void createAmkaField(GridBagConstraints gbc) {

        amkaField = new JTextField(20);
        amkaField.setFont(new Font("Arial", Font.PLAIN, 20));

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(amkaField, gbc);

    }

    private void createSubmitButton(GridBagConstraints gbc) {

        JButton submitButton = new JButton("Υποβολή");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(173, 216, 230));

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String amka = amkaField.getText().trim();

            if (amka.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε ένα έγκυρο ΑΜΚΑ.", "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Αναζήτηση στη βάση δεδομένων
            SqlConnect sConnect = new SqlConnect();
            try {
                Patient patient = sConnect.selectAMKA(amka);

                if (patient != null) {
                    JOptionPane.showMessageDialog(this, "Ο ασθενής βρέθηκε:\n" + patient, "Ασθενής",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new AppointmentFinalScreen(selectedDate, selectedTime);
                } else {
                    JOptionPane.showMessageDialog(this, "Ο ασθενής με ΑΜΚΑ " + amka + " δεν βρέθηκε.", "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {

            }
        });

    }

    public static void main(String[] args) {

        new OldPatientWindow("__/__/___", "9:00");

    }

}
