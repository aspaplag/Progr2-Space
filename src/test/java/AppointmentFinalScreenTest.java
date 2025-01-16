package space;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class AppointmentFinalScreenTest {

    private AppointmentFinalScreen window;

    @Before
    public void setUp() {
        window = new AppointmentFinalScreen("31/12/2024", "9:00");
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
        assertEquals("Επιβεβαίωση Ραντεβού", window.getTitle());
        assertEquals(1200, window.getWidth());
        assertEquals(800, window.getHeight());
    }

    @Test
    public void testComponentsExistence() {
        JLabel confirmationLabel = (JLabel) findComponentByName(window, "Το ραντεβού σας έχει επιβεβαιωθεί για:");
        assertNotNull(confirmationLabel);
        assertEquals("Το ραντεβού σας έχει επιβεβαιωθεί για:", confirmationLabel.getText());

        JLabel dateLabel = (JLabel) findComponentByName(window, "Ημερομηνία: 31/12/2024");
        assertNotNull(dateLabel);
        assertEquals("Ημερομηνία: 31/12/2024", dateLabel.getText());

        JLabel timeLabel = (JLabel) findComponentByName(window, "Ώρα: 9:00");
        assertNotNull(timeLabel);
        assertEquals("Ώρα: 9:00", timeLabel.getText());
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
