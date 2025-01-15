import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class AppointmentInfoTest {

    private AppointmentInfo window;

    @Before
    public void setUp() {
        window = new AppointmentInfo();
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
        assertEquals("Κλείστε Ραντεβού", window.getTitle());
        assertEquals(1200, window.getWidth());
        assertEquals(800, window.getHeight());
    }

    @Test
    public void testComponentsExistence() {
        JLabel typeLabel = (JLabel) findComponentByName(window, "Επιλέξτε τύπο ραντεβού");
        assertNotNull(typeLabel);
        assertEquals("Επιλέξτε τύπο ραντεβού", typeLabel.getText());

        @SuppressWarnings("unchecked")
        JComboBox<String> comboBox = (JComboBox<String>) findComponentByType(window, JComboBox.class);
        assertNotNull(comboBox);

        JLabel dateLabel = (JLabel) findComponentByName(window, "Ημερομηνία:");
        assertNotNull(dateLabel);
        assertEquals("Ημερομηνία:", dateLabel.getText());

        JLabel timeLabel = (JLabel) findComponentByName(window, "Ώρα:");
        assertNotNull(timeLabel);
        assertEquals("Ώρα:", timeLabel.getText());

        JButton submitButton = (JButton) findComponentByName(window, "Συνέχεια");
        assertNotNull(submitButton);
        assertEquals("Συνέχεια", submitButton.getText());
    }

    @Test
    public void testSubmitButtonAction() {
        JButton submitButton = (JButton) findComponentByName(window, "Συνέχεια");
        assertNotNull(submitButton);

        submitButton.doClick();
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

    private Component findComponentByType(Container container, Class<?> type) {
        for (Component component : container.getComponents()) {
            if (type.isInstance(component)) {
                return component;
            }
            if (component instanceof Container) {
                Component found = findComponentByType((Container) component, type);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
