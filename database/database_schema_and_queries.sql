
CREATE TABLE Patient (
    AMKA varchar NOT NULL PRIMARY KEY,
    NameP varchar(40) NOT NULL,
    Surname varchar(40) NOT NULL,
    dateOfBirth date NOT NULL,
    phoneNumber varchar NOT NULL,
    addressP varchar(225) NOT NULL,
    email varchar NOT NULL,
    medicalRecord varchar,
    gender char(1)
);


CREATE TABLE Doctor(
    docCode varchar(40) NOT NULL PRIMARY KEY,
    name varchar(40),
    surname varchar(40),
    specialization varchar(40),
    availableTime time,
    availableMinutes int
);


CREATE TABLE Appointment (
    docCode REFERENCES Doctor(docCode),
    specialization varchar(40),
    apptTime time,
    apptDate date,
    patientAMKA varchar(40) REFERENCES Patient(AMKA),
    priority INT,
    duration INT,
    
    -- Foreign key references
    FOREIGN KEY (docCode) REFERENCES Doctor(docCode),
    FOREIGN KEY (patientAMKA) REFERENCES Patient(AMKA)
);


INSERT INTO Patient (AMKA, Name, Surname, dateOfBirth, phoneNumber, address, email, medicalRecord, gender)
VALUES ('12345678901', 'John', 'Doe', '1985-07-15', '1234567890', '123 Main St, Athens', 'johndoe@example.com', 'No known allergies', 'M');

INSERT INTO Doctor (docCode, name, surname, specialization, availableTime)
VALUES ('DOC123', 'Emily', 'Smith', 'Cardiologist', '10:00:00');

INSERT INTO Appointment (docCode, specialization, apptTime, apptDate, patientAMKA)
VALUES ('DOC123', 'Cardiologist', '11:00:00', '2024-12-10', '12345678901');

SELECT * FROM Appointment
WHERE docCode = 'DOC123';

SELECT * FROM Appointment
WHERE patientAMKA = '12345678901';

UPDATE Patient
SET phoneNumber = '0987654321'
WHERE AMKA = '12345678901';

DELETE FROM Appointment
WHERE docCode = 'DOC123' AND patientAMKA = '12345678901' AND apptDate = '2024-12-10';

SELECT docCode, name, surname, specialization
FROM Doctor;

SELECT AMKA, Name, Surname, dateOfBirth, phoneNumber, address, email, gender
FROM Patient;

SELECT * FROM Appointment
WHERE apptDate = '2024-12-10';
