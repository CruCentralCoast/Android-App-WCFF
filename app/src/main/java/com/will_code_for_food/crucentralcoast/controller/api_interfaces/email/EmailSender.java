package com.will_code_for_food.crucentralcoast.controller.api_interfaces.email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.Logger;

/**
 * Sends emails using the user's email account and existing email app
 */
public class EmailSender {
    private static final String SCHEME = "mailto";

    public static boolean send(Activity activity, EmailMessage msg) {
        if (msg == null) {
            Log.e("Sending Email", "Could not send null message");
            return false;
        }
        Log.e("Sending Email", "Sending email to " + msg.to);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME,
                msg.to, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, msg.subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg.body);
        for (AttachmentFile file : msg.attachments) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.file));
        }
        activity.startActivityForResult(Intent.createChooser(intent, "Send email..."), 0);
        /*
        for (AttachmentFile file : msg.attachments) {
            if (file.deleteAfterwards) {
                Logger.i("Deleting (internal)", file.file.getName());
                LocalStorageIO.deleteFile(file.file.getName());
            }
            Log.e("Deleting (external)", file.file.getName());
            if(!file.file.delete()) {
                Logger.i("Attachment Deletion", file.file.getName() + "failed external deletion");
            }
        }
        */
        // TODO delete external files every time & local if flagged for deletion
        return true;
    }
}
