package projetPOEIspring.poeidata.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import projetPOEIspring.poeidata.api.dto.TechnicianDtoForWorksite;
import projetPOEIspring.poeidata.api.dto.WorksiteDto;
import projetPOEIspring.poeidata.models.Worksite;

import java.util.ArrayList;
import java.util.List;


@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorksiteMapper {

    @Mapping(target = "adressId", source = "adress.id")
    WorksiteDto mapToDto(Worksite worksite);

    @Mapping(target = "technicians", expression = "java(getTechnicians(worksite))")
    default List<TechnicianDtoForWorksite> getTechnicians(Worksite worksite) {
        List<TechnicianDtoForWorksite> technicians = new ArrayList<>();
        if(worksite.getTechnicians() != null){
            technicians = worksite.getTechnicians().stream()
                    .map(technician -> new TechnicianDtoForWorksite(
                            technician.getId(),
                            technician.getFirstname(),
                            technician.getLastname(),
                            technician.getAge()
                    ))
                    .toList();
        }
        return technicians;
    }

    @Mapping(target = "adress.id", source = "adressId")
    Worksite mapToModel(WorksiteDto worksiteDto);

}
