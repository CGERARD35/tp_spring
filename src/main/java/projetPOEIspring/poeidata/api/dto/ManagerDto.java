package projetPOEIspring.poeidata.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPOEIspring.poeidata.models.Technician;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {

    private Integer id;

    private String lastname;

    private String firstname;

    private String phone;

    private String mobilePhone;

    private List<TechnicianDto> technicians;

}
