package com.jwtmember.service;

public interface MemberService {



    public MemberSignUpResponse singUp (MemberSignUpRequest req);


    public void emailDuplicate (String email);

    public void nickNameDuplicate (String nickName);
}
