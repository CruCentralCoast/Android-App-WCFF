package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Sends emails using the user's email account and existing email app
 */
public class EmailSender {
    private static final String SCHEME = "mailto";

    public static boolean send(Activity activity, EmailMessage msg) {
        if (msg == null) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME,
                msg.to, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, msg.subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg.body);
        for (AttachmentFile file : msg.attachments) {
            intent.putExtra(Intent.EXTRA_STREAM, file.uri);
        }
        activity.startActivityForResult(Intent.createChooser(intent, "Send email..."), 0);
        return true;
    }
}
