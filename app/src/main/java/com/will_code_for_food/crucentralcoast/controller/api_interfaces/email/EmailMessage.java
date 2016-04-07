package com.will_code_for_food.crucentralcoast.controller.api_interfaces.email;

import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;

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

    private static final String TEMP_FNAME = "Report.txt";

    private EmailMessage(final String to, final String subject, final String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachments = new ArrayList<>();
    }

    public static EmailMessage buildMessage(final String to, final String subject,
                                            final String body) {
        if (to == null) {
            Logger.e("Email Message", "Email cannot have a null 'to' address");
            return null;
        }
        if (subject == null) {
            Logger.e("Email Message", "Email to " + to + " cannot have a null subject");
            return null;
        }
        return new EmailMessage(to, subject, body != null ? body : "");
    }

    private boolean addAttachmentFile(final String fname) {
        return addAttachmentFile(fname, false);
    }

    private boolean addAttachmentFile(final String fname, final boolean deleteAfter) {
        if (LocalStorageIO.fileExists(fname)) {
            LocalStorageIO.copyToExternal(fname);
            File file = new File(Util.getContext().getExternalFilesDir(null), fname);
            attachments.add(new AttachmentFile(file, deleteAfter));
            return true;
        } else {
            Logger.e("Email Attachment", "File not found");
        }
        return false;
    }

    public boolean addAttachmentFromText(final String text) {
        LocalStorageIO.writeSingleLineFile(TEMP_FNAME, text);
        return addAttachmentFile(TEMP_FNAME, true);
    }
}
