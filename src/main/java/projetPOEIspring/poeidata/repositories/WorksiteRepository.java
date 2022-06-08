package projetPOEIspring.poeidata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projetPOEIspring.poeidata.models.Worksite;

public interface WorksiteRepository extends JpaRepository<Worksite, Integer> {
}
