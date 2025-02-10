package com.example.datemedicale;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Serviciu pentru gestionarea datelor medicale
public class MedicalService {
    private List<MedicalData> medicalDataList = new ArrayList<>();

    // Constructor: Populăm datele inițiale
    public MedicalService() {
        medicalDataList.add(new BMIData(LocalDate.now().minusDays(10), 22.5)); // BMI normal
        medicalDataList.add(new BMIData(LocalDate.now().minusDays(20), 29.3)); // BMI mare
        medicalDataList.add(new BPData(LocalDate.now().minusDays(5), 120, 75)); // BP normal
        medicalDataList.add(new BPData(LocalDate.now().minusDays(15), 140, 90)); // BP mare
    }

    public void addBMI(LocalDate date, double value) {
        medicalDataList.add(new BMIData(date, value));
    }

    public void addBP(LocalDate date, int systolic, int diastolic) {
        medicalDataList.add(new BPData(date, systolic, diastolic));
    }

    public List<MedicalData> getAllData() {
        return medicalDataList;
    }

    public boolean isHealthy(int currentMonth) {
        LocalDate now = LocalDate.now();
        LocalDate twoMonthsAgo = now.minusMonths(2);

        for (MedicalData data : medicalDataList) {
            if (data.getDate().isAfter(twoMonthsAgo) && data.getDate().getMonthValue() == currentMonth) {
                if (data instanceof BMIData) {
                    double bmi = ((BMIData) data).getValue();
                    if (bmi <= 18.5 || bmi >= 25) return false;
                } else if (data instanceof BPData) {
                    int systolic = ((BPData) data).getSystolic();
                    int diastolic = ((BPData) data).getDiastolic();
                    if (systolic < 100 || systolic > 130 || diastolic < 60 || diastolic > 80) return false;
                }
            }
        }
        return true;
    }

    public void saveDataToFileAfterDate(String filePath, LocalDate date) throws IOException {
        List<MedicalData> filteredData = new ArrayList<>();
        for (MedicalData data : medicalDataList) {
            if (data.getDate().isAfter(date)) {
                filteredData.add(data);
            }
        }

        // Sortăm după dată
        filteredData.sort(Comparator.comparing(MedicalData::getDate));

        // Scriem datele filtrate într-un fișier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (MedicalData data : filteredData) {
                writer.write(data.getType() + " | " + data.getDetails() + " | " + data.getDate());
                writer.newLine();
            }
        }
    }
}