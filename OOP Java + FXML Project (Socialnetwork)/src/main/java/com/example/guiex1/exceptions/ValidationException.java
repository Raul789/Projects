package com.example.guiex1.exceptions;

import com.example.guiex1.domain.validators.Validator;

public class ValidationException extends Exception{

    public ValidationException(String message){
        super(message);
    }
}
