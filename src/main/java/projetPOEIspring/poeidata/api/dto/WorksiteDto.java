package projetPOEIspring.poeidata.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorksiteDto {

    private Integer id;
    private String name;
    private Integer price;
    private Integer adressId;
    private List<TechnicianDtoForWorksite> technicians;

}
