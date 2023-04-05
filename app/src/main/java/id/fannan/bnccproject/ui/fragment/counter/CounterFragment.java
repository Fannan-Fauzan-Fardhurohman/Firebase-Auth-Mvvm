package id.fannan.bnccproject.ui.fragment.counter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import id.fannan.bnccproject.databinding.FragmentCounterBinding;
import id.fannan.bnccproject.helper.CounterManager;

public class CounterFragment extends Fragment {
    private FragmentCounterBinding binding;
    private CounterManager counterManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCounterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        counterManager = new CounterManager(requireContext().getSharedPreferences("Counter", Context.MODE_PRIVATE));
        binding.counterTextView.setText(String.valueOf(counterManager.getCounter()));

        binding.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterManager.increment();
                counterManager.saveCounter();
                binding.counterTextView.setText(String.valueOf(counterManager.getCounter()));
            }
        });

        binding.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterManager.decrement();
                counterManager.saveCounter();
                binding.counterTextView.setText(String.valueOf(counterManager.getCounter()));
            }
        });

        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterManager.reset();
                counterManager.saveCounter();
                binding.counterTextView.setText(String.valueOf(counterManager.getCounter()));
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}