package uz.pdp.lebazar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lebazar.entity.Product;
import uz.pdp.lebazar.entity.User;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private ProductDto product;
    private int quantity;

    private UserDto user;
    private Timestamp createdAt;
    private UUID id;
}
