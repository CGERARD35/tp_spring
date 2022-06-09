package projetPOEIspring.poeidata.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPOEIspring.poeidata.models.Technician;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdressDto {

    private Integer id;

    private String city;

    private String street;

    private String number;

    private Integer technicianId;

}
