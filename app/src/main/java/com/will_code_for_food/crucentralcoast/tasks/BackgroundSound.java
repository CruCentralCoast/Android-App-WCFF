package com.will_code_for_food.crucentralcoast.tasks;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.will_code_for_food.crucentralcoast.view.common.MainActivity;
import com.will_code_for_food.crucentralcoast.R;

/**
 * Created by Kayla on 1/21/2016.
 * Plays background music in all activities that extend MainActivity
 */
public class BackgroundSound extends AsyncTask<Void, Void, Void> {

    Activity currentActivity;   // reference to the activity running this task
    MediaPlayer player;         // music player

    public BackgroundSound() {
        currentActivity = (MainActivity) MainActivity.context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        player = MediaPlayer.create(currentActivity, R.raw.temmie);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        player.start();
        return null;
    }

    // Resume playing the music across all activities
    public void resume() {
        player.start();
    }
}
