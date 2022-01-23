package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserDto implements DTO, Serializable {

    @Length(min = 3, message = "Username must be at least 3 characters long!")
    @Length(max = 24, message = "Username cannot be longer than 24 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username can contain only alphanumeric characters!")
    private String username;

    @Length(min = 9, message = "Password must be at least 9 characters long!")
    @Length(max = 64, message = "Password cannot be longer than 64 characters!")
    private String password;

    private UserDto() {}

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public User toEntity() {
        return new User(getUsername(), getPassword(), User.Type.USER);
    }

}
