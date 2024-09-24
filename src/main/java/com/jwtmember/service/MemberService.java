package com.jwtmember.service;

public interface MemberService {



    public MemberSignUpResponse singUp (MemberSignUpRequest req);


    public String emailDuplicate (String email);

    public String nickNameDuplicate (String nickName);
}
