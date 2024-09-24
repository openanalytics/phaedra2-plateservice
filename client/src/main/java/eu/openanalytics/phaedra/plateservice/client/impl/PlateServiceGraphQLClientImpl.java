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
    WellDTO result = httpGraphQlClient
        .document(document)
        .retrieveSync("metadata")
        .toEntity(WellDTO.class);

    return result;
  }

  @Override
  public List<WellDTO> getWells(List<Long> wellIds) {
    return List.of();
  }

  @Override
  public List<WellDTO> getWellsByPlateId(long plateId) {
    return List.of();
  }

  @Override
  public List<WellDTO> getWellsByPlateIds(List<Long> plateIds) {
    return List.of();
  }

  @Override
  public List<WellDTO> getWellsByExperimentId(long experimentId) {
    return List.of();
  }

  @Override
  public List<WellDTO> getWellsByExperimentIds(List<Long> experimentIds) {
    return List.of();
  }

  private HttpGraphQlClient httpGraphQlClient() {
    String bearerToken = authService.getCurrentBearerToken();
    return HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
  }
}
