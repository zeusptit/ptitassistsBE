package ptitassist.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import ptitassist.model.Auth;
import ptitassist.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class AuthRepository {
    public User getUser(String name) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection("users").document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        User user = null;
        if (documentSnapshot.exists()) {
            user = documentSnapshot.toObject(User.class);
        }
        return user;
    }

    public String createUser(User user) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        CollectionReference usersCollection = dbFireStore.collection("users");
        ApiFuture<WriteResult> collectionsApiFuture = dbFireStore.collection("users").document(user.getName()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Boolean checkCredentials(Auth auth) throws ExecutionException, InterruptedException {
        User foundUser = getUser(auth.getName());
        if (foundUser != null) {
            return auth.getPassword().equals(foundUser.getPassword());
        }
        return false;
    }
}
