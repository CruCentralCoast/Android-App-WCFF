package com.will_code_for_food.crucentralcoast.controller.api_interfaces.email;

import android.net.Uri;

import java.io.File;

/**
 * A file to be attached to an outgoing email message
 */
public class AttachmentFile {
    public final File file;
    public final boolean deleteAfterwards;

    public AttachmentFile(final File file) {
        this(file, false);
    }

    public AttachmentFile(final File file, final boolean deleteAfterwards) {
        this.file = file;
        this.deleteAfterwards = deleteAfterwards;
    }
}
