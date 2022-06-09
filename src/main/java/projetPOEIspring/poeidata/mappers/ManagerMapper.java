package projetPOEIspring.poeidata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.ManagerDto;
import projetPOEIspring.poeidata.api.dto.TechnicianDto;
import projetPOEIspring.poeidata.models.Adress;
import projetPOEIspring.poeidata.models.Manager;
import projetPOEIspring.poeidata.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {

//    @Mapping(target = "technicians", expression = "java(getTechnician(manager))")
    ManagerDto mapManagerToManagerDto(Manager manager);

    default List<TechnicianDto> getTechnicians(Manager manager) {
        List<TechnicianDto> technicians = new ArrayList<>();
        if (manager.getTechnicians() != null) {
            technicians = manager.getTechnicians().stream()
                    .map(technician -> new TechnicianDto(
                            technician.getId(),
                            technician.getLastname(),
                            technician.getFirstname(),
                            technician.getAge(),
                            null,
                            manager.getId(),
                            null,
                            technician.getWorksites().stream().map(Worksite::getId).toList()
                    ))
                    .toList();
        }
        return technicians;
    }

    Manager mapManagerDtoToManager (ManagerDto managerDto);


}
