package com.will_code_for_food.crucentralcoast.model.common.common.users;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

/**
 * Created by Gavin on 2/24/2016.
 */
public enum Gender {
    MALE (R.string.gender_male),
    FEMALE (R.string.gender_female);

    private final int nameId;

    Gender(final int nameId) {
        this.nameId = nameId;
    }

    public static Gender getFromName(final String query) {
        if (query != null) {
            for (Gender g : values()) {
                if (query.equals(Util.getString(g.nameId))) {
                    return g;
                }
            }
        }
        return null;
    }

    public int getNameId() {
        return nameId;
    }

    public static Gender getUsersGender() {
        String genString = LocalStorageIO.readSingleLine(LocalFiles.USER_GENDER);
        return getFromName(genString);
    }
}
