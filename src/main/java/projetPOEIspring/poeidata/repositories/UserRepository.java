package projetPOEIspring.poeidata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetPOEIspring.poeidata.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}