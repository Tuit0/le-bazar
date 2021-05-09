package uz.pdp.lebazar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.lebazar.exceptions.MyRuntimeException;
import uz.pdp.lebazar.repository.UserRepository;

import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new MyRuntimeException(email+" not found"));
    }

    public UserDetails loadByUserId(UUID userId){
        return userRepository.findById(userId).orElseThrow(() -> new MyRuntimeException("user not found"));
    }
}
