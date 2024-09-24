package com.jwtmember.service;

import com.jwtmember.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSignUpResponse {


    private String email;
    private String username;
    private String nickname;
    private String birthdate;


    public static MemberSignUpResponse toDto(Member member) {
        return MemberSignUpResponse.builder()
                .email(member.getUserEmail())
                .username(member.getUserName())
                .nickname(member.getNickName())
                .build();
    }
}
