package fr.dawan.spring_rest_api.repositories;

import fr.dawan.spring_rest_api.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //lien doc finders methods de Spring Data
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    Product findByDescription(String key);
    List<Product> findByPriceBetween(double priceMin, double priceMax);
    List<Product> findAllByDescriptionLike(String description);
    List<Product> findByDescriptionAndPrice(String description, double price);
    List<Product> findByIdIn(List<Long> ids);

    //Pagination

    Page<Product> findByDescriptionContaining(String description, Pageable page);
    long countByDescriptionContaining(String description);

    //ManyToOne
    /*
    Les commandes JP-QL pointes vers les classes
     */
    @Query("From Product p where p.category.id =:id")
    List<Product> findAllByCategoryId(@Param("id") long id);

    @Query(nativeQuery = true, value = "Select * From product p Inner Join category c ON (p.category_id = c.id) where  c.id =:id")
    List<Product> findAllSqlCategoryId(@Param("id") long id);
}
