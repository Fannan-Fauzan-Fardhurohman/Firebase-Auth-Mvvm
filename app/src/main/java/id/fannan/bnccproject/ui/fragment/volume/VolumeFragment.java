package id.fannan.bnccproject.ui.fragment.volume;

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
import id.fannan.bnccproject.databinding.FragmentVolumeBinding;
import id.fannan.bnccproject.ui.viewmodel.CalculateViewModel;

public class VolumeFragment extends Fragment {

    private FragmentVolumeBinding binding;

    private CalculateViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVolumeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
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
            boolean isBlocks = checkedId == R.id.rb_blocks;
            boolean isPyramids = checkedId == R.id.rb_pyramids;
            boolean isTubes = checkedId == R.id.rb_tubes;
            binding.etBase.setText(""); // Clear the EditText
            binding.etHeight.setText(""); // Clear the EditText
            binding.etLength.setText(""); // Clear the EditText
            binding.etRadius.setText(""); // Clear the EditText
            binding.etRadius2.setText(""); // Clear the EditText
            binding.etBase.setVisibility(isBlocks || isPyramids ? View.VISIBLE : View.GONE);
            binding.etHeight.setVisibility(View.VISIBLE);
            binding.etLength.setVisibility(isBlocks ? View.VISIBLE : View.GONE);
            binding.etRadius.setVisibility(isTubes ? View.VISIBLE : View.GONE);
            binding.etRadius2.setVisibility(isTubes ? View.VISIBLE : View.GONE);
        });

        binding.btnCalculate.setOnClickListener(v -> {
            String shape = "";
            double base = 0;
            double height = 0;
            double length = 0;
            double radius = 0;
            double radius2 = 0;

            if (binding.rbBlocks.isChecked()) {
                shape = "Blocks";
                base = Double.parseDouble(binding.etBase.getText().toString());
                height = Double.parseDouble(binding.etHeight.getText().toString());
                length = Double.parseDouble(binding.etLength.getText().toString());
            } else if (binding.rbPyramids.isChecked()) {
                shape = "Pyramids";
                base = Double.parseDouble(binding.etBase.getText().toString());
                height = Double.parseDouble(binding.etHeight.getText().toString());
            } else if (binding.rbTubes.isChecked()) {
                shape = "Tubes";
                radius = Double.parseDouble(binding.etRadius.getText().toString());
                radius2 = Double.parseDouble(binding.etRadius2.getText().toString());
                height = Double.parseDouble(binding.etHeight.getText().toString());
            }

            viewModel.calculateVolume(shape, base, height, length, radius, radius2);
        });
        viewModel.getVolumeLiveData().observe(getViewLifecycleOwner(), volume -> {
            Toast.makeText(getContext(), "Volume : " + volume, Toast.LENGTH_SHORT).show();
            binding.tvResult.setText(String.valueOf(volume));
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}