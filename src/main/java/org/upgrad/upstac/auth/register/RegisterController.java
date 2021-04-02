package org.upgrad.upstac.auth.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;

@RestController
public class RegisterController {


    private RegisterService registerService;


    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);


    @Autowired
    public RegisterController(RegisterService userService) {

        this.registerService = userService;
    }


    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody RegisterRequest registerRequest) {
    try {
       return registerService.addUser(registerRequest);
     
      }catch(AppException e){

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
     }
    }


    @RequestMapping(value = "/auth/doctor/register")
    public User saveDoctor(@RequestBody RegisterRequest registerRequest) {
    	
    try {
       return registerService.addDoctor(registerRequest);
       
    }catch(AppException e){
    	
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      
    }
   }


    @RequestMapping(value = "/auth/tester/register")
    public User saveTester(@RequestBody RegisterRequest registerRequest) {
/*
        this method should call addTester function from registerService
        if everything goes fine , it should return User object,
        else if there is an error
            the method should throw ResponseStatusException with HttpStatus.BAD_REQUEST
*/  try {
       return registerService.addTester(registerRequest);
    
    }catch(AppException e){

       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
   }
}