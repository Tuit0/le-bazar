package uz.pdp.lebazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.lebazar.entity.Product;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.entity.enums.RoleName;
import uz.pdp.lebazar.exceptions.MyRuntimeException;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.repository.ProductRepository;
import uz.pdp.lebazar.repository.RoleRepository;
import uz.pdp.lebazar.repository.UserRepository;

import java.util.*;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Controller
public class ApplicationController {
    @GetMapping("/")
    public String direct() {
        return "direct";
    }

    @GetMapping("/homePage")
    public String goHome() {
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "email";
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/saveUser")
    @ResponseBody
    public ApiResponse saveUser(@RequestParam String email,
                                @RequestParam String password) {
        try {
            User user = new User(email,passwordEncoder.encode(password));
            List<RoleName> roleNames = new ArrayList<>();
            roleNames.add(RoleName.ROLE_CLIENT);
            user.setRoles(roleRepository.findAllByRoleNameIn(roleNames));
            userRepository.save(user);
            return new ApiResponse("Saved",true);
        } catch (MyRuntimeException e) {
            return new ApiResponse("Error",false);
        }
    }

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/oneProduct")
    public String oneProduct(@RequestParam UUID id, @RequestParam(required = false) Integer admin, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product one = product.get();
            model.addAttribute("maker",one.getMaker());
            model.addAttribute("price",one.getPrice());
            model.addAttribute("name",one.getName());
            model.addAttribute("avatar",encodeBase64String(one.getAvatar()));
            if (one.getDescription()==null) one.setDescription("Not available");
            model.addAttribute("description",one.getDescription());
            model.addAttribute("id",id);
        }
        if (admin == null)
        return "one_product";
        return "one_product_admin";
    }

//    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/userPage")
    public String userPage() {
        return "user_page";
    }
    @GetMapping("/search")
    public String search(@RequestParam String search, Model model) {
        model.addAttribute("search",search);
        return "search";
    }
}
