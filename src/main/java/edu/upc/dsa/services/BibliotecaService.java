package edu.upc.dsa.services;

import edu.upc.dsa.SistemaManager;
import edu.upc.dsa.SistemaManagerImpl;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Api(value = "/biblioteca", description = "Punt d'accés al servei SistemaManager")
@Path("/biblioteca")
public class BibliotecaService {

    private SistemaManager sm;

    public BibliotecaService() {
        this.sm = SistemaManagerImpl.getInstance();

        if (sm.getLectorsSize()==0) {
            this.sm.addLector("L1", "Anna", "Garcia", "12345678A", "1990-05", "Barcelona", "Carrer Major 1");
            this.sm.addLector("L2", "Pau", "Martí", "23456789B", "1995-12", "Girona", "Avinguda del Mar 3");

            Llibre llibre1 = new Llibre("B1", "ISBN-001", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");
            Llibre llibre2 = new Llibre("B2", "ISBN-002", "La Muntanya", "Seix Barral", 2019, 1, "Autor2", "Natura");
            Llibre llibre3 = new Llibre("B3", "ISBN-003", "El Viatge", "Planeta", 2020, 1, "Autor1", "Aventura");

            this.sm.emmagatzemarLlibre(llibre1);
            this.sm.emmagatzemarLlibre(llibre2);
            this.sm.emmagatzemarLlibre(llibre3);

            this.sm.catalogarLlibre();
            this.sm.catalogarLlibre();
            this.sm.catalogarLlibre();

            this.sm.addPrestec("P1", "L1", "B1", "2025-11-01", "2025-11-15");
        }
    }



    @POST
    @ApiOperation(value = "Afegir un nou lector", notes = "Crea un lector amb tota la informació")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lector creat", response = Lector.class),
            @ApiResponse(code = 400, message = "Error de validació")
    })
    @Path("/lectors")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response afegirLector(Lector lector) {
        if (lector.getId() == null || lector.getNom() == null || lector.getCognom() == null)
            return Response.status(400).entity("Falten paràmetres").build();

        sm.addLector(lector.getId(), lector.getNom(), lector.getCognom(),
                lector.getDni(), lector.getDataNeix(), lector.getOrigenNeix(), lector.getAdreca());
        return Response.status(201).entity(lector).build();
    }

    @GET
    @ApiOperation(value = "Obtenir tots els lectors", notes = "Retorna una llista amb tots els lectors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correcte", response = Lector.class, responseContainer = "List")
    })
    @Path("/lectors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenirLectors() {
        List<Lector> lectors = sm.getLectors();
        GenericEntity<Collection<Lector>> entity = new GenericEntity<Collection<Lector>>(lectors){};
        return Response.status(200).entity(entity).build();
    }



    @POST
    @ApiOperation(value = "Afegir un nou llibre al MAGATZEM", notes = "Crea un llibre al MAGATZEM")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Llibre creat", response = Llibre.class),
            @ApiResponse(code = 400, message = "Error de validació")
    })
    @Path("/llibres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response afegirLlibre(Llibre llibre) {
        if (llibre.getId() == null || llibre.getTitol() == null)
            return Response.status(400).entity("Falten paràmetres").build();

        sm.emmagatzemarLlibre(llibre);
        return Response.status(201).entity(llibre).build();
    }

    @POST
    @Path("/catalogar/{idLlibre}")
    @ApiOperation(value = "Catalogar un llibre existent", notes = "Afegeix el llibre del magatzem al cataleg")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response catalogarLlibre(@PathParam("idLlibre") String idLlibre) {
        if(sm.llibreCatalogat(idLlibre)){
            return Response.status(400).entity("El llibre ja està catalogat").build();
        }
        if (!sm.llibreExisteixAlMagatzem(idLlibre)) {
            return Response.status(404).entity("Llibre no trobat al magatzem").build();
        }
        sm.catalogarLlibre();
        return Response.status(200).entity("Llibre catalogat correctament").build();
    }



    @GET
    @ApiOperation(value = "Obtenir tots els llibres CATALOGATS", notes = "Retorna una llista amb tots els llibres CATALOGATS")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correcte", response = Llibre.class, responseContainer = "List")
    })
    @Path("/llibres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenirLlibres() {
        List<Llibre> llibres = sm.getLlibresCatalogats();
        GenericEntity<Collection<Llibre>> entity = new GenericEntity<Collection<Llibre>>(llibres){};
        return Response.status(200).entity(entity).build();
    }


    @POST
    @ApiOperation(value = "Afegir un nou préstec", notes = "Crea un préstec per un lector i un llibre")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Préstec creat", response = Prestec.class),
            @ApiResponse(code = 400, message = "Error de validació")
    })
    @Path("/prestecs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response afegirPrestec(Prestec prestec) {
        if (prestec.getId() == null || prestec.getIdLector() == null || prestec.getIdLlibre() == null)
            return Response.status(400).entity("Falten paràmetres").build();

        sm.addPrestec(prestec.getId(), prestec.getIdLector(), prestec.getIdLlibre(),
                prestec.getDataPrestec(), prestec.getDataFinal());
        return Response.status(201).entity(prestec).build();
    }

    @GET
    @ApiOperation(value = "Obtenir tots els préstecs d'un lector", notes = "Retorna tots els préstecs d'un lector")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correcte", response = Prestec.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No s'han trobat préstecs")
    })
    @Path("/prestecs/lector/{idLector}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenirPrestecsPerLector(@PathParam("idLector") String idLector) {
        List<Prestec> prestecs = sm.getPrestecPerLector(idLector);
        if (prestecs == null || prestecs.isEmpty())
            return Response.status(404).entity("No s'han trobat préstecs per aquest lector").build();

        GenericEntity<Collection<Prestec>> entity = new GenericEntity<Collection<Prestec>>(prestecs){};
        return Response.status(200).entity(entity).build();
    }

}

