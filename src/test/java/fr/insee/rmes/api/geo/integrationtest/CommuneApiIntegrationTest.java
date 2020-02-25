package fr.insee.rmes.api.geo.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.insee.rmes.api.geo.territoire.CommuneApi;
import fr.insee.rmes.utils.SparqlUtils;

public class CommuneApiIntegrationTest {

    @InjectMocks
    private CommuneApi geoApi;

    @Mock
    protected SparqlUtils mockSparqlUtils;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givengetCommune_whenCorrectRequest_With_JSON_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_GET);
        Response response = geoApi.getCommune("01002", MediaType.APPLICATION_JSON, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_GET_JSON, response.getEntity());
    }

    @Test
    public void givengetCommune_whenCorrectRequest_With_XML_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_GET);
        Response response = geoApi.getCommune("01002", MediaType.APPLICATION_XML, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_GET_XML, response.getEntity());
    }

    @Test
    public void givengetListeCommunes_whenCorrectRequest_With_JSON_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_LISTE);
        Response response = geoApi.getListeCommunes(MediaType.APPLICATION_JSON, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_LISTE_TOP_JSON, response.getEntity());
    }

    @Test
    public void givengetListeCommunes_whenCorrectRequest_With_XML_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_LISTE);
        Response response = geoApi.getListeCommunes(MediaType.APPLICATION_XML, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_LISTE_TOP_XML, response.getEntity());
    }

    @Test
    public void givengetAscendantsCommunes_whenCorrectRequest_With_JSON_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_ASCENDANTS);
        Response response = geoApi.getAscendantsFromCommune("01002", MediaType.APPLICATION_JSON, null, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_ASCENDANTS_JSON, response.getEntity());
    }

    @Test
    public void givengetAscendantsCommunes_whenCorrectRequest_With_XML_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_ASCENDANTS);
        Response response = geoApi.getAscendantsFromCommune("01002", MediaType.APPLICATION_XML, null, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_ASCENDANTS_XML, response.getEntity());
    }

    @Test
    public void givengetDescendantsCommunes_whenCorrectRequest_With_JSON_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_DESCENDANTS);
        Response response = geoApi.getDescendantsFromCommune("01015", MediaType.APPLICATION_JSON, null, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_DESCENDANTS_JSON, response.getEntity());
    }

    @Test
    public void givengetDescendantsCommunes_whenCorrectRequest_With_XML_Header_thenResponseIsOk() {
        when(mockSparqlUtils.executeSparqlQuery(anyString()))
            .thenReturn(ConstantIntegrationTest.COMMUNE_MOCK_SERVER_RETURN_DESCENDANTS);
        Response response = geoApi.getDescendantsFromCommune("01015", MediaType.APPLICATION_XML, null, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ConstantIntegrationTest.COMMUNE_EXPECTED_RESPONSE_DESCENDANTS_XML, response.getEntity());
    }

}
