package com.example.degubtools;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private final String TAG = "DEFAULT_TAG";

    private final int min = 20;
    private final int max = 80;
    private final int random = (int) (Math.random() * (max - min + 1)) + min;
    private final int[] array = new int[random];

    private int thread_count;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < random; i++) {
            array[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }

        for (int j : array) {
            Log.d(TAG, String.valueOf(j));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (thread == null || !thread.isAlive()) {
            startThread();
        }
    }

    private void startThread() {
        thread_count++;
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Log.d(TAG, "Number of threads running: " + thread_count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopThread();
    }

    private void stopThread() {
        thread_count--;
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join(); // Ждем, пока поток завершится
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread = null; // После завершения потока устанавливаем ссылку на него как null
        }
    }
}
