/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.plateservice.client.impl;

import eu.openanalytics.phaedra.plateservice.client.PlateServiceGraphQLClient;
import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
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
  private static final int MAX_IN_MEMORY_SIZE = 10 * 1024 * 1024;

  public PlateServiceGraphQLClientImpl(IAuthorizationService authService, Environment environment) {
    String baseUrl = environment.getProperty(PROP_BASE_URL, DEFAULT_BASE_URL);
    this.authService = authService;
    this.webClient = WebClient.builder()
        .baseUrl(String.format("%s/graphql", baseUrl))
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE))
        .build();
  }

  @Override
  public WellDTO getWell(long wellId) {
    String document = """
       {
         getWellById(wellId: %d) {
           %s
         }
       }
       """.formatted(wellId, buildWellGraphQLDocumentBody());

    return httpGraphQlClient()
        .document(document)
        .retrieveSync("getWellById")
        .toEntity(WellDTO.class);
  }

  @Override
  public List<WellDTO> getWells(List<Long> wellIds) {
    String document = """
       {
         getWells(wellIds: %s) {
           %s
         }
       }
       """.formatted(wellIds, buildWellGraphQLDocumentBody());

    WellDTO[] results = httpGraphQlClient()
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
           %s
         }
       }
       """.formatted(plateId, buildWellGraphQLDocumentBody());

    WellDTO[] results = httpGraphQlClient()
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
              %s
            }
          }
       """.formatted(plateIds, buildWellGraphQLDocumentBody());

    WellDTO[] results = httpGraphQlClient()
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
            %s
          }
        }
       """.formatted(experimentId, buildWellGraphQLDocumentBody());

    WellDTO[] results = httpGraphQlClient()
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
           %s
         }
       }
       """.formatted(experimentIds, buildWellGraphQLDocumentBody());

    WellDTO[] results = httpGraphQlClient()
        .document(document)
        .retrieveSync("getWellsByExperimentIds")
        .toEntity(WellDTO[].class);

    return List.of(results);
  }

  @Override
  public List<PlateMeasurementDTO> getActivePlateMeasurements(List<Long> plateIds) {
    String document = """
          query($plateIds: [ID]) {
            getActiveMeasurementByPlateIds(plateIds: $plateIds)  {
              %s
            }
          }
        """.formatted(buildPlateMeasurementGraphQLDocumentBody());
    PlateMeasurementDTO[] results = httpGraphQlClient()
        .document(document)
        .variable("plateIds", plateIds)
        .retrieveSync("getActiveMeasurementByPlateIds")
        .toEntity(PlateMeasurementDTO[].class);
    return List.of(results);
  }

  private String buildWellGraphQLDocumentBody() {
    return """
            id
            plateId
            wellNr
            row
            column
            status
            description
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
            """;
  }

  private String buildPlateMeasurementGraphQLDocumentBody() {
    return """
        id
        measurementId
        plateId
        active
        linkedBy
        linkedOn
        """;
  }

  private HttpGraphQlClient httpGraphQlClient() {
    String bearerToken = authService.getCurrentBearerToken();
    return HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
  }
}
