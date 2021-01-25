package com.space.util;

import com.space.exeptions.InvalidIdException;

public class IdValidator {
    private IdValidator(){}
    
    public static Long validate(String stringValue) throws InvalidIdException {
        if (stringValue == null || stringValue.equals("0") || stringValue.contains("-")) throw new InvalidIdException("Bad Id");
        long id ;
        try{
            id = Long.parseLong(stringValue);
        } catch (NumberFormatException e){
            throw new InvalidIdException("Bad id");
        }
        return id;
    }
}
