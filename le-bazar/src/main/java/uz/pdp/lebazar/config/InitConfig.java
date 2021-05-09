package uz.pdp.lebazar.config;

import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.util.Properties;

public class InitConfig {

    public static boolean isStart() {
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("/application.properties").getInputStream());
            if (props.getProperty("spring.jpa.hibernate.ddl-auto").equals("update")
                    && props.getProperty("spring.datasource.initialization-mode").equals("never")) {
                return true;
            }
            else{
                String confirm = JOptionPane.showInputDialog("Ma'lumotlarni o'chirib yuborma! O'chirish kodi (Restart123) :");
                if (confirm!=null && confirm.equals("Restart123")){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
