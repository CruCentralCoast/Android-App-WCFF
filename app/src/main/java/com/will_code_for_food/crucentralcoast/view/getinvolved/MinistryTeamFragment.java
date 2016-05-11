package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.controller.retrieval.Retriever;
import com.will_code_for_food.crucentralcoast.controller.retrieval.RetrieverSchema;
import com.will_code_for_food.crucentralcoast.controller.retrieval.SingleMemoryRetriever;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.getInvolved.SummerMission;
import com.will_code_for_food.crucentralcoast.tasks.AsyncResponse;
import com.will_code_for_food.crucentralcoast.tasks.RetrievalTask;
import com.will_code_for_food.crucentralcoast.values.Database;
import com.will_code_for_food.crucentralcoast.view.common.CardFragmentFactory;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryTeam;

/**
 * Created by Brian on 2/19/2016.
 */
public class MinistryTeamFragment extends CruFragment {
    ListView listView;
    SwipeRefreshLayout layout;
    private int index, top;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        layout = (SwipeRefreshLayout) hold.findViewById(R.id.card_refresh_layout);
        listView = (ListView) hold.findViewById(R.id.list_cards);
        loadMinistryTeamList();
        return hold;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMinistryTeamList();
            }
        });
    }

    @Override
    public void onPause(){
        index = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : v.getTop();
        super.onPause();
    }

    private void loadMinistryTeamList() {
        SingleMemoryRetriever retriever = new SingleMemoryRetriever(Database.MINISTRY_TEAM);
        populateList(retriever);
    }

    private void refreshMinistryTeamList() {

        Logger.i("MinistryTeamFragment", "refreshing ministry team list");

        if (!DBObjectLoader.loadObjects(RetrieverSchema.MINISTRY_TEAM, Database.DB_TIMEOUT)) {
            Toast.makeText(getParent(), "Unable to refresh ministry teams", Toast.LENGTH_SHORT);
        }

        loadMinistryTeamList();
    }

    private void populateList(Retriever retriever) {
        CardFragmentFactory factory = new MinistryTeamCardFactory();
        new RetrievalTask<MinistryTeam>(retriever, factory,
                R.string.toast_no_ministry_teams, new AsyncResponse(getParent()) {
            @Override
            protected void otherProcessing() {
                layout.setRefreshing(false);
            }
        }).execute(index, top);
    }
}
