package ptitassist.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import com.google.cloud.firestore.WriteResult;
import java.util.concurrent.ExecutionException;

import ptitassist.exception.UserNotFoundException;
import ptitassist.model.User;

import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {
    public User getUser(String username) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        User user = null;
        if (documentSnapshot.exists()) {
            user = documentSnapshot.toObject(User.class);
        }
        return user;
    }

    //Cập nhật thông tin user: name, email, password
    public User updateUser(String username, User updateUser) throws ExecutionException, InterruptedException{
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference userReference = dbFireStore.collection("users").document(username);
        DocumentSnapshot userSnapshot;
        try {
            userSnapshot = userReference.get().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new UserNotFoundException("User not found");
        }
        if (!userSnapshot.exists()) {
            throw new UserNotFoundException("User not found");
        }
        WriteResult writeResult;
        try {
            writeResult = userReference.set(updateUser).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to update user");
        }
        return updateUser;
    }
}
