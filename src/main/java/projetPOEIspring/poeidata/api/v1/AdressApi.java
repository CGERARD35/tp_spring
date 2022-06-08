package projetPOEIspring.poeidata.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetPOEIspring.poeidata.api.dto.AdressDto;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.mappers.AdressMapper;
import projetPOEIspring.poeidata.models.Adress;
import projetPOEIspring.poeidata.services.AdressService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/adresses")
public class AdressApi {

    private final AdressMapper adressMapper;
    private final AdressService adressService;


    public AdressApi(AdressMapper adressMapper, AdressService adressService) {
        this.adressMapper = adressMapper;
        this.adressService = adressService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get all adresses")
    public ResponseEntity<List<AdressDto>> getAll(){
        try{
            return ResponseEntity.ok(
                    this.adressService.getAll().stream()
                            .map(this.adressMapper::mapToDto)
                            .toList()
            );
        } catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get an adress by it's ID")
    public ResponseEntity<AdressDto> getById(@PathVariable final Integer id){
        try{
            return ResponseEntity.ok(this.adressMapper.mapToDto(adressService.getById(id)));
        } catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an adress by it's ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        try{
            this.adressService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create an adress")
    public ResponseEntity<AdressDto> create(@RequestBody AdressDto adressDto){
        try{
            AdressDto adressCreated = this.adressMapper.mapToDto(
                    this.adressService.create(this.adressMapper.mapToModel(adressDto))
            );

            return ResponseEntity.created(URI.create("/v1/adresses" + adressCreated.getId()))
                    .body(adressCreated);
        }catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update an adress")
    public ResponseEntity<AdressDto> update(
            @PathVariable final Integer id,
            @RequestBody AdressDto adressDto)
    {
        try{
            adressDto.setId(id);
            AdressDto adressToUpdate = this.adressMapper.mapToDto(this.adressService.update(this.adressMapper.mapToModel(adressDto)));
            return ResponseEntity.ok(adressToUpdate);
    }catch (UnknownResourceException ure){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
    }

    }
}
