package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasonJStevenson on 2/9/2016.
 *
 * Wrapper class for content loaded from the database or otherwise.
 */
public class Content<T extends DatabaseObject> extends ArrayList<T> {
    //private List<T> objects;
    private ContentType type;

    public Content(List<T> objects, ContentType type) {
        //this.objects = objects;
        this.addAll(objects);
        this.type = type;
    }

    /**
     * DEPRECATED
     */
    public List<T> getObjects() {
        return this;
    }

    public ContentType getType() {
        return type;
    }
}
