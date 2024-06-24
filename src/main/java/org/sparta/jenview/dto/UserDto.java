package org.sparta.jenview.dto;

import lombok.Getter;
import lombok.Setter;
import org.sparta.jenview.entity.UserEntity;

@Getter
@Setter
// UserDto 클래스: 사용자 정보를 담는 데이터 전송 객체
public class UserDto {

    private String username;
    private String password;
    private String role;
    private String createdAt;
    private String updatedAt;

    //기본 생성자
    public UserDto() {
    }

    // UserEntity를 받아들이는 생성자
    public UserDto(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.role = userEntity.getRole();
    }

    public UserDto(UserDto userDto) {
    }
}
