package space;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OptimizationAlgorithmTest {

    private List<Appointment> appointments;
    private List<Doctor> doctors;
    private OptimizationAlgorithm optimizer;

    @Before
    public void setUp() {
        Doctor doctor1 = new Doctor("John", "Doe", "Cardiologist",
                Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 180);

        Doctor doctor2 = new Doctor("Jane", "Smith", "Dermatologist",
                Arrays.asList(LocalTime.of(9, 30), LocalTime.of(10, 30)), 120);

        doctors = Arrays.asList(doctor1, doctor2);

        Patient patient1 = new Patient("Alice", "Brown", "1990-05-15", "123 Main St", "555-1234",
                "alice@example.com", 123456789, "Female");

        Appointment appointment1 = new Appointment("Cardiologist", LocalDateTime.of(2024, 12, 9, 9, 0),
                patient1, doctor1, 1, 30);

        appointments = new ArrayList<>();
        appointments.add(appointment1);

        optimizer = new OptimizationAlgorithm(appointments, doctors);
    }

    @Test
    public void testOptimizeSchedule() {
        optimizer.optimizeSchedule();

        // Ελέγχουμε αν το ραντεβού ανατέθηκε στον σωστό γιατρό
        assertEquals("Cardiologist", appointments.get(0).getDoctor().getSpecialization());
    }
}
