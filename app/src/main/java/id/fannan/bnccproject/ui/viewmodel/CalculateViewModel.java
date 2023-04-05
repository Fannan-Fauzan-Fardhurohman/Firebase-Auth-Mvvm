package id.fannan.bnccproject.ui.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public class CalculateViewModel extends ViewModel {
    private MutableLiveData<Double> areaLiveData = new MutableLiveData<>();
    private MutableLiveData<Double> volumeLiveData = new MutableLiveData<>();

    public LiveData<Double> getAreaLiveData() {
        return areaLiveData;
    }

    public LiveData<Double> getVolumeLiveData() {
        return volumeLiveData;
    }


    public void calculateArea(String shape, double base, double height, double length, double radius) {
        double area = 0;

        switch (shape) {
            case "Square":
                area = base * base;
                break;
            case "Triangle":
                area = 0.5 * base * height;
                break;
            case "Circle":
                area = Math.PI * radius * radius;
                break;
        }

        areaLiveData.setValue(area);
    }

    public void calculateVolume(String shape, double base, double height, double length, double radius,double radius2) {
        double volume = 0;
        switch (shape) {
            case "Blocks":
                volume = base * height * length;
                break;
            case "Pyramids":
                volume = (1.0 / 3.0) * base * height;
                break;
            case "Tubes":
                volume = Math.PI * radius * radius2 * height;
                break;
        }
        volumeLiveData.setValue(volume);
    }
}
