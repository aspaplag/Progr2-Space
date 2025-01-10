import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OptimizationAlgorithm {
    private SqlConnect sConnect;

    public OptimizationAlgorithm(SqlConnect sConnect) {
        this.sConnect = sConnect;
    }

    /**
     * Ελέγχει αν η ώρα είναι διαθέσιμη για την ειδικότητα και την ημερομηνία.
     * 
     * @param specialization Η ειδικότητα
     * @param date           Η ημερομηνία
     * @param time           Η ώρα
     * @return true αν η ώρα είναι διαθέσιμη, αλλιώς false
     */
    public boolean isTimeSlotAvailable(String date, String time) throws Exception {
        // Ανάκτηση ραντεβού για την ειδικότητα και την ημερομηνία
        List<Appointment> appointments = sConnect.getAppointments();

        // Έλεγχος αν η ώρα είναι κατειλημμένη
        for (Appointment appointment : appointments) {
            if (appointment.getApptTime().toLocalTime().toString().equals(time)) {
                return false; // Η ώρα είναι κατειλημμένη
            }
        }
        return true; // Η ώρα είναι διαθέσιμη
    }

    /**
     * Προτείνει εναλλακτικές ώρες για την επιλεγμένη ειδικότητα και ημερομηνία.
     * 
     * @param specialization Η ειδικότητα
     * @param date           Η ημερομηνία
     * @param doctors        Λίστα με τους διαθέσιμους γιατρούς
     * @return Λίστα με τις προτεινόμενες ώρες
     */
    public List<String> suggestAlternativeTimes(String specialization, String date, List<Doctor> doctors) throws Exception {
        List<String> suggestedTimes = new ArrayList<>();

        // Ελέγξτε όλους τους γιατρούς της ειδικότητας
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                for (LocalTime time : doctor.getAvailableTimeSlots()) {
                    // Αν η ώρα είναι διαθέσιμη, προσθέστε την στις προτάσεις
                    if (isTimeSlotAvailable(date, time.toString())) {
                        suggestedTimes.add(time.toString());
                    }
                }
            }
        }

        // Επιστροφή των προτεινόμενων ωρών
        return suggestedTimes;
    }
}
