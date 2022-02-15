/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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
package eu.openanalytics.phaedra.plateservice.api;

import eu.openanalytics.phaedra.plateservice.service.WellService;
import eu.openanalytics.phaedra.plateservice.dto.WellDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3131", maxAge = 3600)
@RestController
public class WellController {

    private WellService wellService;

    public WellController(WellService wellService) {
        this.wellService = wellService;
    }

    @PostMapping(value = "/well", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellDTO> createWell(@RequestBody WellDTO wellDTO) {
        WellDTO response = wellService.createWell(wellDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/well", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WellDTO> updateWell(@RequestBody WellDTO wellDTO) {
        WellDTO response = wellService.updateWell(wellDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
