package eu.openanalytics.phaedra.plateservice.client.impl;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceGraphQLClient;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PlateServiceGraphQLClientImpl implements PlateServiceGraphQLClient {

  private final IAuthorizationService authService;
  private final WebClient webClient;
  private static final String PROP_BASE_URL = "phaedra.plate-service.base-url";
  private static final String DEFAULT_BASE_URL = "http://phaedra-plate-service:8080/phaedra/plate-service";

  public PlateServiceGraphQLClientImpl(IAuthorizationService authService, Environment environment) {
    String baseUrl = environment.getProperty(PROP_BASE_URL, DEFAULT_BASE_URL);
    this.authService = authService;
    this.webClient = WebClient.builder()
        .baseUrl(String.format("%s/graphql", baseUrl))
        .build();
  }

  @Override
  public WellDTO getWell(long wellId) {
    String document = """
       {
         getWellById(wellId: %d) {
           wellNr
           wellType
           wellSubstance {
             name
             concentration
           }
           tags
           properties {
             propertyName
             propertyValue
           }
         }
       }
       """.formatted(wellId);

    String bearerToken = authService.getCurrentBearerToken();
    HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
    return httpGraphQlClient
        .document(document)
        .retrieveSync("getWellById")
        .toEntity(WellDTO.class);
  }

  @Override
  public List<WellDTO> getWells(List<Long> wellIds) {
    String document = """
       {
         getWells(wellIds: %s) {
           wellNr
           wellType
           wellSubstance {
             name
             concentration
           }
           tags
           properties {
             propertyName
             propertyValue
           }
         }
       }
       """.formatted(wellIds);

    String bearerToken = authService.getCurrentBearerToken();
    HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
    WellDTO[] results = httpGraphQlClient
        .document(document)
        .retrieveSync("getWells")
        .toEntity(WellDTO[].class);

    return List.of(results);
  }

  @Override
  public List<WellDTO> getWellsByPlateId(long plateId) {
    String document = """
       {
         getPlateWells(plateId: %d) {
           wellNr
           wellType
           wellSubstance {
             name
             concentration
           }
           tags
           properties {
             propertyName
             propertyValue
           }
         }
       }
       """.formatted(plateId);

    String bearerToken = authService.getCurrentBearerToken();
    HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
    WellDTO[] results = httpGraphQlClient
        .document(document)
        .retrieveSync("getPlateWells")
        .toEntity(WellDTO[].class);

    return List.of(results);
  }

  @Override
  public List<WellDTO> getWellsByPlateIds(List<Long> plateIds) {
    String document = """
       {
         getWellsByPlateIds(plateIds: %s) {
           wellNr
           wellType
           wellSubstance {
             name
             concentration
           }
           tags
           properties {
             propertyName
             propertyValue
           }
         }
       }
       """.formatted(plateIds);

    String bearerToken = authService.getCurrentBearerToken();
    HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
    WellDTO[] results = httpGraphQlClient
        .document(document)
        .retrieveSync("getWellsByPlateIds")
        .toEntity(WellDTO[].class);

    return List.of(results);
  }

  @Override
  public List<WellDTO> getWellsByExperimentId(long experimentId) {
    String document = """
       {
         getWellsByExperimentId(experimentId: %d) {
           wellNr
           wellType
           wellSubstance {
             name
             concentration
           }
           tags
           properties {
             propertyName
             propertyValue
           }
         }
       }
       """.formatted(experimentId);

    String bearerToken = authService.getCurrentBearerToken();
    HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
    WellDTO[] results = httpGraphQlClient
        .document(document)
        .retrieveSync("getWellsByExperimentId")
        .toEntity(WellDTO[].class);

    return List.of(results);
  }

  @Override
  public List<WellDTO> getWellsByExperimentIds(List<Long> experimentIds) {
    String document = """
       {
         getWellsByExperimentIds(experimentIds: %s) {
           wellNr
           wellType
           wellSubstance {
             name
             concentration
           }
           tags
           properties {
             propertyName
             propertyValue
           }
         }
       }
       """.formatted(experimentIds);

    String bearerToken = authService.getCurrentBearerToken();
    HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
    WellDTO[] results = httpGraphQlClient
        .document(document)
        .retrieveSync("getWellsByExperimentId")
        .toEntity(WellDTO[].class);

    return List.of(results);
  }

  private HttpGraphQlClient httpGraphQlClient() {
    String bearerToken = authService.getCurrentBearerToken();
    return HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
  }
}
