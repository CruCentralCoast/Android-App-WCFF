package com.will_code_for_food.crucentralcoast.model.community_groups;

import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;

import java.util.ArrayList;

/**
 * An ArrayList wrapper that holds all the information needed for
 * a community group form.
 */
public class CommunityGroupForm extends ArrayList<CommunityGroupForm> {
    private final Ministry ministry;

    public CommunityGroupForm(final Ministry ministry) {
        this.ministry = ministry;
    }
}
