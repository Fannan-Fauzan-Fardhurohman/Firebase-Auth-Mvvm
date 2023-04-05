package id.fannan.bnccproject.ui.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import id.fannan.bnccproject.model.User;
import id.fannan.bnccproject.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<LoginState> authStateLiveData;


    public AuthViewModel() {
        authRepository = new AuthRepository();
        authStateLiveData = new MutableLiveData<>();
    }

    public void register(User user) {
        setLoginState(LoginState.LOADING);
        authRepository.register(user, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    setLoginState(LoginState.SUCCESS);
                } else {
                    setLoginState(LoginState.ERROR);
                }
            }
        });
    }

    public void login(String email, String password) {
        setLoginState(LoginState.LOADING);
        authRepository.login(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    setLoginState(LoginState.SUCCESS);
                } else {
                    setLoginState(LoginState.ERROR);
                }
            }
        });
    }


    public void logout() {
        authRepository.logout();
    }

    public boolean isLogin() {
        return authRepository.isLoggin();
    }

    public LiveData<User> getInfoUser() {
        return authRepository.userGetInfo();
    }

    public LiveData<LoginState> getLoginState() {
        return authStateLiveData;
    }

    public void setLoginState(LoginState state) {
        authStateLiveData.setValue(state);
    }

    public enum LoginState {
        IDLE,
        LOADING,
        SUCCESS,
        ERROR
    }
}
