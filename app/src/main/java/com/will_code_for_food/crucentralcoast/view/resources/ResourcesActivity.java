package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.model.resources.YoutubeViewer;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by mallika on 1/14/16.
 */
public class ResourcesActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragmentById(R.layout.fragment_resources, "Resources", null, this);
    }

    public void testYoutube(View view) {
        YoutubeViewer.watchYoutubeVideo("hGcmaztq7eU", this);
    }

    public void clickLeaderResources(View view) {
        if (!Authenticator.isUserLoggedIn() && !Authenticator.logIn()) {
            startActivityForResult(new Intent(this, LogInActivity.class), 1);
        } else {
            goToLeaderResources();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && Authenticator.isUserLoggedIn()) {
            goToLeaderResources();
        }
    }

    private void goToLeaderResources() {
        // TODO actually show leader resources
        Toast.makeText(context, "Show leader resources...", Toast.LENGTH_LONG).show();
    }
}
