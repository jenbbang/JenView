package org.sparta.jenview.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Table(name = "jenview") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class JenViewEntity {

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

