
import java.sql.Date;

public class Patient {

    private String name;
    private String surname;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String amka;
    private String medicalRecord;
    private String gender;

    public Patient(String name, String surname, Date dateOfBirth, String address, String phoneNumber, String email,
            String amka, String medicalRecord, String gender) {

        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.amka = amka;
        this.medicalRecord = medicalRecord;
        this.gender = gender;
    }

    public String getName() {

        return name;
    }

    public String getSurname() {

        return surname;
    }

    public Date getDateOfBirth() {

        return dateOfBirth;
    }

    public String getAddress() {

        return address;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public String getEmail() {

        return email;
    }

    public String getAmka() {

        return amka;
    }

    public String medicalRecord() {

        return medicalRecord;
    }

    public String getGender() {

        return gender;
    }

    @Override
    public String toString() {

        return "Patient{" + "name='" + name + '\'' + ", surname='" + surname + '\'' + ", dateOfBirth='" + dateOfBirth
                + '\'' +
                ", address='" + address + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email + '\'' +
                '\'' + ", amka='" + amka + '\'' + ", gender='" + gender + '\''
                + '}';
    }
}
