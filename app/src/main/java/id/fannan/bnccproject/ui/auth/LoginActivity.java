package id.fannan.bnccproject.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.fannan.bnccproject.R;
import id.fannan.bnccproject.databinding.ActivityLoginBinding;
import id.fannan.bnccproject.ui.MainActivity;
import id.fannan.bnccproject.ui.auth.viewmodel.AuthViewModel;
import id.fannan.bnccproject.ui.viewmodel.CalculateViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    binding.editTextEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    binding.editTextPassword.setError("Password is required");
                    return;
                }
                viewModel.login(email, password);
            }
        });
        binding.textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        viewModel.getLoginState().observe(this, state -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            switch (state) {
                case LOADING:
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Logging in... ");
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}