package com.will_code_for_food.crucentralcoast.view.resources;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.resources.Resource;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

/**
 * Created by Brian on 2/16/2016.
 */
public class ResourceArticleFragment extends CruFragment implements TextView.OnEditorActionListener {

    ListView listView;
    private int index, top;
    private CardFragmentFactory factory;

    public void setFactory (CardFragmentFactory aFactory) {
        factory = aFactory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        listView = (ListView) fragmentView.findViewById(R.id.list_cards);

        Retriever retriever = new SingleRetriever<Resource>(RetrieverSchema.RESOURCE);
        new RetrievalTask<Resource>(retriever, factory, R.string.toast_no_articles).execute(index, top);

        return fragmentView;
    }

    @Override
    public void onPause(){
        index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : v.getTop();
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setActionView(R.layout.action_search);
        final MenuItem sortItem = menu.findItem(R.id.sort);
        final EditText search = (EditText) searchItem.getActionView().findViewById(R.id.text);
        search.setOnEditorActionListener(this);
        search.setImeActionLabel(Util.getString(R.string.search_title), KeyEvent.KEYCODE_ENTER);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                sortItem.setVisible(false);
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                ((ArticleCardAdapter) listView.getAdapter()).clearSearch();
                search.setText("");
                sortItem.setVisible(true);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_newest) {
            ((ArticleCardAdapter) listView.getAdapter()).sortByNewest();
            return true;
        } else if (item.getItemId() == R.id.sort_oldest) {
            ((ArticleCardAdapter) listView.getAdapter()).sortByOldest();
            return true;
        } else if (item.getItemId() == R.id.sort_type) {
            ((ArticleCardAdapter) listView.getAdapter()).sortByType();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event == null || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            ((ArticleCardAdapter) listView.getAdapter()).search(v.getText().toString());
            InputMethodManager imm = (InputMethodManager) getParent().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(listView.getWindowToken(), 0);
        }
        return true;
    }
}
