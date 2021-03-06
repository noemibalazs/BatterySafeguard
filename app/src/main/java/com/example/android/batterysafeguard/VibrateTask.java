package com.example.android.batterysafeguard;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class VibrateTask {

    public static final String VIBRATE = "vibrate";
    public static final String NUMBER = "number";

    private static final int CHARGED_LEVEL = 93;


    public static void vibrateTask(Context context, String action, int level){

        if (VIBRATE.equals(action ) && level == CHARGED_LEVEL){
            Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator!=null){
                vibrator.vibrate(6000);
            }

            MediaPlayer player = MediaPlayer.create(context, R.raw.tell_me_who);
            player.start();

        }
    }
}
