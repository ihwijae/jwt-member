package com.jwtmember.domain;

import com.jwtmember.service.MemberSignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity{


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String email;

    private String password;
    private String name;
    private LocalDate birthDate;
    private String nickName;
//


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Authority authority;



    public static Member toEntity(MemberSignUpRequest req){
        return Member.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .name(req.getName())
                .nickName(req.getNickname())
                .build();
    }


}
