/*
 * This SQL script creates a database called "Hospital" and defines several tables:
 * - Patients: stores information about patients, including their SSN, name, medical insurance, date admitted, and date checked out.
 * - Doctors: stores information about doctors, including their DSSN, name, and specialization.
 * - Tests: stores information about medical tests, including the test ID, test name, test date, test time, and test result.
 * - Patient_Doctor: establishes a many-to-many relationship between patients and doctors, indicating which doctors are assigned to which patients.
 * - Doctor_Test: establishes a many-to-many relationship between doctors and tests, indicating which tests are assigned to which doctors.
 *
 * The primary keys and foreign keys are defined to ensure data integrity and enforce referential integrity between the tables.
 */
 
CREATE DATABASE Hospital

CREATE TABLE Patients (
    SSN VARCHAR(9) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    MedicalInsurance VARCHAR(100) NOT NULL,
    DateAdmitted DATETIME,
    DateCheckedOut DATETIME
);

CREATE TABLE Doctors (
    DSSN VARCHAR(9) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Specialization VARCHAR(100) NOT NULL
);

CREATE TABLE Tests (
    TestID INT PRIMARY KEY,
    TestName VARCHAR(100) NOT NULL,
    TestDate DATETIME,
    TestTime TIME,
    Result VARCHAR(255) NOT NULL
);

CREATE TABLE Patient_Doctor (
    SSN VARCHAR(9) NOT NULL,
    DSSN VARCHAR(9) NOT NULL,
    FOREIGN KEY (SSN) REFERENCES Patients(SSN),
    FOREIGN KEY (DSSN) REFERENCES Doctors(DSSN),
    PRIMARY KEY (SSN, DSSN)
);

CREATE TABLE Doctor_Test (
    TestID INT NOT NULL,
    DSSN VARCHAR(9) NOT NULL,
    FOREIGN KEY (TestID) REFERENCES Tests(TestID),
    FOREIGN KEY (DSSN) REFERENCES Doctors(DSSN),
    PRIMARY KEY (TestID, DSSN)
);
