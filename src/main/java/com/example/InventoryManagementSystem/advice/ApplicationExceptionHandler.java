package com.example.InventoryManagementSystem.advice;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // @ControllerAdvice+@ResponseBody
public class ApplicationExceptionHandler {//EntityExistsException one more time execute

    @ExceptionHandler(EntityExistsException.class)//whenever this exception occur then this annotation tells to springboot to redirect to this method
    public ResponseEntity<String> entityExistsException(EntityExistsException ex){
        return new ResponseEntity<>("execute once more",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)//whenever this exception occur then this annotation tells to springboot to redirect to this method
    public Map<String,String> handleInavalidArgument(MethodArgumentNotValidException ex){
        //logic for better readable
        Map<String,String> errorMap=new HashMap<>();
        //getting all errors and converted to list by this method getFieldErrors()
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());//error.getField() return issue
        });
        System.out.println(ex.getLocalizedMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)//whenever this exception occur then this annotation tells to springboot to redirect to this method
    public Map<String,String> handleInavalidArgument(java.sql.SQLIntegrityConstraintViolationException ex){
        //logic for better readable
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error","SQLIntegrityConstraintViolation");//error.getField() return issue
        System.out.println(ex.getLocalizedMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(java.util.NoSuchElementException.class)//whenever this exception occur then this annotation tells to springboot to redirect to this method
    public Map<String,String> handleInavalidArgument(java.util.NoSuchElementException ex){
        //logic for better readable
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error","id not present");//error.getField() return issue
        System.out.println(ex.getLocalizedMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CannotAccessIdException.class)//whenever this exception occur then this annotation tells to springboot to redirect to this method
    public Map<String,String> handleInavalidArgument(CannotAccessIdException ex){
        //logic for better readable
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getLocalizedMessage());//error.getField() return issue
        System.out.println(ex.getLocalizedMessage());
        return errorMap;
    }
}









/*Explain all status code and when we should use
HTTP status codes are three-digit numbers that are returned by a server to indicate the status of a client's request.
They are used to indicate whether a request was successful, and if not, why not.

Here are some of the most commonly used HTTP status codes and when they should be used:

200 OK: Indicates that the request was successful and that the requested resource was returned. This is the most
        common status code and should be used when a request is successful.

201 Created: Indicates that a new resource was successfully created as a result of the request. This status code should
             be used when creating a new resource, such as a new user or a new blog post.

204 No Content: Indicates that the request was successful, but there is no content to return. This status code should
                be used when a request is successful, but there is no data to return, such as when deleting a resource.

400 Bad Request: Indicates that the request was malformed or invalid. This status code should be used when the client
                  sends a request that the server cannot understand or process, such as a request with missing or invalid parameters.

401 Unauthorized: Indicates that the client is not authorized to access the requested resource. This status code should
                  be used when the client needs to authenticate before accessing a resource.

403 Forbidden: Indicates that the client is not allowed to access the requested resource. This status code should be
               used when the client is authenticated, but is not authorized to access the resource.

404 Not Found: Indicates that the requested resource could not be found on the server. This status code should be used
                when the client requests a resource that does not exist.

500 Internal Server Error: Indicates that an error occurred on the server. This status code should be used when an
                       unexpected error occurs on the server, such as a server-side crash or a database connection issue.

These are the most common status code, but there are many other status codes that are used for different purposes.
It is important to choose the correct status code for your response to ensure that the client understands the outcome
 of the request.*/