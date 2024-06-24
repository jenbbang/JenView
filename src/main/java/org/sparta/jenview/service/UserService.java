package org.sparta.jenview.service;

import jakarta.transaction.Transactional;
import org.sparta.jenview.dto.MessageDto;
import org.sparta.jenview.dto.UserDto;
import org.sparta.jenview.entity.UserEntity;
import org.sparta.jenview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타내는 어노테이션
public class UserService {

    private final UserRepository userRepository;

    // UserService 생성자, UserRepository를 주입받아 초기화
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 새로운 사용자를 생성하는 메서드
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity(userDto); // UserDto를 UserEntity로 변환
        UserEntity savedUserEntity = userRepository.save(userEntity); // DB에 저장
        return new UserDto(savedUserEntity); // 저장된 UserEntity를 UserDto로 변환하여 반환
    }

    // 모든 사용자를 조회하는 메서드
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll(); // 모든 UserEntity 조회
        return userEntities.stream() // UserEntity 리스트를 UserDto 리스트로 변환
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    // 특정 ID를 가진 사용자를 조회하는 메서드
    public UserDto getUser(int id) {
        UserEntity userEntity = userRepository.findById(id) // ID로 UserEntity 조회
                .orElseThrow(() -> new RuntimeException("User not found")); // 없으면 예외 발생
        return new UserDto(userEntity); // 조회된 UserEntity를 UserDto로 변환하여 반환
    }

    // 특정 ID를 가진 사용자를 업데이트하는 메서드
    @Transactional
    public UserDto updateUser(UserDto userDto, int id) {
        UserEntity userEntity = findUser(id); // ID로 UserEntity 조회 및 존재 확인
        userEntity.update(userDto); // UserEntity 업데이트
        return new UserDto(userEntity); // 업데이트된 UserEntity를 UserDto로 변환하여 반환
    }

    // 특정 ID를 가진 사용자를 삭제하는 메서드
    public MessageDto deleteUser(int id) {
        UserEntity userEntity = findUser(id); // ID로 UserEntity 조회 및 존재 확인
        userRepository.delete(userEntity); // UserEntity 삭제
        return new MessageDto("User deleted successfully"); // 성공 메시지 반환
    }

    // 특정 ID를 가진 사용자를 조회하고 없으면 예외를 발생시키는 메서드
    private UserEntity findUser(int id) {
        return userRepository.findById(id) // ID로 UserEntity 조회
                .orElseThrow(() -> new IllegalArgumentException("User not found")); // 없으면 예외 발생
    }
}
