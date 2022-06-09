package projetPOEIspring.poeidata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPOEIspring.poeidata.models.Technician;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
}
