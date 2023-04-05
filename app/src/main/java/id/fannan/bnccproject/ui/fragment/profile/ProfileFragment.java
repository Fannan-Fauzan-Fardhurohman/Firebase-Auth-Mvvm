package id.fannan.bnccproject.ui.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import id.fannan.bnccproject.databinding.FragmentProfileBinding;
import id.fannan.bnccproject.ui.auth.LoginActivity;
import id.fannan.bnccproject.ui.auth.viewmodel.AuthViewModel;

public class ProfileFragment extends Fragment {
    private AuthViewModel authViewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        authViewModel.getInfoUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.tvUsername.setText(user.getName());
                binding.tvEmail.setText(user.getEmail());
            }
        });

        binding.logoutButton.setOnClickListener(v -> {
            authViewModel.logout();
            redirectToLoginPage();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!authViewModel.isLogin()) {
            redirectToLoginPage();
        }
    }

    private void redirectToLoginPage() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}