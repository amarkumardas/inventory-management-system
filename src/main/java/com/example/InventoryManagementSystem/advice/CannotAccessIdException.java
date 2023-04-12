package com.example.InventoryManagementSystem.advice;

public class CannotAccessIdException extends Exception{
    public CannotAccessIdException(){//non parameterized constructor
             super();
    }
    public CannotAccessIdException(String message){// parameterized constructor to pass message
         super(message);
    }
}
