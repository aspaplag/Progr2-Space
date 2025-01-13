import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PatientInfo extends JFrame {
    private static final int WINDOW_WIDTH = 1200; 
    private static final int WINDOW_HEIGHT = 800;
    private JButton yesButton;
    private JButton noButton;
    private String selectedDate;
    private String selectedTime;
    private String selectedSpecialization;
    
    public PatientInfo(String date, String time, String selectedSpecialization) {
        
        this.selectedDate = date;
        this.selectedTime = time;
        this.selectedSpecialization = selectedSpecialization;
        
        setTitle("Στοιχεία Ασθενή");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Color lightBlue = new Color(230, 245, 255); 
        getContentPane().setBackground(lightBlue);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        //ρωτάμε τον χρήστη αν έχει ξανακλείσει ραντεβού, ώστε να συλλέξουμε τα στοιχεία του από τη βάση δεδομένων, εφόσον αυτό είναι δυνατό
        createQuestionLabel(gbc);
        createButtons(gbc);
        
        setVisible(true);
    }
    
    private void createQuestionLabel(GridBagConstraints gbc) {
        
        JLabel questionLabel = new JLabel("Έχετε ξανακλείσει ραντεβού;", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setForeground(Color.BLACK);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        add(questionLabel, gbc);
        
    }

    private void createButtons(GridBagConstraints gbc) {
        
        yesButton = new JButton("Ναι");
        noButton = new JButton("Όχι");
        
        Dimension buttonSize = new Dimension(120, 50);
        yesButton.setPreferredSize(buttonSize);
        noButton.setPreferredSize(buttonSize);

        yesButton.setBackground(new Color(173, 216, 230)); 
        noButton.setBackground(new Color(173, 216, 230));
        
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        yesButton.setFont(buttonFont);
        noButton.setFont(buttonFont);
        
        gbc.gridwidth = 1; 
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(yesButton, gbc);

        gbc.gridx = 1;
        add(noButton, gbc);

        yesButton.addActionListener(e -> {
            dispose();
            new OldPatientWindow(selectedDate, selectedTime, selectedSpecialization); 
        });

        noButton.addActionListener(e -> {
            dispose(); 
            new NewPatientWindow(selectedDate, selectedTime, selectedSpecialization); 
        });
   
    }
    

    public static void main(String[] args) {
        new PatientInfo("__/__/___", "9:00","Παθολόγος");
    }
}

