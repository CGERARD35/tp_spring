package projetPOEIspring.poeidata.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianDto {

    private Integer id;

    private String lastname;

    private String firstname;

    private Integer age;

    private Integer adressId;

    private Integer managerId;

    private Integer vehicleId;

    private List<Integer> worksitesId;

}
