package com.jwtmember.service;

import com.jwtmember.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSignUpRsp {


    private String email;
    private String username;
    private String nickname;
    private String birthdate;


    public static MemberSignUpRsp toDto(Member member) {
        return MemberSignUpRsp.builder()
                .email(member.getUserEmail())
                .username(member.getUserName())
                .nickname(member.getNickName())
                .build();
    }
}
