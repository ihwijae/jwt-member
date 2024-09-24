package com.jwtmember.domain;

import com.jwtmember.service.MemberSignUpRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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



    public static Member toEntity(MemberSignUpRequest req){
        return Member.builder()
                .userEmail(req.getEmail())
                .password(req.getPassword())
                .nickName(req.getNickname())
                .phoneNumber(req.getPhone())
                .build();
    }
}
