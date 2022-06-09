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
import projetPOEIspring.poeidata.api.dto.ManagerDto;
import projetPOEIspring.poeidata.exceptions.NotAllowedToDeleteManagerException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.mappers.ManagerMapper;
import projetPOEIspring.poeidata.models.Manager;
import projetPOEIspring.poeidata.services.ManagerService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/managers")
public class ManagerApi {

    Logger log = LoggerFactory.getLogger(ManagerApi.class);

    private final ManagerMapper managerMapper;

    private final ManagerService managerService;

    public ManagerApi(ManagerMapper managerMapper, ManagerService managerService) {
        this.managerMapper = managerMapper;
        this.managerService = managerService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Return the list of all managers ordered by lastname ascending.")
    public ResponseEntity<List<ManagerDto>> getAll() {
        log.info("Retrieving managers.");

        return ResponseEntity.ok(
                this.managerService.getAll().stream().map(this.managerMapper::mapManagerToManagerDto).toList());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Trying to retrieve a manager from the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return the manager found the given ID"),
            @ApiResponse(responseCode = "404", description = "No manager found the given ID")
    })
    public ResponseEntity<ManagerDto> findById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(this.managerMapper.mapManagerToManagerDto(this.managerService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "create a manager")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<ManagerDto> createManager(@RequestBody final ManagerDto managerDto) {
        log.debug("Attempting to create manager with lastname {}", managerDto.getLastname());
        ManagerDto managerDtoResponse = this.managerMapper.mapManagerToManagerDto(
                this.managerService.createManager(
                        this.managerMapper.mapManagerDtoToManager(managerDto)));
        return ResponseEntity
                .created(URI.create("/v1/managers" + managerDtoResponse.getId()))
                .body(managerDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a manager for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the manager for the given ID"),
            @ApiResponse(responseCode = "404", description = "No manager found the given ID")
    })
    public ResponseEntity<Void> deleteManager (@PathVariable final Integer id) {
        log.debug("Attempting to delete a manager with id {}", id);
        try {
            this.managerService.deleteManager(id);
            return ResponseEntity.noContent().build();
        }catch(UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }catch(NotAllowedToDeleteManagerException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a manager")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
    })
    public ResponseEntity<Manager> updateManager(@PathVariable final Integer id,
                                                 @RequestBody ManagerDto managerDto){
        try {
            log.debug("Updating manager {}", managerDto.getId());
            managerDto.setId(id);
            this.managerService.updateManager(managerMapper.mapManagerDtoToManager(managerDto));
            log.debug("Successfully updated manager {}", managerDto.getId());
            return ResponseEntity.noContent().build();
        }catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

}
