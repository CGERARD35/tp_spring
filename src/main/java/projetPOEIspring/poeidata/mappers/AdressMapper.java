package projetPOEIspring.poeidata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.AdressDto;
import projetPOEIspring.poeidata.api.dto.TechnicianDto;
import projetPOEIspring.poeidata.models.Adress;
import projetPOEIspring.poeidata.models.Manager;
import projetPOEIspring.poeidata.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdressMapper {

    AdressDto mapToDto(Adress adress);

    Adress mapToModel(AdressDto adressDto);

}
