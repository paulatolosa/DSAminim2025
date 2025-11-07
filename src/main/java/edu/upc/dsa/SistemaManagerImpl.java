package edu.upc.dsa;

import edu.upc.dsa.exceptions.LectorNoExisteixException;
import edu.upc.dsa.exceptions.LlibreNoExisteixException;
import edu.upc.dsa.exceptions.MagatzemBuitException;
import edu.upc.dsa.exceptions.ExemplarsNoDisponibleException;
import edu.upc.dsa.exceptions.MateixIdIParamatresException;

import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Munt;
import edu.upc.dsa.models.Prestec;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import org.apache.log4j.Logger;

public class SistemaManagerImpl implements SistemaManager {

    private static SistemaManager instance;
    final static Logger logger = Logger.getLogger(SistemaManagerImpl.class);

    List<Lector> lectors;
    List<Munt> magatzem;
    Map<String, Llibre> llibresCatalogats;
    List<Prestec> prestecs;

    private SistemaManagerImpl() {
        this.lectors = new ArrayList<Lector>();
        this.magatzem = new ArrayList<>();
        this.llibresCatalogats = new HashMap<>();
        this.prestecs = new ArrayList<>();
    }
    public static SistemaManager getInstance() {
        if (instance == null) {
            instance = new SistemaManagerImpl();
        }
        return instance;
    }


    public void addLector(String id, String nom, String cognom, String dni, Float dataNeix, String origenNeix, String adreca){
        logger.info("Inici addLector: ID = " + id + ", nom = " + nom + ", cognom = " + cognom + ", dni = " + dni);

        try {
            for (Lector lector : lectors) {
                if (lector.getId().equals(id)) {
                    lector.getId();
                    if (lector.getNom().equals(nom) && lector.getDni().equals(dni) && lector.getDataNeix().equals(dataNeix) && lector.getOrigenNeix().equals(origenNeix) && lector.getAdreca().equals(adreca) && lector.getAdreca().equals(adreca)) {
                        logger.error("No pots assignar mateixos parametres per un lector ja existent ");
                        throw new MateixIdIParamatresException("El lector amb ID="+id+" ja té associats aquests parametres");

                    } else {
                        lector.setNom(nom);
                        lector.setDni(dni);
                        lector.setDataNeix(dataNeix);
                        lector.setOrigenNeix(origenNeix);
                        lector.setAdreca(adreca);
                        logger.info("Lector amb ID=" + id + " actualitzat");
                        return;
                    }
                }
            }
            Lector lector = new Lector(id, nom, cognom, dni, dataNeix, origenNeix, adreca);
            lectors.add(lector);
            logger.info("Lector amb ID=" + id + " afegit");
        }
        catch(MateixIdIParamatresException ex){
            logger.error("excepció: ID amb mateixos parametres");
        }
    }

    public void emmagatzemarLlibre(Llibre llibre){
        logger.info("Inici emmagatzemarLlibre:" +llibre);
        if(magatzem.size()==0){
            Munt muntInicial=new Munt();
            muntInicial.addLlibre(llibre);
            magatzem.add(muntInicial);
            logger.info("S'ha cret el primer munt de llibres al magatzem i s'ha afegit el llibre:"+llibre);
        }
        else{
            Munt muntExistent=magatzem.get(magatzem.size()-1);
            if(!muntExistent.muntPle()){
                muntExistent.addLlibre(llibre);
                logger.info("S'ha afegit el llibre:"+llibre+" al munt ja existent="+muntExistent);
            }
            else{
                Munt munt=new Munt();
                munt.addLlibre(llibre);
                magatzem.add(munt);
                logger.info("S'ha creat un nou munt:"+munt+" al magatzem i s'ha afegit el llibre:"+llibre);
            }
        }
    }

