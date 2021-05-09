package uz.pdp.lebazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.secret.CurrentUser;
import uz.pdp.lebazar.service.UserService;
import uz.pdp.lebazar.utils.ApplicationConstants;


@RestController
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/getUser")
    public HttpEntity<?> getUserInfo(@CurrentUser User user) {
        if (user == null) {
            return ResponseEntity.ok(new ApiResponse("Success", true, null, null, "/homePage"));
        } else if (!(user.isCredentialsNonExpired() && user.isCredentialNonExpired() && user.isAccountNonExpired() &&
                user.isEnabled() && user.isAccountNonBlocked() && user.isAccountNonLocked())) {
            return ResponseEntity.ok(new ApiResponse("Success", true, null, null, "/loginPage"));
        } else if (user.getRoles().size() == 2) {
            return ResponseEntity.ok(new ApiResponse("Success", true, null, null, "/adminPage"));
        } else if (user.getRoles().size() == 1)
            return ResponseEntity.ok(new ApiResponse("Success", true, null, null, "/userPage"));
        else return ResponseEntity.ok(new ApiResponse("Success", true, null, null, "/loginPage"));
    }

    @GetMapping("/getClients")
    public HttpEntity<?> getClients(@RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER)
                                            Integer page,
                                    @RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE)
                                            Integer size) {
        return ResponseEntity.ok(service.getUsers(page, size));
    }

    @GetMapping("/userProfile")
    public HttpEntity<?> userProfile(@CurrentUser User user) {
        return ResponseEntity.ok(user);
    }
}
