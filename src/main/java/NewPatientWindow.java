import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class NewPatientWindow extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int LABEL_FONT_SIZE = 22;
    private static final int FIELD_WIDTH = 500;
    private static final int FIELD_HEIGHT = 30;
    private JTextField nameField, surnameField, addressField, phoneField, emailField, amkaField, genderField;
    private JFormattedTextField birthDateField;
    private JTextArea medicalHistoryArea;
    private String selectedDate;
    private String selectedTime;
    private String selectedSpecialization;

    public NewPatientWindow(String date, String time, String selectedSpecialization) {
        this.selectedDate = date;
        this.selectedTime = time;
        this.selectedSpecialization = selectedSpecialization;
        setTitle("Στοιχεία Ασθενή");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color lightBlue = new Color(230, 245, 255);
        getContentPane().setBackground(lightBlue);
        JPanel mainPanel = createMainPanel(lightBlue);
        add(mainPanel, BorderLayout.CENTER);
        JPanel bottomPanel = createBottomPanel(lightBlue);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createMainPanel(Color lightBlue) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(lightBlue);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        JPanel leftPanel = createLeftPanel(lightBlue);
        JPanel rightPanel = createRightPanel(lightBlue);
        mainPanel.add(leftPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        mainPanel.add(rightPanel);
        return mainPanel;
    }

    private JPanel createLeftPanel(Color lightBlue) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(lightBlue);
        leftPanel.add(createLabel("Παρακαλώ εισάγετε τα στοιχεία σας: ", 26));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        nameField = addField(leftPanel, "Όνομα:");
        surnameField = addField(leftPanel, "Επίθετο:");
        birthDateField = createDateField(leftPanel, "Ημερομηνία γέννησης (dd/mm/yyyy):");
        addressField = addField(leftPanel, "Διεύθυνση:");
        phoneField = addField(leftPanel, "Κινητό:");
        emailField = addField(leftPanel, "Email:");
        return leftPanel;
    }

    private JPanel createRightPanel(Color lightBlue) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(lightBlue);
        rightPanel.add(createLabel("Ιατρικό Ιστορικό:", LABEL_FONT_SIZE));
        medicalHistoryArea = createMedicalHistoryArea();
        rightPanel.add(medicalHistoryArea);
        amkaField = addField(rightPanel, "ΑΜΚΑ:");
        genderField = addField(rightPanel, "Φύλο:");
        return rightPanel;
    }

    private JPanel createBottomPanel(Color lightBlue) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(lightBlue);
        JButton submitButton = new JButton("Υποβολή");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setBackground(new Color(173, 216, 230));
        bottomPanel.add(submitButton);
        submitButton.addActionListener(_ -> handleSubmit());
        return bottomPanel;
    }

    private void handleSubmit() {
        try {
            // Συλλογή δεδομένων
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String birthDate = birthDateField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String amka = amkaField.getText().trim();
            String gender = genderField.getText().trim();
            String medicalHistory = medicalHistoryArea.getText().trim();
            // Έλεγχος για κενά πεδία
            if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || address.isEmpty() ||
                    phone.isEmpty() || email.isEmpty() || amka.isEmpty() || gender.isEmpty()) {
                throw new IllegalArgumentException("Όλα τα πεδία πρέπει να συμπληρωθούν.");
            }
            // Μετατροπή δεδομένων

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

            Date dob = dateFormatter.parse(birthDate);
            java.sql.Date sqlDob = new java.sql.Date(dob.getTime());

            Date sTime = timeFormatter.parse(selectedTime);
            java.sql.Time sqlTime = new java.sql.Time(sTime.getTime());

            Date sDate = dateFormatter.parse(selectedDate);
            java.sql.Date sqlDate = new java.sql.Date(sDate.getTime());

            // Δημιουργία αντικειμένου Patient
            // Patient patient = new Patient(name, surname, sqlDob, address, phone, email,
            // amka, medicalHistory, gender);
            // Εισαγωγή στη βάση δεδομένων
            SqlConnect sConnect = new SqlConnect();
            sConnect.insertPatient(amka, name, surname, sqlDob, phone, address, email, medicalHistory, gender);
            JOptionPane.showMessageDialog(this, "Ο ασθενής προστέθηκε επιτυχώς στη βάση δεδομένων!", "Επιτυχία",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();

            List<Doctor> doctors = sConnect.getDoctorsBySpecialization(selectedSpecialization);
            String docCode = doctors.get(0).getDocCode();

            try {
                sConnect.insertAppointment(docCode, selectedSpecialization, sqlTime, sqlDate, amka);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αποθήκευση του ραντεβού: " + ex.getMessage(),
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE);
            }

            new AppointmentFinalScreen(selectedDate, selectedTime);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Το ΑΜΚΑ πρέπει να είναι αριθμός.", "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αποθήκευση του ασθενή: " + ex.getMessage(), "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private JTextField addField(JPanel panel, String labelText) {
        panel.add(createLabel(labelText, LABEL_FONT_SIZE));
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        textField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        panel.add(textField);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        return textField;
    }

    private JFormattedTextField createDateField(JPanel panel, String labelText) {
        panel.add(createLabel(labelText, LABEL_FONT_SIZE));
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFormattedTextField dateField = new JFormattedTextField(dateFormatter);
        dateField.setFont(new Font("Arial", Font.PLAIN, 14));
        dateField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        dateField.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        panel.add(dateField);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        return dateField;
    }

    private JTextArea createMedicalHistoryArea() {
        JTextArea medicalHistory = new JTextArea();
        medicalHistory.setFont(new Font("Arial", Font.PLAIN, 14));
        medicalHistory.setLineWrap(true);
        medicalHistory.setWrapStyleWord(true);
        medicalHistory.setPreferredSize(new Dimension(FIELD_WIDTH, 100));
        return medicalHistory;
    }

    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        return label;
    }

}
