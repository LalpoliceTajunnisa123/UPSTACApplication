package org.upgrad.upstac.auth.register;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.UserService;
import org.upgrad.upstac.users.models.AccountStatus;
import org.upgrad.upstac.users.roles.UserRole;

import static org.upgrad.upstac.shared.DateParser.getDateFromString;

@Service
public class RegisterService {

    @Autowired
    private UserService userService;


    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);


    public User addUser(RegisterRequest registerRequest) {


    if(userService.findByUserName(registerRequest.getUserName())!=null)
    	throw new AppException("Username already exists " + registerRequest.getUserName());

    if(userService.findByEmail(registerRequest.getEmail())!=null)
    	throw new AppException("User with same email id already exists "+ registerRequest.getEmail());
    
    if(userService.findByPhoneNumber(registerRequest.getPhoneNumber())!=null)
    	throw new AppException("User with same phone number already exists "+registerRequest.getPhoneNumber());
    
    User newUser = new User();
    newUser.setUserName(registerRequest.getUserName());
    newUser.setPassword(userService.toEncrypted(registerRequest.getPassword()));
    newUser.setRoles(userService.getRoleFor(UserRole.USER));
    newUser.setCreated(LocalDateTime.now());
    newUser.setUpdated(LocalDateTime.now());
    newUser.setAddress(registerRequest.getAddress());
    newUser.setFirstName(registerRequest.getFirstName());
    newUser.setLastName(registerRequest.getLastName());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setPhoneNumber(registerRequest.getPhoneNumber());
    newUser.setPinCode(registerRequest.getPinCode());
    newUser.setGender(registerRequest.getGender());
    newUser.setAddress(registerRequest.getAddress());
    newUser.setDateOfBirth(getDateFromString(registerRequest.getDateOfBirth()));
    newUser.setStatus(AccountStatus.APPROVED);
    return userService.saveInDatabase(newUser);

/*      User should be validated before registration.
                the username , email and phone number should be unique (i.e should throw AppException if the RegisterRequest has the same username or email or phone number)
                    hint:
                        userService.findByUserName
                        userService.findByEmail
                        userService.findByPhoneNumber

         A new User Object should be created with same details as registerRequest
                password should be encrypted with help of   userService.toEncrypted
                roles should be set with help of  userService.getRoleFor(UserRole.USER)
                status should be set to AccountStatus.APPROVED

        And finally
            Call userService.saveInDatabase to save the new user and return the saved user
*/

      
    }

   
	

	public User addDoctor(RegisterRequest user) {
 
		if(userService.findByUserName(user.getUserName())!=null)
	    	throw new AppException("Username already exists " + user.getUserName());

	    if(userService.findByEmail(user.getEmail())!=null)
	    	throw new AppException("User with same email id already exists "+ user.getEmail());
	    
	    if(userService.findByPhoneNumber(user.getPhoneNumber())!=null)
	    	throw new AppException("User with same phone number already exists "+user.getPhoneNumber());
	    
	    User newUser = new User();
	    newUser.setUserName(user.getUserName());
	    newUser.setPassword(userService.toEncrypted(user.getPassword()));
	    newUser.setRoles(userService.getRoleFor(UserRole.DOCTOR));
	    newUser.setCreated(LocalDateTime.now());
	    newUser.setUpdated(LocalDateTime.now());
	    newUser.setAddress(user.getAddress());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
	    newUser.setEmail(user.getEmail());
	    newUser.setPhoneNumber(user.getPhoneNumber());
	    newUser.setPinCode(user.getPinCode());
	    newUser.setGender(user.getGender());
	    newUser.setAddress(user.getAddress());
	    newUser.setDateOfBirth(getDateFromString(user.getDateOfBirth()));
	    newUser.setStatus(AccountStatus.INITIATED);
	    return userService.saveInDatabase(newUser);
/*      Doctor should be validated before registration.
                the username , email and phone number should be unique (i.e should throw AppException if the RegisterRequest has the same username or email or phone number)
                    hint:
                        userService.findByUserName
                        userService.findByEmail
                        userService.findByPhoneNumber

         A new User Object should be created with same details as registerRequest
                password should be encrypted with help of   userService.toEncrypted
                roles should be set with help of  userService.getRoleFor(UserRole.DOCTOR)
                status should be set to AccountStatus.INITIATED

        And finally
            Call userService.saveInDatabase to save the newly registered doctor and return the saved value
*/
       
    }


    public User addTester(RegisterRequest user) {

    	if(userService.findByUserName(user.getUserName())!=null)
	    	throw new AppException("Username already exists " + user.getUserName());

	    if(userService.findByEmail(user.getEmail())!=null)
	    	throw new AppException("User with same email id already exists "+ user.getEmail());
	    
	    if(userService.findByPhoneNumber(user.getPhoneNumber())!=null)
	    	throw new AppException("User with same phone number already exists "+user.getPhoneNumber());
	    
	    User newUser = new User();
	    newUser.setUserName(user.getUserName());
	    newUser.setPassword(userService.toEncrypted(user.getPassword()));
	    newUser.setRoles(userService.getRoleFor(UserRole.TESTER));
	    newUser.setCreated(LocalDateTime.now());
	    newUser.setUpdated(LocalDateTime.now());
	    newUser.setAddress(user.getAddress());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
	    newUser.setEmail(user.getEmail());
	    newUser.setPhoneNumber(user.getPhoneNumber());
	    newUser.setPinCode(user.getPinCode());
	    newUser.setGender(user.getGender());
	    newUser.setAddress(user.getAddress());
	    newUser.setDateOfBirth(getDateFromString(user.getDateOfBirth()));
	    newUser.setStatus(AccountStatus.INITIATED);
	    return userService.saveInDatabase(newUser);
/*      Tester should be validated before registration.
                the username , email and phone number should be unique (i.e should throw AppException if the RegisterRequest has the same username or email or phone number)
                    hint:
                        userService.findByUserName
                        userService.findByEmail
                        userService.findByPhoneNumber

         A new User Object should be created with same details as registerRequest
                password should be encrypted with help of   userService.toEncrypted
                roles should be set with help of  userService.getRoleFor(UserRole.TESTER)
                status should be set to AccountStatus.INITIATED

        And finally
            Call userService.saveInDatabase to save newly registered tester and return the saved value
*/

        
    }


}
