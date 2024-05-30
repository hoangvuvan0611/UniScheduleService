package com.service.unischeduleservice.entity;

import com.service.unischeduleservice.constant.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_user")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "subscribe_date")
    @CreationTimestamp
    private Date subscribeDate;

    @Column(name = "months_count")
    private int monthsCount;

    @Column(name = "role", columnDefinition = "ENUM('USER','ADMIN')")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
