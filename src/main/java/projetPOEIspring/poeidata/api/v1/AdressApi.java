package projetPOEIspring.poeidata.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetPOEIspring.poeidata.api.dto.AdressDto;
import projetPOEIspring.poeidata.mappers.AdressMapper;
import projetPOEIspring.poeidata.services.AdressService;

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
    @Operation(summary = "Return the list of all customers order by lastname ascending")
    public ResponseEntity<List<AdressDto>> getAll(){
        return ResponseEntity.ok(
                this.adressService.getAll().stream()
                        .map(this.adressMapper::mapToDto)
                        .toList()
        );
    }
}
