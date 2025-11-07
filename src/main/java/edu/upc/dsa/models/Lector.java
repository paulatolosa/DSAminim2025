package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Lector {

    String id;
    String nom;
    String cognom;
    String dni;
    Float dataNeix;
    String origenNeix;
    String adreca;

    public Lector(String id, String nom, String cognom, String dni, Float dataNeix, String origenNeix, String adreca) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
        this.dni = dni;
        this.dataNeix = dataNeix;
        this.origenNeix = origenNeix;
        this.adreca = adreca;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Float getDataNeix() {
        return dataNeix;
    }

    public void setDataNeix(Float dataNeix) {
        this.dataNeix = dataNeix;
    }

    public String getOrigenNeix() {
        return origenNeix;
    }

    public void setOrigenNeix(String origenNeix) {
        this.origenNeix = origenNeix;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }


}