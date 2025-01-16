package space;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class AppointmentWelcomeScreenTest {
  private AppointmentWelcomeScreen window;

    @Before
    public void setUp() {
        window = new AppointmentWelcomeScreen();
    }
  
     @After
    public void tearDown() {
        if (window != null) {
            window.dispose();
        }
    }

    @Test
    public void testWindowProperties() {
        assertNotNull(window);
        assertEquals("Διαχείριση Ραντεβού σε Ιατρείο", window.getTitle());
        assertEquals(1200, window.getWidth());
        assertEquals(800, window.getHeight());
    }

    @Test
    public void testComponentsExistence() {
        JLabel titleLabel = (JLabel) findComponentByName(window, "DocOptimizer");
        assertNotNull(titleLabel);
        assertEquals("DocOptimizer", titleLabel.getText());

        JLabel descriptionLabel = (JLabel) findComponentByName(window, "Μπορείτε τώρα να κλείσετε ραντεβού γρήγορα και εύκολα");
        assertNotNull(descriptionLabel);
        assertEquals("Μπορείτε τώρα να κλείσετε ραντεβού γρήγορα και εύκολα", descriptionLabel.getText());

        JButton enterButton = (JButton) findComponentByName(window, "Είσοδος στην εφαρμογή");
        assertNotNull(enterButton);
        assertEquals("Είσοδος στην εφαρμογή", enterButton.getText());
    }

    @Test
    public void testEnterButtonAction() {
        JButton enterButton = (JButton) findComponentByName(window, "Είσοδος στην εφαρμογή");
        assertNotNull(enterButton);

        enterButton.doClick();
        assertFalse(window.isShowing());
    }

    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel && ((JLabel) component).getText().equals(name)) {
                return component;
            }
            if (component instanceof JButton && ((JButton) component).getText().equals(name)) {
                return component;
            }
            if (component instanceof Container) {
                Component found = findComponentByName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
