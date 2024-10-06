package com.example;

import java.io.Serializable;

public class LoanBean implements Serializable {
    private double kwota;  // Kwota pożyczki
    private double procentRoczny;  // Procent roczny
    private int liczbaRat;  // Liczba rat

    public LoanBean() {
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getProcentRoczny() {
        return procentRoczny;
    }

    public void setProcentRoczny(double procentRoczny) {
        this.procentRoczny = procentRoczny;
    }

    public int getLiczbaRat() {
        return liczbaRat;
    }

    public void setLiczbaRat(int liczbaRat) {
        this.liczbaRat = liczbaRat;
    }

    public double getRata() {
        double p = (procentRoczny / 100) / 12;
        return (kwota * p) / (1 - Math.pow(1 + p, -liczbaRat));
    }
}
