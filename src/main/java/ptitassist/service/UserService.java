package ptitassist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ptitassist.model.User;
import ptitassist.repository.UserRepository;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository

    //Phương thức cập nhật thông tin user theo username name
    public User updateUser(String username, User updateUser) throws ExecutionException, InterruptedException {
        return userRepository.updateUser(username, updateUser);
    }
}
