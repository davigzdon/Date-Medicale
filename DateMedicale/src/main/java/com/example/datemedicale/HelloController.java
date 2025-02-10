package com.example.datemedicale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class HelloController {
    private final MedicalService service = new MedicalService();

    @FXML
    private Label welcomeText;

    @FXML
    private TextArea dataDisplay; // Zona în care afișăm mesajele

    @FXML
    private TextField inputType; // Câmp pentru introducerea tipului valorii: BMI sau BP
    @FXML
    private TextField inputValue; // Câmp pentru introducerea valorii BMI sau BP (sys/dia)
    @FXML
    private TextField inputDate; // Câmp pentru data necesară (yyyy-MM-dd)

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Medical Data Application!");
    }

    @FXML
    protected void showAllData() {
        StringBuilder result = new StringBuilder();
        for (MedicalData data : service.getAllData()) {
            result.append(data.getType())
                    .append(" | ")
                    .append(data.getDetails())
                    .append(" | Date: ")
                    .append(data.getDate())
                    .append("\n");
        }
        dataDisplay.setText(result.isEmpty() ? "No data available" : result.toString());
    }

    @FXML
    protected void checkHealth() {
        int currentMonth = LocalDate.now().getMonthValue();
        boolean isHealthy = service.isHealthy(currentMonth);
        dataDisplay.setText("Health status: " + (isHealthy ? "Healthy" : "Not Healthy"));
    }

    @FXML
    protected void addUserData() {
        String type = inputType.getText();
        String value = inputValue.getText();

        try {
            if (type.equalsIgnoreCase("BMI")) {
                double bmiValue = Double.parseDouble(value);
                service.addBMI(LocalDate.now(), bmiValue);
                dataDisplay.setText("BMI Data Added: " + bmiValue);
            } else if (type.equalsIgnoreCase("BP")) {
                String[] bpValues = value.split("/"); // Format: systolic/diastolic
                int systolic = Integer.parseInt(bpValues[0].trim());
                int diastolic = Integer.parseInt(bpValues[1].trim());
                service.addBP(LocalDate.now(), systolic, diastolic);
                dataDisplay.setText("BP Data Added: " +
                        "Systolic: " + systolic + ", Diastolic: " + diastolic);
            } else {
                dataDisplay.setText("Invalid type! Enter either 'BMI' or 'BP'.");
            }
        } catch (Exception e) {
            dataDisplay.setText("Error adding data: " + e.getMessage());
        }
    }

    @FXML
    protected void saveDataToFile() {
        String dateStr = inputDate.getText();
        String filePath = "medical_data.txt"; // Locația unde vom salva fișierul

        try {
            LocalDate date = LocalDate.parse(dateStr);
            service.saveDataToFileAfterDate(filePath, date);
            dataDisplay.setText("Data saved successfully to file: " + filePath);
        } catch (Exception e) {
            dataDisplay.setText("Error saving data: " + e.getMessage());
        }
    }
}