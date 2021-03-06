package projetPOEIspring.poeidata.api.v1;

import com.sun.source.tree.TryTree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetPOEIspring.poeidata.api.dto.VehicleDto;
import projetPOEIspring.poeidata.exceptions.CantCreateException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.mappers.VehicleMapper;
import projetPOEIspring.poeidata.services.VehicleService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleApi {
    private final VehicleService vehicleService;

    private final VehicleMapper vehicleMapper;

    public VehicleApi(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Return the list of all customers ordered by ID ascending.")
    public ResponseEntity<List<VehicleDto>> getAll() {
        return ResponseEntity.ok(
                this.vehicleService.getAll().stream()
                        .map(vehicle -> this.vehicleMapper.mapToDto(vehicle))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Return a vehicle from the given ID", responses = @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehicleDto.class)))))
    public ResponseEntity<VehicleDto> getById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(vehicleMapper.mapToDto(vehicleService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}
    )
    @Operation(summary = "Create a vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "403", description = "Can't create the vehicle")
    })
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody final VehicleDto vehicleDto) {
        try {
            VehicleDto vehicleDtoResponse =
                    this.vehicleMapper.mapToDto(
                            this.vehicleService.createVehicle(
                                    this.vehicleMapper.mapToModel(vehicleDto)
                            ));

            return ResponseEntity
                    .created(URI.create("/v1/vehicles/" + vehicleDtoResponse.getId()))
                    .body(vehicleDtoResponse);
        } catch (CantCreateException cce) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, cce.getMessage());
        }

    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a vehicle from the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No vehicle found the given ID"),
    })
    public ResponseEntity<Void> deleteVehicle(@PathVariable final Integer id) {
        try {
            this.vehicleService.deleteVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No vehicle found the given ID"),

    })
    public ResponseEntity<Void> updateVehicle(@PathVariable final Integer id, @RequestBody VehicleDto vehicleDto) {
        try {
            vehicleDto.setId(id);
            this.vehicleService.updateVehicle(vehicleMapper.mapToModel(vehicleDto));
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }
}