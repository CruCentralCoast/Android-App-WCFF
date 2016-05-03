package com.will_code_for_food.crucentralcoast.model.community_groups;

import java.util.ArrayList;

/**
 * An ArrayList wrapper that holds all the information needed for
 * a community group form.
 */
public class CommunityGroupForm extends ArrayList<CommunityGroupQuestion> {
    private final String ministryId;

    public CommunityGroupForm(final String ministryId) {
        this.ministryId = ministryId;
    }
}
