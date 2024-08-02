package com.doraflower.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name = "register_date", updatable = false)
    private LocalDateTime regDate;  // 등록시간

    @LastModifiedDate
    @Column(name = "modify_date")
    private LocalDateTime modDate;  // 수정시간
}
