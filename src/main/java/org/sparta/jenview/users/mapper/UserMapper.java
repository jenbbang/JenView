package org.sparta.jenview.users.mapper;

import org.sparta.jenview.jwt_security.mapper.MapperInterface;
import org.sparta.jenview.users.dto.UserDTO;
import org.sparta.jenview.users.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperInterface<UserDTO, UserEntity> {

    // to Dto
    public UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setRole(userEntity.getRole());
        userDTO.setName(userEntity.getName());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }

    // to Entity
    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setRole(userDTO.getRole());
        userEntity.setName(userDTO.getName());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        return userEntity;
    }
}
