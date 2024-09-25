package com.hymns.hymns.dto;


import com.hymns.hymns.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String role;

    //    to dto
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
