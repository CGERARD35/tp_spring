package projetPOEIspring.poeidata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPOEIspring.poeidata.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
