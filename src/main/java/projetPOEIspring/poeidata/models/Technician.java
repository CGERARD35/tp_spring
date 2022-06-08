package projetPOEIspring.poeidata.models;

import javax.persistence.*;

@Entity
@Table(name = "Technician")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstname;

}
