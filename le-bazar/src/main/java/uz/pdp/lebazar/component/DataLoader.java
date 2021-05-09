package uz.pdp.lebazar.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.lebazar.entity.Role;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.entity.enums.RoleName;
import uz.pdp.lebazar.repository.RoleRepository;
import uz.pdp.lebazar.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_CLIENT));

            List<RoleName> roleNames = new ArrayList<>();
            roleNames.add(RoleName.ROLE_ADMIN);
            roleNames.add(RoleName.ROLE_CLIENT);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            userRepository.save(new User(
                    "Og`abek",
                    "Saidov",
                    "ogabeksaidov028@gmail.com",
                    passwordEncoder.encode("2212"),
                    "+998944209449",
                    "Male",
                    simpleDateFormat.parse("22/12/2000"),
                    roleRepository.findAllByRoleNameIn(roleNames),
                    true,
                    true,
                    true,
                    true
            ));

        }
    }
}
