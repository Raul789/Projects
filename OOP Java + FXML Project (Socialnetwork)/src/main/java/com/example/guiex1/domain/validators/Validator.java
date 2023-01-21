package com.example.guiex1.domain.validators;

import com.example.guiex1.domain.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws com.example.guiex1.exceptions.ValidationException;
}
