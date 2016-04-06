package com.will_code_for_food.crucentralcoast.model.resources;

import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.view.resources.ArticleCardFactory;

/**
 * Created by Kayla on 4/4/2016.
 */
public class LeaderArticleCardFactory extends ArticleCardFactory {
    @Override
    public boolean include(DatabaseObject object) {
        Resource resource = (Resource) object;

        if (resource != null) {
            return resource.isRestricted();
        }
        return false;
    }
}
