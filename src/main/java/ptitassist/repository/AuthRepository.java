package ptitassist.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import ptitassist.model.Auth;
import ptitassist.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class AuthRepository {
    public User getUser(String email) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFileStore.collection("users").document(email);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        User user = null;
        if (documentSnapshot.exists()) {
            user = documentSnapshot.toObject(User.class);
        }
        return user;
    }

    public String createUser(User user) throws ExecutionException, InterruptedException {
        Firestore dbFileStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFileStore.collection("users").document(user.getEmail()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Boolean checkCredentials(Auth auth) throws ExecutionException, InterruptedException {
        User foundUser = getUser(auth.getEmail());
        if (foundUser != null) {
            return auth.getPassword().equals(foundUser.getPassword());
        }
        return false;
    }
}
