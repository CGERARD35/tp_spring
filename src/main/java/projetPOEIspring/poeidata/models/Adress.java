package projetPOEIspring.poeidata.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "adress")
public class Adress {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String city;
    private String street;
    private String number;

}