    public void catalogarLlibre(){
        logger.info("Inici catalogarLlibre");
        try{
            if(magatzem.size()==0){
                logger.error("El magatzem es buit, no hi ha llibres a descatalogar");
                throw new MagatzemBuitException("El magatzem es buit");
            }
            else{
                Munt primerMuntMagatzem=magatzem.get(0);
                if(primerMuntMagatzem.muntBuit()){
                    magatzem.remove(0);
                    if(magatzem.size()==0){
                        logger.error("El magatzem es buit, no hi ha llibres a descatalogar");
                    }
                    primerMuntMagatzem=magatzem.get(0);
                }
                else{
                    Llibre llibre=primerMuntMagatzem.getLlibre();
                    logger.info("S'ha extret del la pila del magatzem el llibre:"+llibre);

                    String isbn=llibre.getIsbn();
                    if(llibresCatalogats.containsKey(isbn)){
                        llibresCatalogats.get(isbn).setNumExemplars(llibresCatalogats.get(isbn).getNumExemplars()+1);
                        logger.info("S'ha actualitzat el numero de exemplars disponibles a"+llibresCatalogats.get(isbn).getNumExemplars()+" per el llibre:"+llibre);
                    }
                    else{
                        llibresCatalogats.put(isbn,llibre);

                    }

                }

            }

        }
        catch(MagatzemBuitException ex){
            logger.error("Excepció: magatzem buit");
        }
    }

    public void addPrestec(String idPrestec, String idLector, String idLlibre, String dataPrestec, String dataFinal){
        logger.info("Inici addPrestec: idPresec="+idPrestec+",idLector="+idLector+", idLlibre="+idLlibre);
        try{
            if(trobarLectorbyId(idLector)==null){
                logger.error("usuari amb ID="+idLector+" no existeix");
                throw new LectorNoExisteixException("Letor amb ID="+idLector+" no existeix");
            }
            if(!llibresCatalogats.containsKey(idLlibre)){
                logger.error("Llibre amb ID="+idLlibre+" no existeix");
                throw new LlibreNoExisteixException("Llibre amb ID="+idLlibre+" no existeix");
            }
            Llibre llibre = llibresCatalogats.get(idLlibre);
            if (llibre.getNumExemplars() <= 0) {
                logger.error("Error: no hi ha exemplars disponibles del llibre amb ID=" + idLlibre);
                throw new ExemplarsNoDisponibleException("Llibre amb ID="+idLlibre+" no té suficients exemplars");
            }
            Prestec prestec=new Prestec(idPrestec,idLector,idLlibre,dataPrestec,dataFinal);
            prestec.setEnTramit(true);
            prestecs.add(prestec);
            llibre.setNumExemplars(llibre.getNumExemplars() - 1);
            logger.info("Prestec creat");
        }
        catch(LectorNoExisteixException|LlibreNoExisteixException|ExemplarsNoDisponibleException ex){
            logger.error("Excpeció");
        }
    }

    public List<Prestec> getPrestecPerLector(String idLector) {
        logger.info("Consultant préstecs del lector amb ID=" + idLector);

        List<Prestec> prestecsLector = new ArrayList<>();

        for (Prestec p : prestecs) {
            if (p.getIdLector().equals(idLector)) {
                prestecsLector.add(p);
            }
        }

        if (prestecsLector.isEmpty()) {
            logger.warn("El lector amb ID=" + idLector + " no té cap préstec registrat");
        } else {
            logger.info("S'han trobat " + prestecsLector.size() + " préstecs per al lector amb ID=" + idLector);
        }

        return prestecsLector;
    }


    // metodes necessaris a part dels demanats
    public void crearLlibre(String id, String isbn, String titol, String editorial, int anyPublicacio, int numEdicio, String autor, String tematica){
        Llibre llibre = new Llibre(id, isbn, titol, editorial, anyPublicacio, numEdicio, autor, tematica);
    }
    public Lector trobarLectorbyId(String lectorId){
        for (Lector lector : lectors) {
            if (lector.getId().equals(lectorId)) return lector;
        }
        return null;
    }

    public void clear() {
        this.lectors.clear();
        this.magatzem.clear();
        this.llibresCatalogats.clear();
        this.prestecs.clear();
    }

}