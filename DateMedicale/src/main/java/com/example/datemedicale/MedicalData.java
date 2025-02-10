package com.example.datemedicale;

import java.time.LocalDate;

// Clasa abstractă MedicalData
public abstract class MedicalData {
    private LocalDate date;

    public MedicalData(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public abstract String getType();

    public abstract String getDetails();
}

// Clasa pentru BMI
class BMIData extends MedicalData {
    private double value;

    public BMIData(LocalDate date, double value) {
        super(date);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String getType() {
        return "BMI";
    }

    @Override
    public String getDetails() {
        return "Value: " + value;
    }
}

// Clasa pentru BP
class BPData extends MedicalData {
    private int systolic;
    private int diastolic;

    public BPData(LocalDate date, int systolic, int diastolic) {
        super(date);
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public int getSystolic() {
        return systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    @Override
    public String getType() {
        return "BP";
    }

    @Override
    public String getDetails() {
        return "Systolic: " + systolic + ", Diastolic: " + diastolic;
    }
}