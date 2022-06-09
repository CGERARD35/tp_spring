package projetPOEIspring.poeidata.api.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetPOEIspring.poeidata.api.dto.TechnicianDto;
import projetPOEIspring.poeidata.exceptions.NotAllowedToDeleteManagerException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.mappers.TechnicianMapper;
import projetPOEIspring.poeidata.models.Technician;
import projetPOEIspring.poeidata.services.TechnicianService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/technicians")
public class TechnicianApi {

    Logger log = LoggerFactory.getLogger(TechnicianApi.class);

    private final TechnicianMapper technicianMapper;

    private final TechnicianService technicianService;

    public TechnicianApi(TechnicianMapper technicianMapper, TechnicianService technicianService) {
        this.technicianMapper = technicianMapper;
        this.technicianService = technicianService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Return the list of all technicians")
    public ResponseEntity<List<TechnicianDto>> getAll() {
        log.info("Retrieving technicians");
        return ResponseEntity.ok(this.technicianService.getAll().stream().map(this.technicianMapper::mapTechnicianToTechnicianDto).toList());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Trying to retrieve a technician from the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return the technician found the given ID"),
            @ApiResponse(responseCode = "404", description = "No technician found the given ID")
    })
    public ResponseEntity<TechnicianDto> findById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(this.technicianMapper.mapTechnicianToTechnicianDto(this.technicianService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Create a technician")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<TechnicianDto> createTechnician(@RequestBody final TechnicianDto technicianDto) {
        log.debug("Attempting to create technician");

        TechnicianDto technicianDtoResponse =
                this.technicianMapper.mapTechnicianToTechnicianDto(
                        this.technicianService.createTechnician(
                                this.technicianMapper.mapTechnicianDtoToTechnician(technicianDto)));
        return ResponseEntity
                .created(URI.create("/v1/technicians/" + technicianDtoResponse.getId()))
                .body(technicianDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a technician for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the technician for the given ID"),
            @ApiResponse(responseCode = "404", description = "No technician found the given ID")
    })
    public ResponseEntity<Void> deleteTechnician(@PathVariable final Integer id) {
        log.debug("Attempting to delete a technician with id {}", id);
        try {
            this.technicianService.deleteTechnician(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        } catch (NotAllowedToDeleteManagerException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a technician")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<Technician> updateTechnician(@PathVariable final Integer id,
                                                       @RequestBody TechnicianDto technicianDto) {
        try {
            log.debug("Updating technician {}", technicianDto.getId());
            technicianDto.setId(id);
            this.technicianService.updateTechnician(technicianMapper.mapTechnicianDtoToTechnician(technicianDto));
            log.debug("Successfully updated technician {}", technicianDto.getId());
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }
}
