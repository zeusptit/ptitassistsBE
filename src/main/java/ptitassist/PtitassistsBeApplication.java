package ptitassist;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
public class PtitassistsBeApplication {

    public static void main(String[] args) throws IOException {

        //Kết nối với firebase
        ClassLoader classLoader = PtitassistsBeApplication.class.getClassLoader();

        File file = new File(Objects.requireNonNull(classLoader.getResource("spring-firebase-auth.json")).getFile());
        //có thể thay đổi file "spring-firebase-auth.json" trong Project Setting/Service Accounts trong Firebase
        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://pabe-4bae0-default-rtdb.firebaseio.com")
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        SpringApplication.run(PtitassistsBeApplication.class, args);
    }
}
