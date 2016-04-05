package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.net.Uri;
import android.util.Log;

import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.view.common.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A message that can be sent as an email
 */
public class EmailMessage {
    public final String to;
    public final String subject;
    public final String body;
    public final List<AttachmentFile> attachments;

    private static final String TEMP_FNAME = "temp-email-attachment-file.mail";

    private EmailMessage(final String to, final String subject, final String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachments = new ArrayList<>();
    }

    public static EmailMessage buildMessage(final String to, final String subject,
                                            final String body) {
        if (to == null) {
            Log.e("Email Message", "Email cannot have a null 'to' address");
            return null;
        }
        if (subject == null) {
            Log.e("Email Message", "Email to " + to + " cannot have a null subject");
            return null;
        }
        if (body == null) {
            Log.e("Email Message", "Email to " + to + " cannot have a null body");
            return null;
        }
        return new EmailMessage(to, subject, body);
    }

    private boolean addAttachmentFile(final String fname) {
        return addAttachmentFile(fname, false);
    }

    private boolean addAttachmentFile(final String fname, final boolean deleteAfter) {
        if (LocalStorageIO.fileExists(fname)) {
            File path = MyApplication.getContext().getFilesDir();
            File file = new File(path, fname);
            attachments.add(new AttachmentFile(Uri.fromFile(file), deleteAfter));
            return true;
        } else {
            Log.e("Email Attachment", "File not found");
        }
        return false;
    }

    public boolean addAttachmentFromText(final String text) {
        LocalStorageIO.writeSingleLineFile(TEMP_FNAME, text);
        return addAttachmentFile(TEMP_FNAME, true);
    }
}
