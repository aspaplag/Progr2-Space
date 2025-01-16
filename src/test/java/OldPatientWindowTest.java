package space;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class OldPatientWindowTest {

    private OldPatientWindow window;

    @Before
    public void setUp() {
      
        window = new OldPatientWindow("31/12/2024", "9:00");
      
    }

    @Test
    public void testAmkaLabel() {
        
        JPanel contentPane = (JPanel) window.getContentPane();
        JLabel amkaLabel = null;
        for (Component component : contentPane.getComponents()) {
            if (component instanceof JLabel) {
                amkaLabel = (JLabel) component;
                break;
            }
        }

        assertNotNull(amkaLabel);
        assertEquals("Παρακαλώ εισάγετε το ΑΜΚΑ σας:", amkaLabel.getText());
      
    }

    @Test
    public void testAmkaField() {

        JPanel contentPane = (JPanel) window.getContentPane();
        JTextField amkaField = null;
        for (Component component : contentPane.getComponents()) {
            if (component instanceof JTextField) {
                amkaField = (JTextField) component;
                break;
            }
        }

        assertNotNull(amkaField);
        assertEquals(20, amkaField.getColumns());
      
    }

    @Test
    public void testSubmitButton() {

        JPanel contentPane = (JPanel) window.getContentPane();
        JButton submitButton = null;
        for (Component component : contentPane.getComponents()) {
            if (component instanceof JButton) {
                submitButton = (JButton) component;
                break;
            }
        }

        assertNotNull(submitButton);
        assertEquals("Υποβολή", submitButton.getText());
      
    }
  
}
