package com.example.android.batterysafeguard;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class VibrateService extends IntentService {

    public VibrateService(){
        super("VibrateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String action = intent.getAction();
        int level = intent.getIntExtra(VibrateTask.NUMBER,0);
        VibrateTask.vibrateTask(this, action, level);

    }
}

