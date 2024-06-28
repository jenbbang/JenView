package org.sparta.jenview.service;

import jakarta.transaction.Transactional;

import org.sparta.jenview.dto.UserDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private UserDTO UserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(userEntity.getRole());
        userDTO.setName(userEntity.getName());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }
    //모든 유저를 가져오는
    public List<UserDTO> getUserList() {
        List<UserEntity> useryList = userRepository.findAll();
        return useryList.stream().map(this::UserDTO).toList();
    }

    //일부 유저를 가져오는
    public UserDTO getUserInfo(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return UserDTO(userEntity);
    }
    //유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

}
