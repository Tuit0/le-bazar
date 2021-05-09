package uz.pdp.lebazar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.lebazar.entity.Product;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.payload.ProductDto;
import uz.pdp.lebazar.repository.ProductRepository;
import uz.pdp.lebazar.utils.CommonUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ApiResponse addOrEditProduct(ProductDto dto) {
        try {
            Product product = new Product();
            if (dto.getId() != null) {
                product = productRepository.findById(dto.getId()).orElseThrow(() -> new IllegalStateException("Product not found"));
            }
            product.setPrice(dto.getPrice());
            product.setMaker(dto.getMaker());
            product.setName(dto.getName());
            product.setAvatar(dto.getAvatar());
            product.setDescription(dto.getDesc());

            productRepository.save(product);
            return new ApiResponse(dto.getId()!=null?"Edited":"Saved", true,null,null,product);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse deleteProduct(UUID productId) {
        try {
            productRepository.deleteById(productId);
            return new ApiResponse("Deleted",true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse getProducts(Integer page, Integer size) {
        Page<Product> productPage = productRepository.findAll(CommonUtils.sortedByCreatedAt(page,size));
        return new ApiResponse("Success",
                true,
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.getContent().stream().map(this::getProductDtoFromProduct).collect(Collectors.toList()));
    }

    public ProductDto getProductDtoFromProduct(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setMaker(product.getMaker());
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        dto.setAvatar(product.getAvatar());

        return dto;
    }

    public ApiResponse getProduct(UUID id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            return product.map(value -> new ApiResponse("Success", true, null, null, value)).orElseGet(() -> new ApiResponse("Product is not found", false));
        } catch (Exception e) {
            return new ApiResponse("Error",false);
        }
    }

    public ApiResponse searchProducts(String search, Integer page, Integer size) {
        Page<Product> productPage = productRepository.search(search.toLowerCase(),CommonUtils.simplePageable(page,size));
        return new ApiResponse("Success",
                true,
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.getContent().stream().map(this::getProductDtoFromProduct).collect(Collectors.toList()));

    }
}
