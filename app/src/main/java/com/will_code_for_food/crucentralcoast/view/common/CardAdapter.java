package com.will_code_for_food.crucentralcoast.view.common;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.will_code_for_food.crucentralcoast.controller.retrieval.Content;
import com.will_code_for_food.crucentralcoast.controller.retrieval.ContentType;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.DatabaseObjectSorter;
import com.will_code_for_food.crucentralcoast.model.common.common.sorting.SortMethod;

/**
 * Created by Kayla on 2/29/2016.
 */
public class CardAdapter extends ArrayAdapter<DatabaseObject> {

    protected Content<DatabaseObject> cards;
    protected Content<DatabaseObject> cardsTemp;

    public CardAdapter(Context context, int resource, Content<DatabaseObject> content) {
        super(context, resource, content);
        cards = content;
        cardsTemp = cards;
    }

    @Override
    public int getCount() {
        return cards != null? cards.size() : 0;
    }

    public void search(String phrase) {
        cardsTemp = cards;
        cards = DatabaseObjectSorter.filterByName(cards, phrase);
        this.notifyDataSetChanged();
    }

    public void clearSearch() {
        cards = cardsTemp;
        this.notifyDataSetChanged();
    }

    public void sortByNewest() {
        DatabaseObjectSorter.sortByDate(cards, SortMethod.DESCENDING);
        this.notifyDataSetChanged();
    }

    public void sortByOldest() {
        DatabaseObjectSorter.sortByDate(cards, SortMethod.ASCENDING);
        this.notifyDataSetChanged();
    }

    public void sortByType() {
        DatabaseObjectSorter.sortFeedObjectsByType(cards, SortMethod.DESCENDING);
        this.notifyDataSetChanged();
    }
}
