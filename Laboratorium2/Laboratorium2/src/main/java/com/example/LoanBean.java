package com.example;

import java.io.Serializable;

public class LoanBean implements Serializable {
    private double kwota;  // Kwota pożyczki
    private double procentRoczny;  // Procent roczny
    private int liczbaRat;  // Liczba rat
    private double base;
    private double result;
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

    public double getRata() throws IllegalArgumentException {
    if (kwota <= 0) {
        throw new IllegalArgumentException("Kwota musi być wartością większą od 0.");
    }
    if (procentRoczny <= 0) {
        throw new IllegalArgumentException("Procent roczny musi być wartością większą od 0.");
    }
    if (liczbaRat <= 0) {
        throw new IllegalArgumentException("Liczba rat musi być wartością większą od 0.");
    }

    // Calculate monthly interest rate
    double p = (procentRoczny / 100) / 12;
    if (p <= 0) {
        throw new IllegalArgumentException("Miesięczny przyrost jest wartością mniejąszą\równą zeru.");
    }

    base = (1 - Math.pow(1 + p, -liczbaRat));
    if(base == 0){
        throw new IllegalArgumentException("Dzielenie przez zero");
    }
    result = ((kwota * p) / base);
    
    return result;
    }
}
