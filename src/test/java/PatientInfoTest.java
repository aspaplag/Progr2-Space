import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class PatientInfoTest {

    private PatientInfo window;

    @Before
    public void setUp() {

        window = new PatientInfo("31/12/2024", "9:00", "Παθολόγος");

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
        assertEquals("Στοιχεία Ασθενή", window.getTitle());
        assertEquals(1200, window.getWidth());
        assertEquals(800, window.getHeight());

    }

    @Test
    public void testComponentsExistence() {

        JLabel questionLabel = (JLabel) findComponentByName(window, "Έχετε ξανακλείσει ραντεβού;");
        assertNotNull(questionLabel);
        assertEquals("Έχετε ξανακλείσει ραντεβού;", questionLabel.getText());

        JButton yesButton = (JButton) findComponentByName(window, "Ναι");
        assertNotNull(yesButton);
        assertEquals("Ναι", yesButton.getText());

        JButton noButton = (JButton) findComponentByName(window, "Όχι");
        assertNotNull(noButton);
        assertEquals("Όχι", noButton.getText());

    }

    @Test
    public void testYesButtonAction() {

        JButton yesButton = (JButton) findComponentByName(window, "Ναι");
        assertNotNull(yesButton);

        yesButton.doClick();
        assertFalse(window.isShowing());

    }

    @Test
    public void testNoButtonAction() {

        JButton noButton = (JButton) findComponentByName(window, "Όχι");
        assertNotNull(noButton);

        noButton.doClick();
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

