package com.will_code_for_food.crucentralcoast.model.common.messaging;

import android.content.Context;

import com.will_code_for_food.crucentralcoast.controller.api_interfaces.SMSHandler;

/**
 * Notifies a user of a message via SMS
 */
public class SMSNotifier extends Notifier {

    public SMSNotifier(final Context context, final String phoneNumber) {
        super(context, null, phoneNumber);
    }

    @Override
    public boolean notify(final String msg) {
        return SMSHandler.sendSMS(getContext(), getContactInfo(), msg);
    }
}
