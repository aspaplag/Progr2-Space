package space;

import java.time.LocalDateTime;

public class Appointment {
    private String specialization;
    private LocalDateTime dateTime;
    private Patient patient;
    private Doctor doctor;
    private int priority; // Προτεραιότητα
    private int duration; // Διάρκεια σε λεπτά

    public Appointment(String specialization, LocalDateTime dateTime, Patient patient, Doctor doctor, int priority,
            int duration) {
        if (specialization == null || dateTime == null || patient == null || doctor == null) {
            throw new IllegalArgumentException("Appointment fields cannot be null");
        }
        this.specialization = specialization;
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.priority = priority;
        this.duration = duration;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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
                ", dateTime=" + dateTime +
                ", patient=" + patient.getName() +
                ", doctor=" + doctor.getFullname() +
                ", priority=" + priority +
                ", duration=" + duration +
                ']';
    }
}
