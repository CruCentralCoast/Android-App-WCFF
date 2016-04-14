package com.will_code_for_food.crucentralcoast.controller.api_interfaces.email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.Logger;

/**
 * Sends emails using the user's email account and existing email app
 */
public class EmailSender {
    private static final String SCHEME = "mailto";

    public static boolean send(Activity activity, EmailMessage msg) {
        if (msg == null) {
            Logger.e("Sending Email", "Could not send null message");
            return false;
        }
        Logger.e("Sending Email", "Sending email to " + msg.to);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME,
                msg.to, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, msg.subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg.body);
        for (AttachmentFile file : msg.attachments) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.file));
        }
        activity.startActivityForResult(Intent.createChooser(intent, "Send email..."), 1);
        return true;
    }
}
