package com.jwtmember.domain;

import com.jwtmember.service.MemberSignUpReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity{


    @Id @GeneratedValue
    private Long id;


    private String userEmail;
    private String password;
    private String userName;
    private LocalDate birthDate;
    private String nickName;
    private String phoneNumber;



    public static Member toEntity(MemberSignUpReq req){
        return Member.builder()
                .userEmail(req.getEmail())
                .password(req.getPassword())
                .nickName(req.getNickname())
                .phoneNumber(req.getPhone())
                .build();
    }
}
