package uz.pdp.lebazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.entity.enums.RoleName;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.payload.JwtResponse;
import uz.pdp.lebazar.payload.UserDto;
import uz.pdp.lebazar.repository.UserRepository;
import uz.pdp.lebazar.secret.JwtTokenProvider;
import uz.pdp.lebazar.service.AuthService;
import uz.pdp.lebazar.service.MailService;
import uz.pdp.lebazar.service.UserService;
import uz.pdp.lebazar.utils.CommonUtils;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    MailService mailService;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/registerAdmin")
//    public HttpEntity<?> registerAdmin(@RequestBody UserDto userDto) {
//        ApiResponse apiResponse = userService.register(userDto);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
//    }

//    @PostMapping("/registerClient")
//    public HttpEntity<?> registerClient(
//            @RequestParam String email,
//            @RequestParam String code) {
//        User user = userRepository.findByEmail(email).get();
//        if (user.getRealCode().equals(code.trim())) {
//            UserDto userDto = new UserDto(user.getEmail(), user.getPassword(), code);
//            userDto.setRoleNames(Collections.singletonList(RoleName.ROLE_CLIENT));
//            ApiResponse apiResponse = userService.register(userDto);
//            return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
//        } else {
//            userRepository.deleteById(user.getId());
//            return ResponseEntity.ok("Error");
//        }
//    }

    @GetMapping("/login")
    public HttpEntity<?> login(@RequestParam String email,@RequestParam String password) {
        UserDto userDto = new UserDto(email, password);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User principal = (User) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Autowired
    UserRepository userRepository;
    @PostMapping("/sendCode")
    public ApiResponse sendText(@RequestParam String email) {
        if (userRepository.findByEmail(email).isPresent())
            return new ApiResponse("Exist",true,null,null,"Email exist already");

        return mailService.sendText(email, String.valueOf(CommonUtils.generateCode()));
    }
}
