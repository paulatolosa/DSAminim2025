package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SistemaManagerTest {

    SistemaManager sistemaManager;

    @Before
    public void setUp() {
        this.sistemaManager = SistemaManagerImpl.getInstance();
        ((SistemaManagerImpl) sistemaManager).clear();

        // Afegim alguns lectors
        sistemaManager.addLector("L1", "Anna", "Garcia", "12345678A", 1990.05f, "Barcelona", "Carrer Major 1");
        sistemaManager.addLector("L2", "Pau", "Martí", "23456789B", 1995.12f, "Girona", "Avinguda del Mar 3");

        // Creem alguns llibres i els afegim al magatzem
        Llibre llibre1 = new Llibre("B1", "ISBN-001", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");
        Llibre llibre2 = new Llibre("B2", "ISBN-002", "La Muntanya", "Seix Barral", 2019, 1, "Autor2", "Natura");
        Llibre llibre3 = new Llibre("B3", "ISBN-001", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");

        sistemaManager.emmagatzemarLlibre(llibre1);
        sistemaManager.emmagatzemarLlibre(llibre2);
        sistemaManager.emmagatzemarLlibre(llibre3);

        // Cataloguem els dos primers llibres
        sistemaManager.catalogarLlibre();
        sistemaManager.catalogarLlibre();
    }

    @After
    public void tearDown() {
        ((SistemaManagerImpl) sistemaManager).clear();
    }

    @Test
    public void testAddLector() {
        sistemaManager.addLector("L3", "Maria", "Soler", "34567890C", 2000.11f, "Tarragona", "Rambla Nova 10");
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        Lector lector = impl.trobarLectorbyId("L3");
        Assert.assertNotNull(lector);
        Assert.assertEquals("Maria", lector.getNom());
        Assert.assertEquals("Soler", lector.getCognom());
    }

    @Test
    public void testEmmagatzemarLlibre() {
        Llibre nouLlibre = new Llibre("B4", "ISBN-004", "El Bosc", "Anagrama", 2021, 1, "Autor3", "Fantasia");
        sistemaManager.emmagatzemarLlibre(nouLlibre);
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        Assert.assertTrue(impl.magatzem.size() >= 1);
        Assert.assertFalse(impl.magatzem.get(impl.magatzem.size()-1).muntBuit());
    }

    @Test
    public void testCatalogarLlibre() {
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        int abans = impl.llibresCatalogats.size();
        sistemaManager.catalogarLlibre();
        int despres = impl.llibresCatalogats.size();
        Assert.assertTrue(despres >= abans);
    }

    @Test
    public void testAfegirPrestec() {
        // Assumim que ISBN-001 ja està catalogat
        sistemaManager.addPrestec("P1", "L1", "ISBN-001", "2025-11-01", "2025-11-15");
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        Assert.assertEquals(1, impl.prestecs.size());
        Prestec p = impl.prestecs.get(0);
        Assert.assertEquals("L1", p.getIdLector());
        Assert.assertTrue(p.isEnTramit());
    }

    @Test
    public void testGetPrestecPerLector() {
        sistemaManager.addPrestec("P2", "L2", "ISBN-001", "2025-11-02", "2025-11-10");
        List<Prestec> prestecs = sistemaManager.getPrestecPerLector("L2");
        Assert.assertEquals(1, prestecs.size());
        Assert.assertEquals("L2", prestecs.get(0).getIdLector());
    }

    @Test
    public void testLectorActualitzat() {
        // Mateix ID però dades noves
        sistemaManager.addLector("L1", "Anna", "Garcia", "12345678A", 1990.05f, "Barcelona", "Nova Adreça 5");
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        Lector lector = impl.trobarLectorbyId("L1");
        Assert.assertEquals("Nova Adreça 5", lector.getAdreca());
    }

    @Test
    public void testClear() {
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        impl.clear();
        Assert.assertEquals(0, impl.lectors.size());
        Assert.assertEquals(0, impl.magatzem.size());
        Assert.assertEquals(0, impl.llibresCatalogats.size());
        Assert.assertEquals(0, impl.prestecs.size());
    }
}
