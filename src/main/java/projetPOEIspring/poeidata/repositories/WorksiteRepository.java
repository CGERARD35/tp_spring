package projetPOEIspring.poeidata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPOEIspring.poeidata.models.Worksite;

@Repository
public interface WorksiteRepository extends JpaRepository<Worksite, Integer> {
}
