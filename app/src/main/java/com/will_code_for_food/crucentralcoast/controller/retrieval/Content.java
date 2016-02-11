package com.will_code_for_food.crucentralcoast.controller.retrieval;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;

import java.util.List;

/**
 * Created by MasonJStevenson on 2/9/2016.
 *
 * Wrapper class for content loaded from the database or otherwise.
 */
public class Content<T extends DatabaseObject> {
    private List<T> objects;
    private ContentType type;

    public Content(List<T> objects, ContentType type) {
        this.objects = objects;
        this.type = type;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }
}
