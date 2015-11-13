package com.will_code_for_food.crucentralcoast.model.common.list;

import android.media.Image;

/**
 * Created by Gavin on 11/12/2015.
 */
public interface ListElement {
    String getId();
    String getName();
    Image getImage();
    String getDescription();
    void loadFullObject();
}
