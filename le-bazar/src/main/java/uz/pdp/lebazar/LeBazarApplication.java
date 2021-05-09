package uz.pdp.lebazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import uz.pdp.lebazar.config.InitConfig;

@SpringBootApplication
@EntityScan(basePackages = {"uz.pdp.lebazar.entity"})
public class LeBazarApplication {

    public static void main(String[] args) {
        if (InitConfig.isStart()){
            SpringApplication.run(LeBazarApplication.class, args);
        }
    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:63342");
//            }
//        };
//    }
}
