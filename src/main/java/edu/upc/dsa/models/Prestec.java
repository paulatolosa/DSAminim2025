package edu.upc.dsa.models;

public class Prestec {

    String id;
    String idLector;
    String idLlibre;
    String dataPrestec;
    String dataFinal;
    boolean enTramit;

    public boolean isEnTramit() {
        return enTramit;
    }

    public void setEnTramit(boolean enTramit) {
        this.enTramit = enTramit;
    }



    public Prestec(String id, String idLector, String idLlibre, String dataPrestec, String dataFinal) {
        this.id = id;
        this.idLector = idLector;
        this.idLlibre = idLlibre;
        this.dataPrestec = dataPrestec;
        this.dataFinal = dataFinal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdLector() {
        return idLector;
    }

    public void setIdLector(String idLector) {
        this.idLector = idLector;
    }

    public String getIdLlibre() {
        return idLlibre;
    }

    public void setIdLlibre(String idLlibre) {
        this.idLlibre = idLlibre;
    }

    public String getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(String dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

}
