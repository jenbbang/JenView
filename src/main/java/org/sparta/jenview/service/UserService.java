package org.sparta.jenview.service;

import org.sparta.jenview.dto.UserDTO;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.mapper.UserMapper;
import org.sparta.jenview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //모든 유저를 가져오는
    public List<UserDTO> getUserList() {
        List<UserEntity> useryList = userRepository.findAll();
        return useryList.stream().map(userMapper::toDTO).toList();
    }

    //일부 유저를 가져오는
    public UserDTO getUserInfo(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return userMapper.toDTO(userEntity);
    }
    //유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

}
