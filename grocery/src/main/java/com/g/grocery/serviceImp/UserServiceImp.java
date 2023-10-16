package com.g.grocery.serviceImp;
import com.g.grocery.constants.GroceryConstants;
import com.g.grocery.dao.UserDao;
import com.g.grocery.jwt.CustomerUserDetailsService;
import com.g.grocery.jwt.JWTUtil;
import com.g.grocery.jwt.JwtFilter;
import com.g.grocery.model.User;
import com.g.grocery.service.UserService;
import com.g.grocery.utils.GroceryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp {}", requestMap);
        try {
            if (validateSignUp(requestMap)) {
                User user = userDao.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return GroceryUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);

                } else {
                    return GroceryUtils.getResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
                }
            } else {
                return GroceryUtils.getResponseEntity(GroceryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if(authentication.isAuthenticated()) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                            customerUserDetailsService.getUserDetail().getRole())
                            + "\"}", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad credentials" + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<User>> getAllUser() {
        try {
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            }
            else{
                new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return GroceryUtils.getResponseEntity("true", HttpStatus.OK);
    }


    private boolean validateSignUp(Map<String, String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        else
            return false;
    }

    private User getUserFromMap(Map<String,String> request){
        User user = new User();
        user.setName(request.get("name"));
        user.setEmail(request.get("email"));
        user.setContactNumber(request.get("contactNumber"));
        user.setPassword(request.get("password"));
        user.setRole(request.get("user"));
        return user;
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User currentUser = userDao.findByEmail(jwtFilter.getCurrentUser());
            if(currentUser != null){
                if(currentUser.getPassword().equals(requestMap.get("oldPassword"))){
                    currentUser.setPassword(requestMap.get("newPassword"));
                    userDao.save(currentUser);
                    return GroceryUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return GroceryUtils.getResponseEntity("Incorrect Old password", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}