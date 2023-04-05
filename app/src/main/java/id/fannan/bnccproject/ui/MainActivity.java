package id.fannan.bnccproject.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

import id.fannan.bnccproject.R;
import id.fannan.bnccproject.databinding.ActivityMainBinding;
import id.fannan.bnccproject.ui.auth.viewmodel.AuthViewModel;
import id.fannan.bnccproject.ui.fragment.counter.CounterFragment;
import id.fannan.bnccproject.ui.fragment.volume.VolumeFragment;

public class MainActivity extends AppCompatActivity {
//    EditText emailField, passwordField;
//    Button loginButton, registerButton;
//
//    FirebaseAuth firebaseAuth;

    private ActivityMainBinding activityMainBinding;
    private CounterFragment counterFragment;
    private id.fannan.bnccproject.ui.fragment.area.AreaFragment areaFragment;
    private VolumeFragment volumeFragment;

    private Fragment activeFragment;

    private FirebaseAuth mAuth;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_counter, R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_logout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(activityMainBinding.navView, navController);

    }
}