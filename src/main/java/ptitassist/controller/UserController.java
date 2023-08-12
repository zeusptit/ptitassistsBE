package ptitassist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptitassist.Exception.EmailAlreadyExistsException;
import ptitassist.model.User;
import ptitassist.service.UserService;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/user")
    public ResponseEntity<Object> changeUser(@RequestBody User updateUser, Authentication authentication) throws ExecutionException, InterruptedException{
        String curentUsername = authentication.getName();
        try{
            User user = userService.updateUser(curentUsername, updateUser);
            return ResponseEntity.ok(user);
        }catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
    }


}