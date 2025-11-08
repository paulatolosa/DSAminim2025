package edu.upc.dsa;

import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;
import edu.upc.dsa.models.Lector;

import java.util.List;

public interface SistemaManager {

    public void addLector(String id, String nom, String cognom, String dni, String dataNeix, String origenNeix, String adreca);
    public void emmagatzemarLlibre(Llibre llibre);
    public void catalogarLlibre();
    public void addPrestec(String idPrestec, String idLector, String idLlibre, String dataPrestec, String dataFinal);
    List<Prestec> getPrestecPerLector(String idLector);

    // metodes necessaris a part dels demanats
    public void crearLlibre(String id, String isbn, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica);
    public Lector trobarLectorbyId(String lectorId);
    public void clear();

    // metodes per fer la rest api
    public int getLectorsSize();
    public List<Lector> getLectors();
    public List<Llibre> getLlibresCatalogats();
    public boolean llibreExisteixAlMagatzem(String idLlibre);
    public boolean llibreCatalogat(String idLlibre);
}

