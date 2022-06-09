package projetPOEIspring.poeidata.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetPOEIspring.poeidata.api.dto.WorksiteDto;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.mappers.WorksiteMapper;
import projetPOEIspring.poeidata.services.WorksiteService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/worksites")
public class WorksiteApi {

    private final WorksiteMapper worksiteMapper;
    private final WorksiteService worksiteService;


    public WorksiteApi(WorksiteMapper worksiteMapper, WorksiteService worksiteService) {
        this.worksiteMapper = worksiteMapper;
        this.worksiteService = worksiteService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get all worksites")
    public ResponseEntity<List<WorksiteDto>> getAll(){
        try{
            return ResponseEntity.ok(
                    this.worksiteService.getAll().stream()
                            .map(this.worksiteMapper::mapToDto)
                            .toList()
            );
        } catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get an adress by it's ID")
    public ResponseEntity<WorksiteDto> getById(@PathVariable final Integer id){
        try{
            return ResponseEntity.ok(this.worksiteMapper.mapToDto(worksiteService.getBydId(id)));
        } catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an adress by it's ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        try{
            this.worksiteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create an adress")
    public ResponseEntity<WorksiteDto> create(@RequestBody final WorksiteDto WorksiteDto){
        try{
            WorksiteDto adressCreated = this.worksiteMapper.mapToDto(
                    this.worksiteService.create(this.worksiteMapper.mapToModel(WorksiteDto))
            );

            return ResponseEntity.created(URI.create("/v1/worksites" + adressCreated.getId()))
                    .body(adressCreated);
        }catch (UnknownResourceException ure){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update an adress")
    public ResponseEntity<WorksiteDto> update(
            @PathVariable final Integer id,
            @RequestBody WorksiteDto WorksiteDto)
    {
        try{
            WorksiteDto.setId(id);
            WorksiteDto adressToUpdate = this.worksiteMapper.mapToDto(this.worksiteService.update(this.worksiteMapper.mapToModel(WorksiteDto)));
            return ResponseEntity.ok(adressToUpdate);
    }catch (UnknownResourceException ure){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
    }

    }
}
