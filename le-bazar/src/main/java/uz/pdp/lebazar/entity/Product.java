package uz.pdp.lebazar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.lebazar.entity.template.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbsEntity {
    private String maker;
    private String name;
    private Double price;
    private byte [] avatar;

    public Product(String maker, String name, Double price, byte[] avatar) {
        this.maker = maker;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product",cascade = CascadeType.ALL)
    private List<Cart> carts;
}
