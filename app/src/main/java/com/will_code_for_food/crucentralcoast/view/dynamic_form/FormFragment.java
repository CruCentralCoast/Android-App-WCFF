package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.common.Campus;
import com.will_code_for_food.crucentralcoast.model.common.common.DBObjectLoader;
import com.will_code_for_food.crucentralcoast.model.common.common.Ministry;
import com.will_code_for_food.crucentralcoast.model.common.common.Util;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;
import com.will_code_for_food.crucentralcoast.model.community_groups.CommunityGroupForm;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryQuestionRetriever;
import com.will_code_for_food.crucentralcoast.values.Android;
import com.will_code_for_food.crucentralcoast.view.common.CruFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A form that can be dynamically build from a Form object
 */
public class FormFragment extends CruFragment {
    private List<FormElementFragment> fragments;
    private Form form;
    private String ministryID;
    private ListView listView;
    private Button submitButton;

    // TODO may need ui methods (onCResume, etc)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle args = getArguments();
        // TODO the form needs to be passed in as an argument
        ministryID = args.getString("ministryID");
        form = new Form() {
            @Override
            public List<FormValidationResult> isValidDetailed() {
                return null;
            }

            @Override
            public boolean submit() {
                return false;
            }
        };
        Question q1 = new Question("Question 1", "What is your name?", QuestionType.FREE_RESPONSE_SHORT);
        Question q2 = new Question("Question 2", "Are you cool?", QuestionType.TRUE_FALSE);
        form.addQuestion(q1);
        form.addQuestion(q2);

        Log.e("QUESTION #", Integer.toString(form.getQuestions().size()));

        listView = (ListView) view.findViewById(R.id.question_list_view);
        submitButton = (Button) view.findViewById(R.id.question_submit);

        //loadForm(form);
        new FormTask(getActivity()).execute();

        return view;
    }

    public void loadForm(final Form form) {
        fragments = new ArrayList<>();
        if (form != null) {
            for (Question question : form.getQuestions()) {
                switch (question.getType()) {
                    case FREE_RESPONSE_SHORT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case FREE_RESPONSE_LONG:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case MAP_SELECTION:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case MULTI_OPTION_SELECT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case TIME_SELECT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case TRUE_FALSE:
                        fragments.add(null); //TODO load real fragment
                        break;
                    case NUMBER_SELECT:
                        fragments.add(null); //TODO load real fragment
                        break;
                    default:
                        Logger.e("DYNAMIC FORM", "Invalid Question Type");
                }
            }
        } else {
            Logger.e("DYNAMIC FORM", "Form should not be null");
        }
    }

    // TODO this needs to be linked to the submit button of the form
    public void pressSubmmitButton() {
        if (form != null) {
            form.clear();
            for (int ndx = 0; ndx < fragments.size(); ndx++) {
                form.answerQuestion(ndx, fragments.get(ndx).getAnswer());
            }
            if (form.isFinished()) {
                // form done, submitting
                form.submit();
            } else {
                List<FormValidationResult> results = form.isFinishedDetailed();
                for (FormValidationResult result : results) {
                    fragments.get(result.questionIndex).showError(result);
                }
            }
        } else {
            Logger.e("DYNAMIC FORM", "Form should not be null");
        }
    }

    private class FormFragmentAdapter extends ArrayAdapter<Question> {

        private List<Question> questionList;
        private Context context;

        public FormFragmentAdapter(List<Question> questionList, Context context) {
            super(context, R.layout.fragment_ministry_setup_card, questionList);
            this.questionList = questionList;
            this.context = context;
        }

        /**
         * Fixes a weird bug in ListView that makes the list items change around when you scroll.
         */
        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        /**
         * Fixes a weird bug in ListView that makes the list items change around when you scroll.
         */
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Question question = questionList.get(position);
            LayoutInflater inflater = LayoutInflater.from(context);
            switch (question.getType()) {
                case FREE_RESPONSE_SHORT:
                    convertView = inflater.inflate(R.layout.question_free_response, parent, false);
                    TextView questionText = (TextView) convertView.findViewById(R.id.question_text);
                    questionText.setText(question.getPrompt());
                    Log.e("PROMPT", question.getPrompt());
                    break;
                case FREE_RESPONSE_LONG:
                    convertView = inflater.inflate(R.layout.question_free_response, parent, false);
                    TextView questionTextFR = (TextView) convertView.findViewById(R.id.question_text);
                    questionTextFR.setText(question.getPrompt());
                    Log.e("PROMPT", question.getPrompt());
                    break;
                /*case MAP_SELECTION:
                    fragments.add(null); //TODO load real fragment
                    break;
                case MULTI_OPTION_SELECT:
                    fragments.add(null); //TODO load real fragment
                    break;*/
                /*case TIME_SELECT:
                    fragments.add(null); //TODO load real fragment
                    break;*/
                case TRUE_FALSE:
                    convertView = inflater.inflate(R.layout.yes_no_radio, parent, false);
                    TextView questionTextTF = (TextView) convertView.findViewById(R.id.question_text);
                    questionTextTF.setText(question.getPrompt());
                    Log.e("PROMPT", question.getPrompt());
                    break;
                /*case NUMBER_SELECT:
                    fragments.add(null); //TODO load real fragment
                    break;*/
                default:
                    Logger.e("DYNAMIC FORM", "Invalid Question Type");
            }

            return convertView;
        }
    }

    /**
     * Asynchronously retrieves a list of ministries from the database and puts them into the ListView
     * for this activity.
     */
    private class FormTask extends AsyncTask<Void, Void, Void> {
        Activity parent;
        CommunityGroupForm correct;

        public FormTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //HashMap<String, CommunityGroupForm> forms = MinistryQuestionRetriever.getAllCommunityGroupForms();
            //correct = forms.get(ministryID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //form = correct;
            Log.e("SETTING ADAPTER", "TRUE");
            listView.setAdapter(new FormFragmentAdapter(form.getQuestions(), parent));
            Log.e("FormTask", "Set Adapter");
        }
    }

}
