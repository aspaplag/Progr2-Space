import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
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
        try (
        Statement statement = myCon.createStatement(); 
        ResultSet resultSet = statement.executeQuery("SELECT * FROM doctor")
        ) { 
            while (resultSet.next()) { 
                 { String docCode = resultSet.getString("docCode"); 
                 String name = resultSet.getString("nameD"); 
                 String surname = resultSet.getString("surname"); 
                 String specialization = resultSet.getString("specialization"); 
                 Time availableTime = resultSet.getTime("availableTime"); 
                 Doctor doctor = new Doctor(docCode, name, surname, specialization, availableTime); 
                 doctors.add(doctor); 
            } 
        }
    } catch (SQLException e) { 
        } 
            return doctors; 
    }

    public List<Appointment> createApptList() {
        List<Appointment> appointments = new ArrayList<>();
        try (
        Statement statement = myCon.createStatement(); 
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Appointment")) {  
        while (resultSet.next()) { 
            String doctCode = resultSet.getString("doctCode"); 
            String specialization = resultSet.getString("specialization"); 
            Time apptTime = resultSet.getTime("apptTime"); 
            Date apptDate = resultSet.getDate("apptDate"); 
            String patientName = resultSet.getString("patientName"); 
            Appointment appointment = new Appointment(doctCode, specialization, apptTime, apptDate, patientName);
                appointments.add(appointment);
            } 
        } catch (SQLException e) { 
        } 
            return appointments; 
        }
}

        
