import static org.junit.Assert.*;
import org.junit.Test;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

public class AppointmentTest {

    @Test
    public void testAppointmentCreationValidData() {
        Patient patient = new Patient("Alice", "Johnson", Date.valueOf("1985-06-15"), "123 Main St", "1234567890",
                "alice@example.com", "987654321", "No allergies", "Female");
        Doctor doctor = new Doctor("DOC001", "Dr. Jane", "Smith", "Cardiology",
                Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 120);

        Appointment appointment = new Appointment(
                "DOC001", "Cardiology", Time.valueOf("09:30:00"), Date.valueOf("2025-01-20"),
                patient, doctor, 1, 30);

        assertNotNull(appointment);
        assertEquals("DOC001", appointment.getDocCode());
        assertEquals("Cardiology", appointment.getDetails().split(",")[0].split(": ")[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAppointmentCreationInvalidData() {
        Patient patient = new Patient("Alice", "Johnson", Date.valueOf("1985-06-15"), "123 Main St", "1234567890",
                "alice@example.com", "987654321", "No allergies", "Female");
        Doctor doctor = new Doctor("DOC001", "Dr. Jane", "Smith", "Cardiology",
                Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 120);

        new Appointment(null, null, null, null, patient, doctor, 1, 30);
    }

    @Test
    public void testToString() {
        Patient patient = new Patient("Alice", "Johnson", Date.valueOf("1985-06-15"), "123 Main St", "1234567890",
                "alice@example.com", "987654321", "No allergies", "Female");
        Doctor doctor = new Doctor("DOC001", "Dr. Jane", "Smith", "Cardiology",
                Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 120);

        Appointment appointment = new Appointment(
                "DOC001", "Cardiology", Time.valueOf("09:30:00"), Date.valueOf("2025-01-20"),
                patient, doctor, 1, 30);

        String expectedString = "Appointment[specialization='Cardiology', Time=09:30:00, Date=2025-01-20, patient=Alice Johnson, doctor=Dr. Jane Smith, priority=1, duration=30]";
        assertEquals(expectedString, appointment.toString());
    }
}

