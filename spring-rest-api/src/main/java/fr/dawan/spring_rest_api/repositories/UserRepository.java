package fr.dawan.spring_rest_api.repositories;

import fr.dawan.spring_rest_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
