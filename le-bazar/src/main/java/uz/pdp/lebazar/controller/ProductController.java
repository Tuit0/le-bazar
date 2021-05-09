package uz.pdp.lebazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.payload.ProductDto;
import uz.pdp.lebazar.service.ProductService;
import uz.pdp.lebazar.utils.ApplicationConstants;

import java.util.UUID;

@Controller
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService service;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/getProducts")
    @ResponseBody
    public HttpEntity<?> getProducts(@RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER)
                                             Integer page,
                                     @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE)
                                             Integer size) {
        return ResponseEntity.ok(service.getProducts(page, size));
    }

    @GetMapping("/search")
    @ResponseBody
    public HttpEntity<?> search(@RequestParam String search,
                                @RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER)
                                        Integer page,
                                @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE)
                                        Integer size) {
        return ResponseEntity.ok(service.searchProducts(search,page, size));
    }

    @GetMapping("/getProduct")
    @ResponseBody
    public HttpEntity<?> getProduct(@RequestParam UUID id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addOrEditProduct")
    public String addOrEditProduct(@RequestParam String maker,
                                   @RequestParam String name,
                                   @RequestParam String price,
                                   @RequestParam String desc,
                                   @RequestParam MultipartFile avatar,
                                   @RequestParam(required = false) UUID id,
                                   Model model) {
        ProductDto dto;
        try {
            if (id == null)
                dto = new ProductDto(maker,name,Double.parseDouble(price),avatar.getBytes(),desc);
            else
                dto = new ProductDto(id,maker,name,Double.parseDouble(price),avatar.getBytes(),desc);
        } catch (Exception e) {
            model.addAttribute("result","Something went wrong!");
            return "result_add";
        }
        ApiResponse apiResponse = service.addOrEditProduct(dto);
        if (apiResponse.isSuccess() && (apiResponse.getMessage().equals("Saved") || apiResponse.getMessage().equals("Edited")))
            return "admin_page";
        model.addAttribute("result","Something went wrong!");
        return "result_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteProduct/{productId}")
    public HttpEntity<?> deleteProduct(@PathVariable UUID productId) {
        ApiResponse apiResponse = service.deleteProduct(productId);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? 201 : 202 : 409).body(apiResponse);
    }
}
