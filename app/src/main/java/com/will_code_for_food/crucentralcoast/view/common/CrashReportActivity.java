package com.will_code_for_food.crucentralcoast.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.api_interfaces.email.EmailSender;
import com.will_code_for_food.crucentralcoast.controller.crash_reports.CrashReport;

/**
 * The activity that allows users to create and send bug reports
 */
public class CrashReportActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        loadFragmentById(R.layout.fragment_settings, "Bug Report", new CrashReportFragment(), this);
    }

    public void submit(View view) {
        EditText text = (EditText) this.findViewById(R.id.cr_user_message);
        EmailSender.send(this, new CrashReport(null, text.getText().toString()).asMessage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO delete temporary files
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
        // restart previous activity
        startActivity(new Intent(this, MainActivity.class));
    }
}
