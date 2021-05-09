package uz.pdp.lebazar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.lebazar.entity.Cart;
import uz.pdp.lebazar.entity.User;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("select c from Cart c where c.user.id=?1")
    Page<Cart> search(UUID user_id, Pageable pageable);

    void deleteByUserAndProductId(User user, UUID product_id);
    @Transactional
    void deleteAllByUserId(UUID user_id);
}
