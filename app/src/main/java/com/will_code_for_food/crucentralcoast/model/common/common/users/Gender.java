package com.will_code_for_food.crucentralcoast.model.common.common.users;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.LocalStorageIO;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.values.LocalFiles;

/**
 * Created by Gavin on 2/24/2016.
 */
public enum Gender {
    MALE (R.string.gender_male, 1),
    FEMALE (R.string.gender_female, 2);

    private final int nameId;
    private final int value;

    Gender(final int nameId, int value) {
        this.nameId = nameId;
        this.value = value;
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
    public int getValue() {
        return value;
    }

    public static Gender getUsersGender() {
        String genString = LocalStorageIO.readSingleLine(LocalFiles.USER_GENDER);
        return getFromName(genString);
    }
}
