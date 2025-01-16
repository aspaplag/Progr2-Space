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

CREATE TABLE Appointment(
    docCode REFERENCES Doctor(docCode),
    specialization varchar(40),
    apptTime time,
    apptDate date,
    patientAMKA varchar(40) REFERENCES Patient(AMKA),
    priority INT,
    duration INT
);
