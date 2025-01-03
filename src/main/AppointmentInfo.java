import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import javax.swing.border.TitledBorder;

public class AppointmentInfo extends JFrame {
    private static final int WINDOW_WIDTH = 1200; 
    private static final int WINDOW_HEIGHT = 800;
    private JComboBox<String> timeComboBox;
    private JFormattedTextField dateField;

	
    public AppointmentInfo() {
        setTitle("Κλείστε Ραντεβού");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Color lightBlue = new Color(230, 245, 255); 
        getContentPane().setBackground(lightBlue);
        
        setLayout(new GridBagLayout());  
        
        selectAppointment();
        selectDateAndTime();
        addButton();

        setVisible(true);
    }
    

     private void selectAppointment() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 60, 20, 20); 
        gbc.anchor = GridBagConstraints.WEST; 

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        JLabel label = new JLabel("Επιλέξτε τύπο ραντεβού");
        label.setFont(new Font("Arial", Font.PLAIN, 22));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);
        
        String[] options = {"Παθολόγος", "Καρδιολόγος", "Γυναικολόγος", "Ορθοπαιδικός", "Δερματολόγος", 
                            "Παιδίατρος", "Νευρολόγος", "Ενδοκρινολόγος", "Ψυχίατρος", "Οφθαλμίατρος"};
        
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setPreferredSize(new Dimension(420, 40));
        panel.add(comboBox, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; 
        add(panel, gbc);
    }

    
    private void selectDateAndTime() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(150, 60, 20, 20); 
        gbc.anchor = GridBagConstraints.WEST; 

        JPanel dateTimePanel = new JPanel(new GridBagLayout());
        dateTimePanel.setBackground(Color.WHITE);
      
        TitledBorder border = BorderFactory.createTitledBorder("Επιλέξτε Ημερομηνία και Ώρα");
        border.setTitleFont(new Font("Arial", Font.PLAIN, 22)); 
        dateTimePanel.setBorder(border);

        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.insets = new Insets(10, 10, 10, 10);

        JLabel dateLabel = new JLabel("Ημερομηνία:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.WEST;
        dateTimePanel.add(dateLabel, innerGbc);

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }

        dateField = new JFormattedTextField(dateFormatter);
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateField.setColumns(10);
        innerGbc.gridx = 1;
        innerGbc.gridy = 0;
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        dateTimePanel.add(dateField, innerGbc);

        JLabel timeLabel = new JLabel("Ώρα:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        innerGbc.anchor = GridBagConstraints.WEST;
        dateTimePanel.add(timeLabel, innerGbc);

        timeComboBox = new JComboBox<>(new String[]{
            "09:00", "09:30", "10:00", "10:30", "11:00", 
            "11:30", "12:00", "12:30", "13:00", "13:30", 
            "14:00", "14:30", "15:00", "15:30", "16:00", 
            "16:30", "17:00"
        });
        
        timeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        timeComboBox.setPreferredSize(new Dimension(300, 30));
        innerGbc.gridx = 1;
        innerGbc.gridy = 1;
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        dateTimePanel.add(timeComboBox, innerGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; 
        add(dateTimePanel, gbc);
    }
    

    private void addButton() {
        JButton submitButton = new JButton("Συνέχεια");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setBackground(new Color(173, 216, 230));
        submitButton.setForeground(Color.BLACK);
        submitButton.setPreferredSize(new Dimension(120, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(100, 20, 20, 20); 
        gbc.anchor = GridBagConstraints.CENTER; 
        add(submitButton, gbc); 

        submitButton.addActionListener(e -> {

	    String selectedDate = dateField.getText();
            String selectedTime = (String) timeComboBox.getSelectedItem(); 
            
	    dispose(); 
	    new PatientInfo(selectedDate, selectedTime); 	
		
        });
    }

    
    public static void main(String[] args) {
        AppointmentInfo frame = new AppointmentInfo(); 
        frame.setVisible(true);        
    }
}
