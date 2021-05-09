package uz.pdp.lebazar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lebazar.entity.Product;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.exceptions.MyRuntimeException;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.payload.ProductDto;
import uz.pdp.lebazar.payload.UserDto;
import uz.pdp.lebazar.repository.RoleRepository;
import uz.pdp.lebazar.repository.UserRepository;
import uz.pdp.lebazar.utils.CommonUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse register(UserDto userDto){
         try {
            User user =userRepository.findByEmail(userDto.getEmail()).get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//            user.setRoles(roleRepository.findAllByRoleNameIn(userDto.getRoleNames()));
            userRepository.save(user);
            return new ApiResponse("Successfully registered",true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse("Error",false);
        }
    }

    public ApiResponse getUsers(Integer page, Integer size) {
        Page<User> userPage = userRepository.findAll(CommonUtils.sortedByCreatedAt(page,size));
        return new ApiResponse("Success",
                true,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getContent().stream().map(this::getUserDtoFromUser).collect(Collectors.toList()));
    }

    public UserDto getUserDtoFromUser(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "You can't see the user password!",
                user.getRoles(),
                user.getCreatedAt(),
                user.getPhone(),
                user.getGender(),
                user.getBirthDate(),
                user.isAccountNonBlocked());
    }
}
