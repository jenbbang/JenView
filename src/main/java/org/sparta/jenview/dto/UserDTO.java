package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.entity.Role;

@Getter
@Setter
public class UserDTO {

    private Role role;
    private String name;
    private String username;
    private String email;

}