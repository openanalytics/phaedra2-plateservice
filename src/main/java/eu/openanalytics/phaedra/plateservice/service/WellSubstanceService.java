package eu.openanalytics.phaedra.plateservice.service;

import eu.openanalytics.phaedra.plateservice.repository.WellSubstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class WellSubstanceService {

    private WellSubstanceRepository wellSubstanceRepository;

    public WellSubstanceService(WellSubstanceRepository wellSubstanceRepository) {
        this.wellSubstanceRepository = wellSubstanceRepository;
    }
}
