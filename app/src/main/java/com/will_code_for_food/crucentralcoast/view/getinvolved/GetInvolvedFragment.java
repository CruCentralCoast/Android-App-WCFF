package com.will_code_for_food.crucentralcoast.view.getinvolved;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;
import com.will_code_for_food.crucentralcoast.view.common.MainActivity;

/**
 * Created by Kayla on 3/5/2016.
 */
public class GetInvolvedFragment extends CruFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View hold = super.onCreateView(inflater, container, savedInstanceState);
        new SetupTask().execute();
        return hold;
    }

    private class SetupTask extends AsyncTask<Void, Void, Void> {
        private MainActivity currentActivity;
        private ListView list;
        private String[] items = {"Ministry Teams", "Community Groups"};

        public SetupTask() {
            super();
            currentActivity = (MainActivity) GetInvolvedActivity.context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list = (ListView) currentActivity.findViewById(R.id.list_resources);
            list.setAdapter(new Adapter(GetInvolvedActivity.context,
                    android.R.layout.simple_list_item_1, items));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> p, View view, int position, long id) {
                    GetInvolvedActivity activity = (GetInvolvedActivity)getActivity();
                    if (position == 0) {
                        activity.viewMinistryTeams();
                    } else if (position == 1) {
                        activity.viewJoinCommunityGroup();
                    }
                }
            });
        }
    }

    private class Adapter extends ArrayAdapter {
        String[] items;

        public Adapter(Context context, int resource, Object[] objects) {
            super(context, resource, objects);
            items = (String[]) objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View hold = inflater.inflate(R.layout.fragment_card_small, parent, false);
            String current = items[position];

            ImageView imageView = (ImageView) hold.findViewById(R.id.card_image);
            if (position == 0) {
                imageView.setImageResource(R.drawable.ic_group_black_36dp);
            } else if (position == 1) {
                imageView.setImageResource(R.drawable.ic_group_add_black_36dp);
            }

            TextView textView = (TextView) hold.findViewById(R.id.card_text);
            textView.setText(current);

            return hold;
        }
    }
}
