package id.fannan.bnccproject.ui.fragment.area;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import id.fannan.bnccproject.R;
import id.fannan.bnccproject.databinding.FragmentAreaBinding;
import id.fannan.bnccproject.ui.viewmodel.CalculateViewModel;

public class AreaFragment extends Fragment {

    private FragmentAreaBinding binding;
    private CalculateViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAreaBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CalculateViewModel.class);
        binding.etBase.setVisibility(View.GONE);
        binding.etHeight.setVisibility(View.GONE);
        binding.etLength.setVisibility(View.GONE);
        binding.etRadius.setVisibility(View.GONE);

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            boolean isRectangle = checkedId == R.id.rb_rectangle;
            boolean isTriangle = checkedId == R.id.rb_triangle;
            boolean isCircle = checkedId == R.id.rb_circle;

            binding.etBase.setVisibility(isRectangle || isTriangle ? View.VISIBLE : View.GONE);
            binding.etHeight.setVisibility(isRectangle || isTriangle ? View.VISIBLE : View.GONE);
            binding.etLength.setVisibility(View.GONE);
            binding.etRadius.setVisibility(isCircle ? View.VISIBLE : View.GONE);
        });

        binding.btnCalculate.setOnClickListener(v -> {
            String shape = "";
            double base = 0;
            double height = 0;
            double length = 0;
            double radius = 0;

            if (binding.rbRectangle.isChecked()) {
                shape = "Square";
                base = Double.parseDouble(binding.etBase.getText().toString());
                height = Double.parseDouble(binding.etHeight.getText().toString());
            } else if (binding.rbTriangle.isChecked()) {
                shape = "Triangle";
                base = Double.parseDouble(binding.etBase.getText().toString());
                height = Double.parseDouble(binding.etHeight.getText().toString());
            } else if (binding.rbCircle.isChecked()) {
                shape = "Circle";
                radius = Double.parseDouble(binding.etRadius.getText().toString());
            }

            viewModel.calculateArea(shape, base, height, length, radius);
        });

        viewModel.getAreaLiveData().observe(getViewLifecycleOwner(), area -> {
            Toast.makeText(getContext(), "Area : " + area, Toast.LENGTH_SHORT).show();
            binding.tvResult.setText(String.valueOf(area));
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}