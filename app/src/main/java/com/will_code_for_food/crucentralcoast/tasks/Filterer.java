package com.will_code_for_food.crucentralcoast.tasks;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;

/**
 * Filters a Content object
 */
public interface Filterer {
    Content filter(final Content content);
}
