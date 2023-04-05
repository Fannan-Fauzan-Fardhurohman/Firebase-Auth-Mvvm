package id.fannan.bnccproject.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import id.fannan.bnccproject.model.User;
import id.fannan.bnccproject.ui.auth.viewmodel.AuthViewModel;

public class AuthRepository {
    private final MutableLiveData<AuthViewModel.LoginState> loginStateLiveData = new MutableLiveData<>(); // new live data

    private final FirebaseAuth firebaseAuth;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void register(User user, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Save user data to Firebase Realtime Database
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            DatabaseReference userRef = FirebaseDatabase.getInstance()
                                    .getReference("users")
                                    .child(firebaseUser.getUid());

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("id", user.getId());
                            userData.put("email", user.getEmail());
                            userData.put("name", user.getName());
                            userData.put("password", user.getPassword());

                            userRef.setValue(userData);
                        }

                        loginStateLiveData.setValue(AuthViewModel.LoginState.SUCCESS);
                    } else {
                        loginStateLiveData.setValue(AuthViewModel.LoginState.ERROR);
                    }
                    listener.onComplete(task);
                });
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginStateLiveData.setValue(AuthViewModel.LoginState.SUCCESS);
                    } else {
                        loginStateLiveData.setValue(AuthViewModel.LoginState.ERROR);
                    }
                    listener.onComplete(task);
                });
    }

    public LiveData<User> userGetInfo() {
        MutableLiveData<User> userDataLiveData = new MutableLiveData<>();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {

            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    userDataLiveData.setValue(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("<><><><> " + error.getMessage());
                }
            });
        }
        return userDataLiveData;
    }


    public void logout() {
        firebaseAuth.signOut();
    }

    public boolean isLoggin() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        return currentUser != null;
    }
}
