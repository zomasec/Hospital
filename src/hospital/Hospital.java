package hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import database.DataBase;

public class Hospital extends JFrame {
    private final DataBase db;

    public Hospital() {
        db = new DataBase("admin", "admin123#");
        setTitle("Hospital Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        add(panel, BorderLayout.CENTER);

        JButton btnAddPatient = new JButton("Add Patient");
        JButton btnViewDoctors = new JButton("View Doctors");
        JButton btnUpdateTest = new JButton("Update Test");
        JButton btnDeleteDoctor = new JButton("Delete Doctor");
        JButton btnSearchPatients = new JButton("Search Patients");

        panel.add(btnAddPatient);
        panel.add(btnViewDoctors);
        panel.add(btnUpdateTest);
        panel.add(btnDeleteDoctor);
        panel.add(btnSearchPatients);

        // Adding action listeners for buttons
        btnAddPatient.addActionListener(this::addPatientAction);
        btnUpdateTest.addActionListener(this::updateTestAction);
        btnDeleteDoctor.addActionListener(this::deleteDoctorAction);
        btnSearchPatients.addActionListener(this::searchPatientsAction);

        setVisible(true);
    }

    private void addPatientAction(ActionEvent event) {
        JTextField ssnField = new JTextField(10);
        JTextField nameField = new JTextField(30);
        JTextField insuranceField = new JTextField(30);
        JTextField dateAdmittedField = new JTextField(15);
        JTextField dateCheckedOutField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("SSN:"));
        panel.add(ssnField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Insurance:"));
        panel.add(insuranceField);
        panel.add(new JLabel("Date Admitted:"));
        panel.add(dateAdmittedField);
        panel.add(new JLabel("Date Checked Out:"));
        panel.add(dateCheckedOutField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Patient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String values = String.format("'%s', '%s', '%s', '%s', '%s'",
                                          ssnField.getText(),
                                          nameField.getText(),
                                          insuranceField.getText(),
                                          dateAdmittedField.getText(),
                                          dateCheckedOutField.getText());
            if (db.insertRecord("Patients", values)) {
                JOptionPane.showMessageDialog(null, "Patient added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add patient.");
            }
        }
    }

    private void updateTestAction(ActionEvent event) {
        JTextField testIdField = new JTextField(10);
        JTextField resultField = new JTextField(30);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Test ID:"));
        panel.add(testIdField);
        panel.add(new JLabel("New Result:"));
        panel.add(resultField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Test Result", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String set = "Result = '" + resultField.getText() + "'";
            String condition = "TestID = " + testIdField.getText();
            if (db.updateRecord("Tests", set, condition)) {
                JOptionPane.showMessageDialog(null, "Test updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update test.");
            }
        }
    }

    private void deleteDoctorAction(ActionEvent event) {
        String dssn = JOptionPane.showInputDialog("Enter Doctor's SSN to Delete:");
        if (dssn != null && !dssn.trim().isEmpty()) {
            if (db.deleteRecord("Doctors", "DSSN = '" + dssn + "'")) {
                JOptionPane.showMessageDialog(null, "Doctor deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete doctor.");
            }
        }
    }

    private void searchPatientsAction(ActionEvent event) {
        try {
            ResultSet rs = db.searchRecords("Patients");
            StringBuilder patientsInfo = new StringBuilder();
            while (rs.next()) {
                patientsInfo.append("Patient SSN: ").append(rs.getString("SSN"))
                            .append(", Name: ").append(rs.getString("Name"))
                            .append("\n");
            }
            JOptionPane.showMessageDialog(null, patientsInfo.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error searching patients: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Hospital();
    }

    
}
