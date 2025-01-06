USE sql7755696;

CREATE TABLE Patient (
AMKA varchar(9) NOT NULL PRIMARY KEY,
NameP varchar(40) NOT NULL,
Surname varchar(40) NOT NULL,
dateOfBirth date NOT NULL,
phoneNumber varchar(12) NOT NULL,
addressP varchar(225) NOT NULL,
email varchar(50) NOT NULL,
medicalRecord varchar(1000),
gender char(1)
);

CREATE TABLE Doctor(
docCode varchar(40) NOT NULL PRIMARY KEY,
nameD varchar(40),
surname varchar(40),
specialization varchar(40),
availableTime time
);

CREATE TABLE Appointment(
doctCode varchar(40) REFERENCES Doctor(docCode),
specialization varchar(40),
apptTime time,
apptDate date,
patientName varchar(40) REFERENCES Patient(Name)
);
