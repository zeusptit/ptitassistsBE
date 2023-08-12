package ptitassist.controller;

import ptitassist.model.Auth;
import ptitassist.model.User;
import ptitassist.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Auth auth) throws ExecutionException, InterruptedException {
        Boolean loginSuccess = authService.checkCredentials(auth);
        if (loginSuccess){
            return ResponseEntity.ok().body("Login Successfully!");
        }
        return ResponseEntity.badRequest().body("Invalid Credentials!");
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) throws ExecutionException, InterruptedException {
        String timeCreated = authService.createUser(user);
        if(timeCreated != null){
            return ResponseEntity.ok().body(timeCreated);
        }
        return ResponseEntity.badRequest().body("Error!");
    }


}

