package uz.pdp.lebazar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.lebazar.entity.Cart;
import uz.pdp.lebazar.entity.Product;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.payload.CartDto;
import uz.pdp.lebazar.payload.ProductDto;
import uz.pdp.lebazar.payload.UserDto;
import uz.pdp.lebazar.repository.CartRepository;
import uz.pdp.lebazar.repository.ProductRepository;
import uz.pdp.lebazar.utils.CommonUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public ApiResponse getUserProduct(UUID user_id, Integer page, Integer size) {
        Page<Cart> cartPage = cartRepository.search(user_id, CommonUtils.sortedByCreatedAt(page, size));

        return new ApiResponse(
                "Success",
                true,
                cartPage.getTotalElements(),
                cartPage.getTotalPages(),
                cartPage.getContent().stream().map(this::getCartDtoFromCart).collect(Collectors.toList()));
    }

    public ApiResponse addCartItem(UUID product_id, User currentUser, int quantity) {
        try {
            Optional<Product> product = productRepository.findById(product_id);
            if (product.isPresent()) {
                cartRepository.save(new Cart(currentUser, product.get(), quantity));
                return new ApiResponse("Added", true);
            }
            return new ApiResponse("Item is not found", false);
        } catch (Exception e) {
            return new ApiResponse("Error in add to cart", false);
        }
    }

    public ApiResponse deleteCartItem(UUID product_id, User user) {
        cartRepository.deleteByUserAndProductId(user,product_id);
        return new ApiResponse("Deleted",true);
    }

    public ApiResponse getOrders(Integer page, Integer size) {
        Page<Cart> allOrders = cartRepository.findAll(CommonUtils.sortedByCreatedAt(page,size));
        return new ApiResponse(
                "Sucess",
                true,
                allOrders.getTotalElements(),
                allOrders.getTotalPages(),
                allOrders.getContent().stream().map(this::getCartDtoFromCart).collect(Collectors.toList()));
    }

    private CartDto getCartDtoFromCart(Cart cart) {
        Product product = cart.getProduct();
        UserDto userDto = new UserDto();
        userDto.setId(cart.getUser().getId());
        userDto.setEmail(cart.getUser().getEmail());
        return new CartDto(
                new ProductDto(product.getId(),product.getMaker(),product.getName(),product.getPrice(), product.getAvatar(),product.getDescription()),
                cart.getQuantity(),
                userDto,
                cart.getCreatedAt(),
                cart.getId()
        );
    }
}
