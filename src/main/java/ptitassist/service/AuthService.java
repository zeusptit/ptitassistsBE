package ptitassist.service;

import ptitassist.model.Auth;
import ptitassist.model.User;
import ptitassist.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    //Các phương thức cho chức năng đăng nhập
    public User getUser(String id) throws ExecutionException, InterruptedException {
        return authRepository.getUser(id);
    }

    public Boolean checkCredentials(Auth auth) throws ExecutionException, InterruptedException {
        return authRepository.checkCredentials(auth);
    }

    public String createUser(User user) throws ExecutionException, InterruptedException {
        return authRepository.createUser(user);
    }
}
