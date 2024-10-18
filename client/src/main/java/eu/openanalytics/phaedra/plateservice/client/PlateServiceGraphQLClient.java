package eu.openanalytics.phaedra.plateservice.client;

import eu.openanalytics.phaedra.plateservice.dto.PlateMeasurementDTO;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import java.util.List;

public interface PlateServiceGraphQLClient {
  public WellDTO getWell(long wellId);
  public List<WellDTO> getWells(List<Long> wellIds);
  public List<WellDTO> getWellsByPlateId(long plateId);
  public List<WellDTO> getWellsByPlateIds(List<Long> plateIds);
  public List<WellDTO> getWellsByExperimentId(long experimentId);
  public List<WellDTO> getWellsByExperimentIds(List<Long> experimentIds);

  public List<PlateMeasurementDTO> getActivePlateMeasurements(List<Long> plateIds);
}
