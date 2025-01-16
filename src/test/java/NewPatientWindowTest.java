package space;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class NewPatientWindowTest {

    private NewPatientWindow window;

    @Before
    public void setUp() {
      
        window = new NewPatientWindow("31/12/2024", "09:00");
      
    }

    @Test
    public void testWindowTitle() {
      
        assertEquals("Στοιχεία Ασθενή", window.getTitle());
      
    }

    @Test
    public void testWindowSize() {
      
        assertEquals(1200, window.getWidth());
        assertEquals(800, window.getHeight());
      
    }

    @Test
    public void testBackgroundColor() {
      
        Color expectedColor = new Color(230, 245, 255);
        assertEquals(expectedColor, window.getContentPane().getBackground());
      
    }

    @Test
    public void testSubmitButton() {
      
        JPanel bottomPanel = (JPanel) window.getContentPane().getComponent(1);
        JButton submitButton = (JButton) bottomPanel.getComponent(0);

        assertNotNull(submitButton);
        assertEquals("Υποβολή", submitButton.getText());
      
    }

    @Test
    public void testTextFieldAndLabels() {
      
        JPanel mainPanel = (JPanel) window.getContentPane().getComponent(0);
        JPanel leftPanel = (JPanel) ((JPanel) mainPanel.getComponent(0)).getComponent(0);

        JLabel nameLabel = (JLabel) ((JPanel) leftPanel.getComponent(1)).getComponent(0);
        JTextField nameField = (JTextField) ((JPanel) leftPanel.getComponent(1)).getComponent(1);

        assertEquals("Όνομα: ", nameLabel.getText());
        assertNotNull(nameField);
      
    }

    @Test
    public void testDateField() {
      
        JPanel mainPanel = (JPanel) window.getContentPane().getComponent(0);
        JPanel leftPanel = (JPanel) ((JPanel) mainPanel.getComponent(0)).getComponent(0);

        JPanel datePanel = (JPanel) leftPanel.getComponent(5);
        JLabel dateLabel = (JLabel) datePanel.getComponent(0);
        JFormattedTextField dateField = (JFormattedTextField) datePanel.getComponent(1);

        assertEquals("Ημερομηνία γέννησης (dd/mm/yyyy): ", dateLabel.getText());
        assertNotNull(dateField);
        assertEquals("__/__/____", dateField.getText());
      
    }
  
}
