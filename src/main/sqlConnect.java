import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class sqlConnect {
    private final  String path = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7755696";
    private final String username = "sql7755696";
    private final String password = "tGKZtBXEFM"; 
    private Connection myCon;

    public Connection makeConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.myCon = DriverManager.getConnection(this.path, this.username, this.password);

        } catch (ClassNotFoundException ex) {

        }
        return this.myCon;
    }

    public void insertPatient(String value1, String value2, String value3, Date value4, String value5, String value6,
     String value7, String value8, String value9, Connection myCon) {
        try {
            String sql = "INSERT INTO Patient(AMKA, Name, Surname, dateOfBirth, phoneNumber, address, email, medicalRecord, gender) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = myCon.prepareStatement(sql);

            insertStmt.setString(1, value1);
            insertStmt.setString(2, value2);
            insertStmt.setString(3, value3);
            insertStmt.setDate(4, value4);
            insertStmt.setString(5, value5);
            insertStmt.setString(6, value6);
            insertStmt.setString(7, value7);
            insertStmt.setString(8, value8);
            insertStmt.setString(9, value9);
            insertStmt.executeUpdate();
        } catch (SQLException ex) {
        }

    }

    public void insertAppointment(String val1, String val2, Date val3, Time val4, String val5) {
        try {
            String sql = "INSERT INTO Appointment(docCode, specialization, apptTime, apptDate, patientName) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = myCon.prepareStatement(sql);
            insertStmt.setString(1, val1);
            insertStmt.setString(2, val2);
            insertStmt.setDate(3, val3);
            insertStmt.setTime(4, val4);
            insertStmt.setString(5, val5);
        } catch (SQLException ex) {
        }
    }

    public void selectTD(String tableName) {
        try {
            PreparedStatement selectStmt = myCon.prepareStatement("SELECT * FROM" + tableName);
            selectStmt.executeQuery();
        } catch (SQLException ex) {
        }
    }

    public void selectAMKA(String amkaString) {
        try {
            String sql = "SELECT * FROM Patient WHERE AMKA = " + amkaString;
            PreparedStatement selectStmt = myCon.prepareStatement(sql);
            selectStmt.execute();
        } catch (SQLException ex) {
        }
    }

    public String getPath() {
        return path;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Doctor> createDocList() {
        List<Doctor> doctors = new ArrayList<>();
        try (Statement statement = myCon.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM doctor")) {

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

    public List<Appointment> createApptList() {
        List<Appointment> appointments = new ArrayList<>();
        try (Statement statement = myCon.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Appointment")) {
    
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

    public List<Doctor> getDoctorsBySpecialization(String selectedSpecialty) {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctor WHERE specialization = ?";
        try (PreparedStatement preparedStatement = myCon.prepareStatement(query)) {
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

    public List<Appointment> getAppointments() {
        return createApptList(); // Reuse `createApptList`
    }
}