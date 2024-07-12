package org.sparta.jenview.users.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.users.entity.Role;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private Role role;
    private String username;
    private String email;

}