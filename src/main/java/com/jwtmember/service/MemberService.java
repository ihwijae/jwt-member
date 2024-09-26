package com.jwtmember.service;

import java.util.List;

public interface MemberService {



    public MemberSignUpResponse singUp (MemberSignUpRequest req);


    public void emailDuplicate (String email);

    public void nickNameDuplicate (String nickName);

    public List<MemberFindAllResponse> memberFindAll();

    public LoginResponse login(LoginRequest loginRequest);

}
