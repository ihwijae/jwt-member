package com.jwtmember.apicontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {



    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

}
