package uz.pdp.lebazar.entity;

import lombok.*;
import uz.pdp.lebazar.entity.template.AbsEntity;

import javax.persistence.*;
import javax.transaction.Transactional;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class Cart extends AbsEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private int quantity;
}
