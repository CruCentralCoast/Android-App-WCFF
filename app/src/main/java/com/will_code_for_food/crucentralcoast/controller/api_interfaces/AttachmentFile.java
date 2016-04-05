package com.will_code_for_food.crucentralcoast.controller.api_interfaces;

import android.net.Uri;

/**
 * A file to be attached to an outgoing email message
 */
public class AttachmentFile {
    public final Uri uri;
    public final boolean deleteAfterwards;

    public AttachmentFile(final Uri uri) {
        this(uri, false);
    }

    public AttachmentFile(final Uri uri, final boolean deleteAfterwards) {
        this.uri = uri;
        this.deleteAfterwards = deleteAfterwards;
    }
}
