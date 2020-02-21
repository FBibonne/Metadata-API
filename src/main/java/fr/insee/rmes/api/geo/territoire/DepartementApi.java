package fr.insee.rmes.api.geo.territoire;

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

import fr.insee.rmes.api.geo.AbstractGeoApi;
import fr.insee.rmes.api.geo.ConstGeoApi;
import fr.insee.rmes.modeles.geo.territoire.Departement;
import fr.insee.rmes.modeles.geo.territoire.Territoire;
import fr.insee.rmes.queries.geo.GeoQueries;
import fr.insee.rmes.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path(ConstGeoApi.PATH_GEO)
@Tag(name = ConstGeoApi.TAG_NAME, description = ConstGeoApi.TAG_DESCRIPTION)
public class DepartementApi extends AbstractGeoApi {

    private static Logger logger = LogManager.getLogger(DepartementApi.class);

    private final String codePattern = "/{code: " + ConstGeoApi.PATTERN_DEPARTEMENT + "}";
    private final String litteralIdOperation = "getcogdep";
    private final String litteralOperationSummary =
        "Informations sur un departement français identifiée par son code (deux caractères)";
    private final String litteralResponseDescription = "Departement";
    private final String litteralParameterDateDescription =
        "Filtre pour renvoyer la departement active à la date donnée. Par défaut, c’est la date courante.";
    private final String litteralParameterTypeDescription = "Filtre sur le type de territoire renvoyé.";

    @Path(ConstGeoApi.PATH_DEPARTEMENT + codePattern)
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(operationId = litteralIdOperation, summary = litteralOperationSummary, responses = {
        @ApiResponse(
            content = @Content(schema = @Schema(implementation = Departement.class)),
            description = litteralResponseDescription)
    })
    public Response getDepartement(
        @Parameter(
            description = ConstGeoApi.PATTERN_DEPARTEMENT_DESCRIPTION,
            required = true,
            schema = @Schema(
                pattern = ConstGeoApi.PATTERN_DEPARTEMENT,
                type = Constants.TYPE_STRING)) @PathParam(Constants.CODE) String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = litteralParameterDateDescription,
            required = false,
            schema = @Schema(type = Constants.TYPE_STRING, format = Constants.FORMAT_DATE)) @QueryParam(
                value = Constants.PARAMETER_DATE) String date) {

        logger.debug("Received GET request for departement {}", code);

        if ( ! this.verifyParameterDateIsRight(date)) {
            return this.generateBadRequestResponse();
        }
        else {
            return this
                .generateResponseATerritoireByCode(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries.getDepartementByCodeAndDate(code, this.formatValidParameterDateIfIsNull(date))),
                    header,
                    new Departement(code));
        }
    }

    @Path(ConstGeoApi.PATH_DEPARTEMENT + codePattern + ConstGeoApi.PATH_ASCENDANT)
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = litteralIdOperation + ConstGeoApi.ID_OPERATION_ASCENDANTS,
        summary = "Récupérer les informations concernant les territoires qui contiennent la departement",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = litteralResponseDescription)
        })
    public Response getAscendantsFromDepartement(
        @Parameter(
            description = ConstGeoApi.PATTERN_DEPARTEMENT_DESCRIPTION,
            required = true,
            schema = @Schema(
                pattern = ConstGeoApi.PATTERN_DEPARTEMENT,
                type = Constants.TYPE_STRING)) @PathParam(Constants.CODE) String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = litteralParameterDateDescription,
            required = false,
            schema = @Schema(type = Constants.TYPE_STRING, format = Constants.FORMAT_DATE)) @QueryParam(
                value = Constants.PARAMETER_DATE) String date,
        @Parameter(
            description = litteralParameterTypeDescription,
            required = false,
            schema = @Schema(type = Constants.TYPE_STRING)) @QueryParam(
                value = Constants.PARAMETER_TYPE) String typeTerritoire) {

        logger.debug("Received GET request for ascendants of departement {}", code);

        if ( ! this.verifyParametersTypeAndDateAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {
            return this
                .generateResponseListOfTerritoire(
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

    @Path(ConstGeoApi.PATH_DEPARTEMENT + codePattern + ConstGeoApi.PATH_DESCENDANT)
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = litteralIdOperation + ConstGeoApi.ID_OPERATION_DESCENDANTS,
        summary = "Récupérer les informations concernant les territoires inclus dans le departement",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Territoire.class)),
                description = litteralResponseDescription)
        })
    public Response getDescendantsFromDepartement(
        @Parameter(
            description = ConstGeoApi.PATTERN_DEPARTEMENT_DESCRIPTION,
            required = true,
            schema = @Schema(
                pattern = ConstGeoApi.PATTERN_DEPARTEMENT,
                type = Constants.TYPE_STRING)) @PathParam(Constants.CODE) String code,
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = litteralParameterDateDescription,
            required = false,
            schema = @Schema(type = Constants.TYPE_STRING, format = Constants.FORMAT_DATE)) @QueryParam(
                value = Constants.PARAMETER_DATE) String date,
        @Parameter(
            description = litteralParameterTypeDescription,
            required = false,
            schema = @Schema(type = Constants.TYPE_STRING)) @QueryParam(
                value = Constants.PARAMETER_TYPE) String typeTerritoire) {

        logger.debug("Received GET request for descendants of departement {}", code);

        if ( ! this.verifyParametersTypeAndDateAreValid(typeTerritoire, date)) {
            return this.generateBadRequestResponse();
        }
        else {
            return this
                .generateResponseListOfTerritoire(
                    sparqlUtils
                        .executeSparqlQuery(
                            GeoQueries
                                .getDescendantsDepartement(
                                    code,
                                    this.formatValidParameterDateIfIsNull(date),
                                    this.formatValidParametertypeTerritoireIfIsNull(typeTerritoire))),
                    header);
        }
    }

    @Path(ConstGeoApi.PATH_LISTE_DEPARTEMENT)
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Operation(
        operationId = litteralIdOperation + ConstGeoApi.ID_OPERATION_LISTE,
        summary = "La requête renvoie toutes les departements actifs à la date donnée. Par défaut, c’est la date courante.",
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(type = ARRAY, implementation = Departement.class)),
                description = litteralResponseDescription)
        })
    public Response getListeDepartements(
        @Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header,
        @Parameter(
            description = litteralParameterDateDescription,
            required = false,
            schema = @Schema(type = Constants.TYPE_STRING, format = Constants.FORMAT_DATE)) @QueryParam(
                value = Constants.PARAMETER_DATE) String date) {

        logger.debug("Received GET request for all departements");

        if ( ! this.verifyParameterDateIsRight(date)) {
            return this.generateBadRequestResponse();
        }
        else {

            return this
                .generateResponseListOfTerritoire(
                    sparqlUtils.executeSparqlQuery(GeoQueries.getListDept(this.formatValidParameterDateIfIsNull(date))),
                    header);
        }
    }
}
