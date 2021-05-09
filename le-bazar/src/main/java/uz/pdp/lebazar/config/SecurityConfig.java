package uz.pdp.lebazar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.lebazar.secret.JwtFilter;
import uz.pdp.lebazar.service.AuthService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthService authService;

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/loginPage",
                        "/registerPage",
                        "/homePage",
                        "/saveUser",
                        "/oneProduct",

                        "/api/auth/sendCode",

                        "/getUser",
                        "/api/cart/getCartItems","/api/cart/addCartItem","/api/cart/deleteCartItem","/adminPage",
                        "/api/auth/registerClient","/api/auth/login",
                        "/api/products/getProducts","/api/products/getProduct",
                        "/api/products/addOrEditProduct",
                        "/api/products/search",

                        "/userPage",
                        "/cart",
                        "/profile",
                        "/saveProfile",
                        "/search"
                ).permitAll()
                // For admin
                .antMatchers(
                        "/addAdmin", "/checkUsername",
                        "/clients","/getClients","/deleteClient",
                        "/blockClient",
                        "/showOrder", "/api/cart/orders","/deleteFromCart",
                        "/addProduct", "/editProduct", "/removeProduct/**").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
