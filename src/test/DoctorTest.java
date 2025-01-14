import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalTime;
import java.util.Arrays;

public class DoctorTest {

    @Test
    public void testDoctorCreation() {
        Doctor doctor = new Doctor("DOC001", "Jane", "Smith", "Cardiology",
                Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 120);

        assertNotNull(doctor);
        assertEquals("Jane Smith", doctor.getFullname());
        assertEquals("Cardiology", doctor.getSpecialization());
        assertEquals(120, doctor.getAvailableMinutes());
    }

    @Test
    public void testIsAvailable() {
        Doctor doctor = new Doctor("DOC001", "Jane", "Smith", "Cardiology",
                Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 120);

        assertTrue(doctor.isAvailable(LocalTime.of(9, 0)));
        assertFalse(doctor.isAvailable(LocalTime.of(11, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoctorCreationWithNullValues() {
        new Doctor("DOC001", null, null, null, null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAvailableMinutesNegative() {
        Doctor doctor = new Doctor("DOC001", "Jane", "Smith", "Cardiology",
                Arrays.asList(LocalTime.of(9, 0)), 120);
        doctor.setAvailableMinutes(-10);
    }
}
