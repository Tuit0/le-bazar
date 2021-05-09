package uz.pdp.lebazar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.lebazar.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("select p from Product p where lower(p.name) like %:search% or lower(p.maker) like %:search%")
    Page<Product> search(@Param("search") String search, Pageable pageable);

}
