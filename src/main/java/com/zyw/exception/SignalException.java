package com.zyw.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignalException extends RuntimeException{

    private final String code;
    private final String message;

    public SignalException(String code,String message){
        this.code=code;
        this.message=message;
    }

}
