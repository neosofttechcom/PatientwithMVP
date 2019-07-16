package com.example.myapplication.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

public class MyWorker extends Worker {
    @Override
    public Result doWork() {

        // Do the work here--in this case, 
        //This method will run on background thread 
        myWork();

        // Indicate success or failure with your return value:
        return Result.SUCCESS;

        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }

    private void myWork() {

        Log.e("MYWORK", "myWork: ");

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Run your task here
                Toast.makeText(getApplicationContext(), "Uploading Data To server", Toast.LENGTH_SHORT).show();
            }
        }, 1000 );

    }
}