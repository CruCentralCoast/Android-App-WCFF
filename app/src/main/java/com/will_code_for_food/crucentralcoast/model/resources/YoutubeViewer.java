package com.will_code_for_food.crucentralcoast.model.resources;

import android.content.Intent;
import android.net.Uri;
import android.content.ActivityNotFoundException;
import android.app.Activity;


/**
 * Created by ShelliCrispen on 11/24/15.
 */
public class YoutubeViewer {
    public static void watchYoutubeVideo(final String id, final Activity curAct){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            curAct.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            curAct.startActivity(intent);
        }
    }
}
