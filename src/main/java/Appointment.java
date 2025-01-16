import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private String docCode;
    private String specialization;
    private Time apptTime;
    private Date apptDate;
    private Patient patient;
    private Doctor doctor;
    private int priority; // Προτεραιότητα
    private int duration; // Διάρκεια σε λεπτά

    public Appointment(String docCode, String specialization, Time apptTime, Date apptDate, Patient patient, Doctor doctor, int priority,
            int duration) {
        if (specialization == null || apptTime == null || apptDate == null || patient == null || doctor == null) {
            throw new IllegalArgumentException("Appointment fields cannot be null");
        }
        this.docCode = docCode;
        this.specialization = specialization;
        this.apptTime = apptTime;
        this.patient = patient;
        this.doctor = doctor;
        this.priority = priority;
        this.duration = duration;
    }

    public String getDocCode() {
        return docCode;
    }

    public Time getApptTime() {
        return apptTime;
    }

    public Date getApptDate() {
        return apptDate;
    }

    public int getPriority() {
        return priority;
    }

    public int getDuration() {
        return duration;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDetails() {
        return "Specialization: " + specialization + ", Patient: " + patient.getName() +
                ", Doctor: " + doctor.getFullname();
    }

    @Override
    public String toString() {
        return "Appointment[" + "specialization='" + specialization + '\'' +
                ", Time=" + apptTime +
                ", Date=" + apptDate +
                ", patient=" + patient.getName() +
                ", doctor=" + doctor.getFullname() +
                ", priority=" + priority +
                ", duration=" + duration +
                ']';
    }
}
