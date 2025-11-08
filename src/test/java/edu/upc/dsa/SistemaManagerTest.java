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
        sistemaManager.addLector("L1", "Anna", "Garcia", "12345678A", "1990-05", "Barcelona", "Carrer Major 1");
        sistemaManager.addLector("L2", "Pau", "Martí", "23456789B", "1995-12", "Girona", "Avinguda del Mar 3");

        // Creem alguns llibres i els afegim al magatzem
        Llibre llibre1 = new Llibre("B1", "ISBN-001", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");
        Llibre llibre2 = new Llibre("B2", "ISBN-002", "La Muntanya", "Seix Barral", 2019, 1, "Autor2", "Natura");
        Llibre llibre3 = new Llibre("B3", "ISBN-003", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");
        Llibre llibre4 = new Llibre("B4", "ISBN-004", "Secrets del Mar", "Anagrama", 2021, 1, "Autor3", "Ficció");
        Llibre llibre5 = new Llibre("B5", "ISBN-005", "Històries de la Nit", "Destino", 2018, 2, "Autor4", "Misteri");
        Llibre llibre6 = new Llibre("B6", "ISBN-006", "Camins Antics", "Empúries", 2022, 1, "Autor5", "Història");
        Llibre llibre7 = new Llibre("B7", "ISBN-007", "La Ciutat Perduda", "Planeta", 2023, 1, "Autor6", "Aventura");
        Llibre llibre8 = new Llibre("B8", "ISBN-008", "El Jardí Secret", "Columna", 2017, 3, "Autor7", "Fantasia");
        Llibre llibre9 = new Llibre("B9", "ISBN-009", "La Muntanya", "Seix Barral", 2019, 1, "Autor2", "Natura");
        Llibre llibre10 = new Llibre("B10", "ISBN-010", "Records del Vent", "Destino", 2021, 1, "Autor8", "Poesia");
        Llibre llibre11 = new Llibre("B11", "ISBN-011", "L’Últim Desig", "Anagrama", 2020, 2, "Autor9", "Drama");
        Llibre llibre12 = new Llibre("B12", "ISBN-012", "Els Colors del Temps", "Empúries", 2024, 1, "Autor10", "Romàntica");
        Llibre llibre13 = new Llibre("B13", "ISBN-013", "La Porta dels Somnis", "Planeta", 2023, 1, "Autor11", "Fantasia");
        Llibre llibre14 = new Llibre("B14", "ISBN-014", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");

        sistemaManager.emmagatzemarLlibre(llibre1);
        sistemaManager.emmagatzemarLlibre(llibre2);
        sistemaManager.emmagatzemarLlibre(llibre3);
        sistemaManager.emmagatzemarLlibre(llibre4);
        sistemaManager.emmagatzemarLlibre(llibre5);
        sistemaManager.emmagatzemarLlibre(llibre6);
        sistemaManager.emmagatzemarLlibre(llibre7);
        sistemaManager.emmagatzemarLlibre(llibre8);
        sistemaManager.emmagatzemarLlibre(llibre9);
        sistemaManager.emmagatzemarLlibre(llibre10);
        sistemaManager.emmagatzemarLlibre(llibre11);
        sistemaManager.emmagatzemarLlibre(llibre12);
        sistemaManager.emmagatzemarLlibre(llibre13);
        sistemaManager.emmagatzemarLlibre(llibre14);


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
        sistemaManager.addLector("L3", "Maria", "Soler", "34567890C", "2000-11", "Tarragona", "Rambla Nova 10");
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
    public void testAddPrestec() {
        // Assumim que ISBN-001 ja està catalogat
        sistemaManager.addPrestec("P1", "L1", "B14", "2025-11-01", "2025-11-15");
        sistemaManager.addPrestec("P2", "L2", "B14", "2025-11-01", "2025-11-15");
        sistemaManager.addPrestec("P3", "L2", "B12", "2025-11-01", "2025-11-15");
        sistemaManager.addPrestec("P4", "L2", "B13", "2025-11-01", "2025-11-15");
        SistemaManagerImpl impl = (SistemaManagerImpl) sistemaManager;
        Assert.assertEquals(2, impl.prestecs.size());
        Prestec p = impl.prestecs.get(0);
        Assert.assertEquals("L1", p.getIdLector());

    }

    @Test
    public void testGetPrestecPerLector() {
        sistemaManager.addPrestec("P5", "L2", "B14", "2025-11-02", "2025-11-10");
        List<Prestec> prestecs = sistemaManager.getPrestecPerLector("L2");
        Assert.assertEquals(1, prestecs.size());
        Assert.assertEquals("L2", prestecs.get(0).getIdLector());
    }

    @Test
    public void testLectorActualitzat() {
        // Mateix ID però dades noves
        sistemaManager.addLector("L1", "Anna", "Garcia", "12345678A", "1990-05", "Barcelona", "Nova Adreça 5");
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
