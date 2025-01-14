import static org.junit.Assert.*;
import org.junit.Test;
import java.sql.Date;

public class PatientTest {

    @Test
    public void testPatientCreation() {
        Patient patient = new Patient("John", "Doe", Date.valueOf("1990-01-01"),
                "123 Main St", "1234567890", "john@example.com", "12345678901",
                "No allergies", "Male");

        assertNotNull(patient);
        assertEquals("John", patient.getName());
        assertEquals("Doe", patient.getSurname());
        assertEquals("123 Main St", patient.getAddress());
        assertEquals("1234567890", patient.getPhoneNumber());
    }

    @Test
    public void testGetters() {
        Patient patient = new Patient("Jane", "Doe", Date.valueOf("1985-06-15"),
                "456 Elm St", "0987654321", "jane@example.com", "98765432109",
                "Diabetic", "Female");

        assertEquals("Jane", patient.getName());
        assertEquals("Doe", patient.getSurname());
        assertEquals(Date.valueOf("1985-06-15"), patient.getDateOfBirth());
        assertEquals("Diabetic", patient.medicalRecord());
        assertEquals("Female", patient.getGender());
    }

    @Test
    public void testToString() {
        Patient patient = new Patient("Alice", "Smith", Date.valueOf("1992-03-21"),
                "789 Oak St", "1231231234", "alice@example.com", "12312312312",
                "Asthmatic", "Female");

        String expected = "Patient{name='Alice', surname='Smith', dateOfBirth='1992-03-21', address='789 Oak St', " +
                "phoneNumber='1231231234', email='alice@example.com', amka='12312312312', gender='Female'}";

        assertEquals(expected, patient.toString());
    }
}
