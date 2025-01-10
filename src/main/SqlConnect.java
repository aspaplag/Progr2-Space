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

   public void insertPatient(int amka, String name, String surname, Date dateOfBirth, String phoneNumber,
            String address,
            String email, String medicalRecord, String gender) throws Exception {
        DB db = new DB();
        String sql = "INSERT INTO Patient (AMKA, Name, Surname, dateOfBirth, phoneNumber, address, email, medicalRecord, gender) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = db.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, amka);
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
    public void insertAppointment(String val1, String val2, Date val3, Time val4, String val5) throws Exception {

        DB db = new DB();
        String sql = "INSERT INTO Appointment(docCode, specialization, apptTime, apptDate, patientName) VALUES(?, ?, ?, ?, ?)";
        try(Connection con = db.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, val1);
            pstmt.setString(2, val2);
            pstmt.setDate(3, val3);
            pstmt.setTime(4, val4);
            pstmt.setString(5, val5);

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
                            rs.getString("dateΟfΒirth"),
                            rs.getString("addressP"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"),
                            rs.getInt("AMKA"),
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

    public List<Doctor> createDocList() throws Exception {

        DB db = new DB();
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";

        try (Connection con = db.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery(sql)) {

            while (resultSet.next()) {
                String docCode = resultSet.getString("docCode");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String specialization = resultSet.getString("specialization");
                List<LocalTime> availableTimeSlots = (List<LocalTime>) resultSet.getTime("availableTimeSlots");
                int availableMinutes = resultSet.getInt("availableMinutes");
                Doctor doctor = new Doctor(docCode, name, surname, specialization, availableTimeSlots, availableMinutes);
                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public List<Appointment> createApptList() throws Exception {

        DB db = new DB();
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";
    
        try (Connection con = db.getConnection();
            Statement pstmt = con.createStatement();
            ResultSet resultSet = pstmt.executeQuery(sql)) {

            while (resultSet.next()) {
                String docCode = resultSet.getString("docCode");
                String specialization = resultSet.getString("specialization");
                Time apptTime = resultSet.getTime("apptTime");
                Date apptDate = resultSet.getDate("apptDate");
                
                Patient patient = createPatientFromResultSet(resultSet);

                int priority = resultSet.getInt("priority");
                int duration = resultSet.getInt("duration");
    
                // Fetch doctor details (ensure the ResultSet has necessary columns)
                Doctor doctor = createDoctorFromResultSet(resultSet);
    
                // Create Appointment object
                Appointment appointment = new Appointment(docCode, specialization, apptTime, apptDate, patient, doctor, priority, duration);
                appointments.add(appointment);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public Doctor createDoctorFromResultSet(ResultSet resultSet) throws SQLException {
        // Retrieve data from ResultSet
        String docCode = resultSet.getString("docCode");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String specialization = resultSet.getString("specialization");
        List<LocalTime> availableTimeSlots = new ArrayList<>();
        String[] timeStrings = resultSet.getString("availableTime").split(",");
        for (String timeString : timeStrings) {
            availableTimeSlots.add(LocalTime.parse(timeString.trim()));
        }
    
        int availableMinutes = resultSet.getInt("availableMinutes");
    
        // Create and return the Doctor object
        return new Doctor(docCode, name, surname, specialization, availableTimeSlots, availableMinutes);
    }

    public Patient createPatientFromResultSet(ResultSet resultSet) throws SQLException {
        
        int amka = resultSet.getInt("AMKA");
        String name = resultSet.getString("Name");
        String surname = resultSet.getString("Surname");
        String dateOfBirth = resultSet.getString("dateOfBirth");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("adress");
        String email = resultSet.getString("email");
        String medicalRecord = resultSet.getString("medicalRecord");
        String gender = resultSet.getString("gender");
        
        Patient patient = new Patient(name, surname, dateOfBirth, address, phoneNumber, email, amka, gender);
        
        return patient;
    }

    public List<Doctor> getDoctorsBySpecialization(String selectedSpecialty) throws Exception {

        DB db = new DB();
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor WHERE specialization = ?";

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, selectedSpecialty);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String docCode = resultSet.getString("docCode");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String specialization = resultSet.getString("specialization");
                    List<LocalTime> availableTimeSlots = (List<LocalTime>) resultSet.getTime("availableTimeSlots");
                    int availableMinutes = resultSet.getInt("availableMinutes");
                    Doctor doctor = new Doctor(docCode, name, surname, specialization, availableTimeSlots, availableMinutes);
                    doctors.add(doctor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public List<Appointment> getAppointments() throws Exception {
        return createApptList(); // Reuse `createApptList`
    }
}
