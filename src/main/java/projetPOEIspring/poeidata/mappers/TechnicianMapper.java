package projetPOEIspring.poeidata.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.TechnicianDto;
import projetPOEIspring.poeidata.models.Technician;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TechnicianMapper {

    @Mapping(source = "adress.id", target = "adressId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    TechnicianDto mapTechnicianToTechnicianDto(Technician technician);

    @Mapping(source = "adressId", target = "adress.id")
    @Mapping(source = "managerId", target = "manager.id")
    @Mapping(source = "vehicleId", target = "vehicle.id")
    Technician mapTechnicianDtoToTechnician(TechnicianDto technicianDto);

}
