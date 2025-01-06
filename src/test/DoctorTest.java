package space;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;

public class DoctorTest {

    private Doctor doctor;

    @Before
    public void setUp() {
        doctor = new Doctor("John", "Cardiology",
        Arrays.asList(LocalTime.of(9, 0), LocalTime.of(10, 0)), 180);
    };
    

    @Test
    public void testIsAvailable() {
        assertTrue(doctor.isAvailable(LocalTime.of(9, 0)));
        assertFalse(doctor.isAvailable(LocalTime.of(12, 0)));
    }

    @Test
    public void testSetAvailableMinutes() {
        doctor.setAvailableMinutes(150);
        assertEquals(150, doctor.getAvailableMinutes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeAvailableMinutes() {
        doctor.setAvailableMinutes(-10);
    }
}

