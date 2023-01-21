package com.example.guiex1.domain.validators;

import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.exceptions.ValidationException;

public class UtilizatorValidator implements Validator<Utilizator>{
    @Override
    public void validate(Utilizator utilizator) throws ValidationException{
        String message = "";
        if(utilizator.getNume() == null || utilizator.getNume().trim().length() == 0)
        {
            message +="Username can not be empty!\n";
        }
        if(utilizator.getEmail() == null || utilizator.getEmail().trim().length() == 0)
        {
            message +="Email can not be empty!\n";
        }
        if(utilizator.getPassword() == null || utilizator.getPassword().trim().length() == 0)
        {
            message += "Pasword can not be empty!\n";
        }
        if(message.length() > 0)
        {
            throw new ValidationException(message);
        }
    }
}
