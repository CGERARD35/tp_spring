package projetPOEIspring.poeidata.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.TechnicianDto;
import projetPOEIspring.poeidata.api.dto.TechnicianDtoForWorksite;
import projetPOEIspring.poeidata.models.Manager;
import projetPOEIspring.poeidata.models.Technician;
import projetPOEIspring.poeidata.models.Worksite;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TechnicianMapper {

    @Mapping(source = "adress.id", target = "adressId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(target = "worksitesId", expression = "java(getWorksites(technician))")
    TechnicianDto mapTechnicianToTechnicianDto(Technician technician);

    default List<Integer> getWorksites(Technician technician) {
        List<Integer> worksitesId = new ArrayList<>();
        if (technician.getWorksites() != null) {
            worksitesId = technician.getWorksites().stream().map(Worksite::getId).toList();
        }
        return worksitesId;
    }

    @Mapping(source = "adressId", target = "adress.id")
    @Mapping(source = "managerId", target = "manager.id")
    @Mapping(source = "vehicleId", target = "vehicle.id")
    Technician mapTechnicianDtoToTechnician(TechnicianDto technicianDto);

    TechnicianDtoForWorksite mapToDto(Technician technician);

    Technician mapToModel(TechnicianDtoForWorksite technicianDtoForWorksite);

}
