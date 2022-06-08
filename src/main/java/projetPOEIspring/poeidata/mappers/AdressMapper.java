package projetPOEIspring.poeidata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.AdressDto;
import projetPOEIspring.poeidata.models.Adress;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdressMapper {

    AdressDto mapToDto(Adress adress);

    Adress mapToModel(AdressDto adressDto);

}
