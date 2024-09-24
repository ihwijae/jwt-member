package com.jwtmember.domain;

import com.jwtmember.service.MemberSignUpRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity{


    @Id @GeneratedValue
    private Long id;


    @Column(unique = true)
    private String email;

    private String password;
    private String name;
    private LocalDate birthDate;
    private String nickName;
    private String phoneNumber;



    public static Member toEntity(MemberSignUpRequest req){
        return Member.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .nickName(req.getNickname())
                .phoneNumber(req.getPhone())
                .build();
    }
}
