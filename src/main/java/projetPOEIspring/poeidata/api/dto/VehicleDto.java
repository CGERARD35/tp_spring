package projetPOEIspring.poeidata.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Integer id;
    private String plateNumber;
    private String brand;
    private String yearOfConstruction;
    private Integer technicianId;

}
