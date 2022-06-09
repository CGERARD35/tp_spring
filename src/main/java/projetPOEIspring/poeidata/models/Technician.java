package projetPOEIspring.poeidata.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Technician")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @OneToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(joinColumns={@JoinColumn(name="technician_id")},
            inverseJoinColumns = {@JoinColumn(name="worksite_id")})
    private Set<Worksite> worksites = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


}
