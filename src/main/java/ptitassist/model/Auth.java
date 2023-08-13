package ptitassist.model;

import lombok.Data;

@Data
public class Auth {
    private String name;
    private String email;
    private String password;
}
