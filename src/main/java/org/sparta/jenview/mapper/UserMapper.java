package org.sparta.jenview.mapper;

import org.sparta.jenview.dto.UserDTO;
import org.sparta.jenview.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperInterface<UserDTO, UserEntity> {

    // to Dto
    public UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(userEntity.getRole());
        userDTO.setName(userEntity.getName());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }

    // to Entity
    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(userDTO.getRole());
        userEntity.setName(userDTO.getName());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        return userEntity;
    }
}
