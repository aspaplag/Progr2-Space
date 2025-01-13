import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SqlConnect {

   public void insertPatient(String amka, String name, String surname, Date dateOfBirth, String phoneNumber,
            String address,
            String email, String medicalRecord, String gender) throws Exception {
        DB db = new DB();
        String sql = "INSERT INTO Patient (AMKA, NameP, Surname, dateOfBirth, phoneNumber, addressP, email, medicalRecord, gender) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = db.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, amka);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setDate(4, dateOfBirth);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, address);
            pstmt.setString(7, email);
            pstmt.setString(8, medicalRecord);
            pstmt.setString(9, gender);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient successfully inserted into the database.");
            } else {
                System.out.println("No rows were inserted. Please check your data.");
            }

        } catch (SQLException ex) {
            System.err.println("An error occurred while inserting the patient: " + ex.getMessage());
            throw new Exception("Database error: " + ex.getMessage(), ex);
        }
    }
    public void insertAppointment(String docCode, String specialization, Time apptTime, Date apptDate, 
        String patientAMKA) throws Exception {

        DB db = new DB();
        String sql = "INSERT INTO Appointment (docCode, specialization, apptTime, apptDate, patientAMKA) "
            +
            "VALUES (?, ?, ?, ?, ?)";

        try(Connection con = db.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, docCode);
            pstmt.setString(2, specialization);
            pstmt.setTime(3, (Time) apptTime);
            pstmt.setDate(4, (Date) apptDate);
            pstmt.setString(5, patientAMKA);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment successfully inserted into the database.");
            } else {
                System.out.println("No rows were inserted. Please check your data.");
            }
        } catch (SQLException ex) {
        }
    }

    public void selectTD(String tableName) throws Exception {

        DB db = new DB();
        String sql = "SELECT * FROM" + tableName;

        try (Connection con = db.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.executeQuery();
        } catch (SQLException ex) {
        }
    }

    public Patient selectAMKA(String amkaString) throws Exception {
        Patient patient = null; // Αρχικοποίηση του αντικειμένου Patient
        DB db = new DB(); // Σύνδεση με τη βάση δεδομένων
        String sql = "SELECT * FROM Patient WHERE AMKA = ?";

        try (Connection con = db.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) { // Χρήση PreparedStatement για ασφαλή ερωτήματα

            stmt.setString(1, amkaString); // Ορισμός παραμέτρου ΑΜΚΑ
            try (ResultSet rs = stmt.executeQuery()) { // Εκτέλεση του ερωτήματος και λήψη αποτελεσμάτων
                if (rs.next()) {
                    // Δημιουργία αντικειμένου Patient από τα αποτελέσματα
                    patient = new Patient(
                            rs.getString("nameP"),
                            rs.getString("surname"),
                            rs.getDate("dateOfBirth"),
                            rs.getString("addressP"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"),
                            rs.getString("AMKA"),
                            rs.getString("medicalRecord"),
                            rs.getString("gender"));
                }
            }
        } catch (SQLException ex) {
            // Αντιμετώπιση εξαίρεσης
            ex.printStackTrace();
            throw new Exception("Σφάλμα κατά την αναζήτηση ασθενούς με ΑΜΚΑ.", ex);
        }
        return patient; // Επιστροφή του αντικειμένου Patient ή null αν δεν βρέθηκε
    }

    public List<Appointment> getAppointments() throws Exception {
        DB db = new DB();
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT " +
                     "Appointment.*, " +
                     "Doctor.docCode, Doctor.nameD AS nameD, Doctor.surname AS surnameD, Doctor.specialization, Doctor.availableTime, Doctor.availableMinutes, " +
                     "Patient.AMKA, Patient.NameP AS nameP, Patient.Surname AS surnameP, Patient.dateOfBirth, Patient.phoneNumber, Patient.addressP AS addressP, Patient.email, Patient.medicalRecord, Patient.gender " +
                     "FROM Appointment " +
                     "JOIN Doctor ON Appointment.docCode = Doctor.docCode " +
                     "JOIN Patient ON Appointment.patientAMKA = Patient.AMKA";
        
        try (Connection con = db.getConnection();
             Statement pstmt = con.createStatement();
             ResultSet resultSet = pstmt.executeQuery(sql)) {
    
            while (resultSet.next()) {
                String docCode = resultSet.getString("docCode");
                String specialization = resultSet.getString("specialization");
                Time apptTime = resultSet.getTime("apptTime");
                Date apptDate = resultSet.getDate("apptDate");
    
                Patient patient = createPatientFromResultSet(resultSet);
                Doctor doctor = createDoctorFromResultSet(resultSet);
    
                int priority = resultSet.getInt("priority");
                int duration = resultSet.getInt("duration");
    
                Appointment appointment = new Appointment(docCode, specialization, apptTime, apptDate, patient, doctor, priority, duration);
                appointments.add(appointment);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public Doctor createDoctorFromResultSet(ResultSet resultSet) throws SQLException {
        String docCode = resultSet.getString("docCode");
        String name = resultSet.getString("nameD");
        String surname = resultSet.getString("surnameD");
        String specialization = resultSet.getString("specialization");
    
        List<LocalTime> availableTimeSlots = new ArrayList<>();
        String availableTime = resultSet.getString("availableTime");
        if (availableTime != null) {
            String[] timeStrings = availableTime.split(",");
            for (String timeString : timeStrings) {
                availableTimeSlots.add(LocalTime.parse(timeString.trim()));
            }
        }
    
        int availableMinutes = resultSet.getInt("availableMinutes");
    
        return new Doctor(docCode, name, surname, specialization, availableTimeSlots, availableMinutes);
    }

    public Patient createPatientFromResultSet(ResultSet resultSet) throws SQLException {
        String amka = resultSet.getString("AMKA");
        String name = resultSet.getString("nameP");
        String surname = resultSet.getString("surnameP");
        Date dateOfBirth = resultSet.getDate("dateOfBirth");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("addressP");
        String email = resultSet.getString("email");
        String medicalRecord = resultSet.getString("medicalRecord");
        String gender = resultSet.getString("gender");
    
        return new Patient(name, surname, dateOfBirth, address, phoneNumber, email, amka, medicalRecord, gender);
    }

    public List<Doctor> getDoctorsBySpecialization(String selectedSpecialty) throws Exception {
        DB db = new DB();
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor WHERE specialization = ? COLLATE utf8mb4_general_ci";
    
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
    
            preparedStatement.setString(1, selectedSpecialty);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String docCode = resultSet.getString("docCode");
                    String name = resultSet.getString("nameD");
                    String surname = resultSet.getString("surname");
                    String specialization = resultSet.getString("specialization");
    
                    Time availableTime = resultSet.getTime("availableTime");
                    LocalTime localAvailableTime = availableTime != null ? availableTime.toLocalTime() : null;
    
                    List<LocalTime> availableTimeSlots = new ArrayList<>();
                    if (localAvailableTime != null) {
                        availableTimeSlots.add(localAvailableTime);
                    }
    
                    int availableMinutes = resultSet.getInt("availableMinutes");
    
                    Doctor doctor = new Doctor(docCode, name, surname, specialization, availableTimeSlots, availableMinutes);
                    doctors.add(doctor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error fetching doctors by specialization", e);
        }
        return doctors;
    }

}
