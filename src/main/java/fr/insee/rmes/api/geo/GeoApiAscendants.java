package fr.insee.rmes.api.geo;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.insee.rmes.modeles.geo.territoire.Territoire;
import fr.insee.rmes.queries.geo.GeoQueries;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/geo")
@Tag(name = "geographie", description = "Geographie API")
public class GeoApiAscendants extends AbstractGeoAscendantsAndDescendantsApi {

    private static Logger logger = LogManager.getLogger(GeoApiAscendants.class);

    @Path("/commune/{code: [0-9][0-9AB][0-9]{3}}/ascendants")
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = "getcogcomasc",
        summary = "Récupérer les informations concernant les territoires qui contiennent la commune",
        description = "Cette requête renvoie également les communes des collectivités d'Outre-Mer",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = "Commune")
        })
    public Response getAscendantsFromCommune(
        @Parameter(
            description = "Code de la commune (cinq caractères)",
            required = true,
            schema = @Schema(pattern = "[0-9][0-9AB][0-9]{3}", type = "string")) @PathParam("code") String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = "Filtre pour renvoyer la commune active à la date donnée. Par défaut, c’est la date courante. ",
            required = false,
            schema = @Schema(type = "string", format = "date")) @QueryParam(value = "date") String date,
        @Parameter(
            description = "Filtre sur le type de territoire renvoyé.",
            required = false,
            schema = @Schema(type = "string")) @QueryParam(value = "type") String typeTerritoire) {

        logger.debug("Received GET request for ascendants of commune {}", code);

        if ( ! this.verifyParametersApiAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {
            return this
                .generateResponseListOfTerritoireForAscendantsOrDescendants(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getAscendantsCommune(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }

    @Path("/departement/{code: ([013-8][0-9])|(2[0-9AB])|(9[0-5])|(97[1-6])}/ascendants")
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = "getcogcdepasc",
        summary = "Récupérer les informations concernant les territoires qui contiennent le département",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = "Departement")
        })
    public Response getAscendantsFromDepartement(
        @Parameter(
            description = "Code du département (deux ou trois chiffres, ou 2A, 2B)",
            required = true,
            schema = @Schema(
                pattern = "([013-8][0-9])|(2[0-9AB])|(9[0-5])|(97[1-6])",
                type = "string")) @PathParam("code") String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = "Filtre pour renvoyer le département actif à la date donnée. Par défaut, c’est la date courante. ",
            required = false,
            schema = @Schema(type = "string", format = "date")) @QueryParam(value = "date") String date,
        @Parameter(
            description = "Filtre sur le type de territoire renvoyé.",
            required = false,
            schema = @Schema(type = "string")) @QueryParam(value = "type") String typeTerritoire) {

        logger.debug("Received GET request for ascendants of departement {}", code);

        if ( ! this.verifyParametersApiAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {

            return this
                .generateResponseListOfTerritoireForAscendantsOrDescendants(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getAscendantsDepartement(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }
    
    @Path("/communeDeleguee/{code: [0-9][0-9AB][0-9]{3}}/ascendants")
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = "getcogcomDelasc",
        summary = "Récupérer les informations concernant les territoires qui contiennent la commune déléguée",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = "Commune déléguée")
        })
    public Response getAscendantsFromCommuneDeleguee(
        @Parameter(
            description = "Code de la commune déléguée",
            required = true,
            schema = @Schema(
                pattern = "[0-9][0-9AB][0-9]{3}",
                type = "string")) @PathParam("code") String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = "Filtre pour renvoyer la commune déléguée active à la date donnée. Par défaut, c’est la date courante. ",
            required = false,
            schema = @Schema(type = "string", format = "date")) @QueryParam(value = "date") String date,
        @Parameter(
            description = "Filtre sur le type de territoire renvoyé.",
            required = false,
            schema = @Schema(type = "string")) @QueryParam(value = "type") String typeTerritoire) {

        logger.debug("Received GET request for ascendants of communeDeleguee {}", code);

        if ( ! this.verifyParametersApiAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {

            return this
                .generateResponseListOfTerritoireForAscendantsOrDescendants(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getAscendantsCommuneDeleguee(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }
    
    @Path("/communeAssociee/{code: [0-9][0-9AB][0-9]{3}}/ascendants")
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = "getcogcomassasc",
        summary = "Récupérer les informations concernant les territoires qui contiennent la commune associée",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = "Commune associée")
        })
    public Response getAscendantsFromCommuneAssociee(
        @Parameter(
            description = "Code de la commune associée",
            required = true,
            schema = @Schema(
                pattern = "[0-9][0-9AB][0-9]{3}",
                type = "string")) @PathParam("code") String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = "Filtre pour renvoyer la commune associée active à la date donnée. Par défaut, c’est la date courante. ",
            required = false,
            schema = @Schema(type = "string", format = "date")) @QueryParam(value = "date") String date,
        @Parameter(
            description = "Filtre sur le type de territoire renvoyé.",
            required = false,
            schema = @Schema(type = "string")) @QueryParam(value = "type") String typeTerritoire) {

        logger.debug("Received GET request for ascendants of communeAssociee {}", code);

        if ( ! this.verifyParametersApiAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {

            return this
                .generateResponseListOfTerritoireForAscendantsOrDescendants(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getAscendantsCommuneAssociee(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }
    
    @Path("/arrondissementMunicipal/{code}/ascendants")
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = "getcogarrmunasc",
        summary = "Récupérer les informations concernant les territoires qui contiennent l'arrondissement municipal",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = "Arrondissement Municipal")
        })
    public Response getAscendantsFromArrondissementMunicipal(
        @Parameter(
            description = "Code de l'arrondissement municipal",
            required = true,
            schema = @Schema(
                pattern = "(([013-8][0-9])|(2[0-9AB])|(9[0-5])|(97[1-6]))[0-9]",
                type = "string")) @PathParam("code") String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = "Filtre pour renvoyer l'arrondissement municipal actif à la date donnée. Par défaut, c’est la date courante. ",
            required = false,
            schema = @Schema(type = "string", format = "date")) @QueryParam(value = "date") String date,
        @Parameter(
            description = "Filtre sur le type de territoire renvoyé.",
            required = false,
            schema = @Schema(type = "string")) @QueryParam(value = "type") String typeTerritoire) {

        logger.debug("Received GET request for ascendants of arrondissement municipal {}", code);

        if ( ! this.verifyParametersApiAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {

            return this
                .generateResponseListOfTerritoireForAscendantsOrDescendants(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getAscendantsArrondissementMunicipal(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }

    @Path("/arrondissement/{code: (([013-8][0-9])|(2[0-9AB])|(9[0-5])|(97[1-6]))[0-9]}/ascendants")
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = "getcogarrasc",
        summary = "Récupérer les informations concernant les territoires qui contiennent l'arrondissement",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = "Arrondissement")
        })
    public Response getAscendantsFromArrondissement(
        @Parameter(
            description = "Code de l'arrondissement",
            required = true,
            schema = @Schema(
                pattern = "(([013-8][0-9])|(2[0-9AB])|(9[0-5])|(97[1-6]))[0-9]",
                type = "string")) @PathParam("code") String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = "Filtre pour renvoyer l'arrondissement actif à la date donnée. Par défaut, c’est la date courante. ",
            required = false,
            schema = @Schema(type = "string", format = "date")) @QueryParam(value = "date") String date,
        @Parameter(
            description = "Filtre sur le type de territoire renvoyé.",
            required = false,
            schema = @Schema(type = "string")) @QueryParam(value = "type") String typeTerritoire) {

        logger.debug("Received GET request for ascendants of arrondissement {}", code);

        if ( ! this.verifyParametersApiAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {

            return this
                .generateResponseListOfTerritoireForAscendantsOrDescendants(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getAscendantsArrondissement(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }
}
