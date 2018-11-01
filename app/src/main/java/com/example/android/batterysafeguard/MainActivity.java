package com.example.android.batterysafeguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private ProgressBar progressBar;

    private int actionLevel;

    private VibrateReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.battery);
        progressBar = findViewById(R.id.progressBar);

        receiver = new VibrateReceiver();

        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


    private void intentService(){

        Intent i = new Intent(this, VibrateService.class);
        i.setAction(VibrateTask.VIBRATE);
        i.putExtra(VibrateTask.NUMBER, actionLevel);
        startService(i);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private  class VibrateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(Intent.ACTION_BATTERY_CHANGED)){

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);

                double perc = level / (double) scale;

                actionLevel = (int) (perc * 100);

                text.setText(String.format("Battery level: %s%%", Integer.toString(actionLevel)));
                progressBar.setProgress(actionLevel);

                intentService();
            }

        }
    }
}
