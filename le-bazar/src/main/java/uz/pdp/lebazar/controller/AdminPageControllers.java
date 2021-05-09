package uz.pdp.lebazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.lebazar.entity.Product;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.entity.enums.RoleName;
import uz.pdp.lebazar.repository.CartRepository;
import uz.pdp.lebazar.repository.ProductRepository;
import uz.pdp.lebazar.repository.RoleRepository;
import uz.pdp.lebazar.repository.UserRepository;
import uz.pdp.lebazar.secret.CurrentUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminPageControllers {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adminPage")
    public String adminPage(){
        return "admin_page";
    }

    @GetMapping("/addAdmin")
    public String addAdmin(){
        return "add_admin";
    }

    @GetMapping("/clients")
    public String clients(){
        return "clients";
    }

    @GetMapping("/deleteClient")
    public String deleteClient(@RequestParam UUID id, Model model) {
        try {
            cartRepository.deleteAllByUserId(id);
            userRepository.deleteById(id);
            return "/clients";
        } catch (Exception e) {
            model.addAttribute("result","Error in deleting!!");
            return "result_add";
        }
    }

    @GetMapping("/blockClient")
    public String blockClient(@RequestParam UUID id, Model model) {
        try {
            User user = userRepository.findById(id).orElseThrow(()->new IllegalStateException("User not found"));
            user.setAccountNonBlocked(!user.isAccountNonBlocked());
            userRepository.save(user);
            return "/clients";
        } catch (Exception e) {
            model.addAttribute("result","Error in Blocking!!");
            return "result_add";
        }
    }

    @PostMapping("/checkUsername")
    public String checkUsername(@RequestParam String email, Model model){
        Optional<User> user_email = userRepository.findByEmail(email);
        if (user_email.isPresent()) {
            User user = user_email.get();
            List<RoleName> roleNames = new ArrayList<>();
            roleNames.add(RoleName.ROLE_ADMIN);
            roleNames.add(RoleName.ROLE_CLIENT);
            user.setRoles(roleRepository.findAllByRoleNameIn(roleNames));
            userRepository.save(user);
            model.addAttribute("result","Roles of \""+email+"\" set as admin and client");
        } else {
            model.addAttribute("result","Not Found");
        }
        return "result_add";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(
                              @RequestParam String firstname,
                              @RequestParam String lastname,
                              @RequestParam String birth,
                              @RequestParam(required = false) String gender,
                              @RequestParam String phone,
                              @RequestParam String email) throws ParseException {
        Optional<User> optional = userRepository.findByEmail(email);
        Date date = null;
        if (birth!=null && !birth.equals("")) {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
            date.setTime(date.getTime()+18000000L);
        }
        if (optional.isPresent()) {
            User user = optional.get();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            if (date !=null)
                user.setBirthDate(date);
            user.setGender(gender);
            user.setPhone(phone);
            userRepository.save(user);
            return "direct";
        } else {
            return "result_add";
        }
    }

    @GetMapping("/showOrder")
    public String showOrder(){
        return "show_order";
    }

    @GetMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam UUID id, @RequestParam String path) {
        cartRepository.deleteById(id);
        return path;
    }

    @GetMapping("/addProduct")
    public String addProduct(){
        return "add_product";
    }

    @GetMapping("/editProduct")
    public String editProduct(@RequestParam UUID id,Model model){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product p = product.get();
            model.addAttribute("id",p.getId());
            model.addAttribute("maker",p.getMaker());
            model.addAttribute("name",p.getName());
            model.addAttribute("price",p.getPrice());
            model.addAttribute("desc",p.getDescription());
            model.addAttribute("avatar",p.getAvatar());
            return "edit_product";
        } else {
            model.addAttribute("result","Product is not found!");
            return "result_add";
        }
    }

    @GetMapping("/removeProduct")
    public String removeProduct(@RequestParam UUID id){
        productRepository.deleteById(id);
        return "admin_page";
    }
}
