package projetPOEIspring.poeidata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.VehicleDto;
import projetPOEIspring.poeidata.models.Vehicle;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleMapper {

    VehicleDto MapToDto(Vehicle vehicle);

    Vehicle MapToModel(VehicleDto vehicleDto);
}
