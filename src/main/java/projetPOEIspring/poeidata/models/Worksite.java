package projetPOEIspring.poeidata.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "worksite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worksite {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @OneToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @ManyToMany(mappedBy = "worksites")
    List<Technician> technicians;

}
