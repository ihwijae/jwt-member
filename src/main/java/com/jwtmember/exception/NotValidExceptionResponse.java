package com.jwtmember.exception;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class NotValidExceptionResponse extends ExceptionRes{
    private final List<String> err;
}
