package id.fannan.bnccproject.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class CounterManager {

    private final SharedPreferences sharedPreferences;
    private int counter;

    public CounterManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.counter = sharedPreferences.getInt("counter", 0);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

    public void reset() {
        counter = 0;
    }

    public void saveCounter() {
        sharedPreferences.edit().putInt("counter", counter).apply();
    }
}