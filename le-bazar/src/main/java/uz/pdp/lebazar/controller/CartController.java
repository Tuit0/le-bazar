package uz.pdp.lebazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.secret.CurrentUser;
import uz.pdp.lebazar.service.CartService;
import uz.pdp.lebazar.utils.ApplicationConstants;

import java.util.UUID;

@Controller
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/getCartItems")
    @ResponseBody
    public HttpEntity<?> getCartItems(@AuthenticationPrincipal User user,
                               @RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER)
                                       Integer page,
                               @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE)
                                           Integer size
                               ) {
        if (user != null)
            return ResponseEntity.ok(cartService.getUserProduct(user.getId(),page,size));
        return ResponseEntity.ok("Please Login");
    }

    @PostMapping("/addCartItem")
    public String addCartItem(@RequestParam UUID product_id, @CurrentUser User user, @RequestParam int quantity,
                              Model model) {
        if (user != null) {
            if (cartService.addCartItem(product_id, user, quantity).getMessage().equals("Added"))
            model.addAttribute("result","Added");
            else
            model.addAttribute("result","Something went wrong! Please try again...");
            return "result_add";
        }
        return "login";
    }

    @DeleteMapping("/deleteCartItem")
    public HttpEntity<?> deleteCartItem(@RequestParam UUID product_id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.deleteCartItem(product_id,user));
    }

    @GetMapping("/orders")
    @ResponseBody
    public HttpEntity<?> getOrders(@RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER)
                                               Integer page,
                                   @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE)
                                               Integer size) {
        return ResponseEntity.ok(cartService.getOrders(page,size));
    }

}
