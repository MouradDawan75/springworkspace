package fr.dawan.spring_rest_api.repositories;

import fr.dawan.spring_rest_api.entities.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalarieRepository extends JpaRepository<Salarie, Long> {
}
