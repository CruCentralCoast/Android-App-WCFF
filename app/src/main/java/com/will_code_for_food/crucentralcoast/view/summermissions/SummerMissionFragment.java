package com.will_code_for_food.crucentralcoast.view.summermissions;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleRetriever;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;

/**
 * Created by Brian on 1/28/2016.
 */
public class SummerMissionFragment extends CruFragment {
    SwipeRefreshLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        layout = (SwipeRefreshLayout) hold.findViewById(R.id.missions_layout);
        refreshMissionsList();
        return hold;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMissionsList();
            }
        });
    }

    private void refreshMissionsList() {
        Retriever retriever = new SingleRetriever<SummerMission>(RetrieverSchema.SUMMER_MISSION);
        CardFragmentFactory factory = new SummerMissionCardFactory();
        new RetrievalTask<SummerMission>(retriever, factory, R.id.list_summer_missions,
                R.string.toast_no_summer_missions, new AsyncResponse(getParent()) {
            @Override
            protected void otherProcessing() {
                layout.setRefreshing(false);
            }
        }).execute();
    }
}
