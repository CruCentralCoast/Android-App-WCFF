package com.will_code_for_food.crucentralcoast.view.fragments;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.SummerMissionsActivity;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DatabaseObject;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.tasks.SummerMissionViewTask;
import com.will_code_for_food.crucentralcoast.values.UI;
import com.will_code_for_food.crucentralcoast.view.other.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.other.SummerMissionCardFactory;

/**
 * Created by Brian on 1/28/2016.
 */
public class SummerMissionFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);

        Retriever retriever = new SingleRetriever<SummerMission>(RetrieverSchema.SUMMER_MISSION);
        CardFragmentFactory factory = new SummerMissionCardFactory();
        new RetrievalTask<SummerMission>(retriever, factory, new SummerMissionViewTask(),
                R.id.list_summer_missions, R.string.toast_no_summer_missions).execute();
        return hold;
    }
}
