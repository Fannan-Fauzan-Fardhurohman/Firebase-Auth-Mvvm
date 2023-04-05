package id.fannan.bnccproject.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import id.fannan.bnccproject.databinding.ActivityRegisterBinding;
import id.fannan.bnccproject.model.User;
import id.fannan.bnccproject.ui.auth.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String idBimbel = binding.etId.getText().toString().trim();
        String name = binding.etUsername.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(idBimbel)) {
            binding.etId.setError("ID Bimbel is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email is required");
            return;
        }
        if (name.length() <= 5) {
            binding.etUsername.setError("Minimum 5 Characters");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Please enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.etConfirmPassword.setError("Confirm password is required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("Passwords do not match");
            return;
        }
        viewModel.register(new User(idBimbel, name, email, password));

        viewModel.getLoginState().observe(this, state -> {
            System.out.println("<><><><><> " + state);
            switch (state) {
                case LOADING:
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Registering... ");
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}