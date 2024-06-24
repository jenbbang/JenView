package org.sparta.jenview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sparta.jenview.dto.UserDto;

import java.time.LocalDateTime;


@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Table(name = "User") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="role")
    private String role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public UserEntity(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
    }

    public void update(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
    }
}

