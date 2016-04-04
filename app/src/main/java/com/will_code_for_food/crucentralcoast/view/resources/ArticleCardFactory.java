package com.will_code_for_food.crucentralcoast.view.resources;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.authentication.Authenticator;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by Brian on 2/16/2016.
 */
public class ArticleCardFactory implements CardFragmentFactory {
    @Override
    public boolean include(DatabaseObject object) {
        Resource resource = (Resource) object;

        if (resource != null) {
            return !resource.isRestricted();
        }
        return false;
    }

    @Override
    public ArrayAdapter createAdapter(Content cardObjects) {
        return new ArticleCardAdapter(ResourcesActivity.context, cardObjects);
    }

    @Override
    public AdapterView.OnItemClickListener createCardListener(final MainActivity currentActivity,
                                                              final Content myDBObjects) {
        return new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                ResourcesActivity.selectedResource = (Resource) myDBObjects.getObjects().get(position);
                currentActivity.loadFragmentById(R.layout.fragment_article,
                        currentActivity.getTitle() + " > " + ResourcesActivity.selectedResource.getTitle(),
                        new ViewArticleFragment(), currentActivity);
            }
        } ;
    }
}
