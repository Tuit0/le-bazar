package uz.pdp.lebazar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lebazar.entity.Role;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> role;
    private Timestamp createdAt;

    private String phone;
    private String gender;
    private Date birthDate;

    private Boolean isNotBlock;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(UUID id, String firstName, String lastName, String email, String password, Set<Role> role, Timestamp createdAt, String phone, String gender, Date birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
