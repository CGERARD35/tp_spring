package projetPOEIspring.poeidata.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity()
@Table(name = "adress")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(length = 5, nullable = false)
    private String number;
}
