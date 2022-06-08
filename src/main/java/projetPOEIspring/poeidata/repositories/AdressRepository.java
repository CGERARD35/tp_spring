package projetPOEIspring.poeidata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPOEIspring.poeidata.models.Adress;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Integer> {
}
