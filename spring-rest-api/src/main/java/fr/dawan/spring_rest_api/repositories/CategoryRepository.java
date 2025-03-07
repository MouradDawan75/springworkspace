package fr.dawan.spring_rest_api.repositories;

import fr.dawan.spring_rest_api.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByNameContaining(String name, Pageable page);
    long countByNameContaining(String name);
}
