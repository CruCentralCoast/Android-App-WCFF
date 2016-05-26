package com.will_code_for_food.crucentralcoast.view.dynamic_form;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;
import com.will_code_for_food.crucentralcoast.controller.Logger;
import com.will_code_for_food.crucentralcoast.model.common.form.Form;
import com.will_code_for_food.crucentralcoast.model.common.form.FormValidationResult;
import com.will_code_for_food.crucentralcoast.model.common.form.Question;
import com.will_code_for_food.crucentralcoast.model.common.form.QuestionType;
import com.will_code_for_food.crucentralcoast.model.getInvolved.MinistryQuestionRetriever;
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pressSubmmitButton();
            }
        });

        //new FormTask(getActivity()).execute();
        listView.setAdapter(new FormFragmentAdapter(form.getQuestions(), getActivity()));

        return view;
    }


    // TODO this needs to be linked to the submit button of the form
    public void pressSubmmitButton() {
        /*if (form != null) {
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
        }*/
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
            final Question question = questionList.get(position);
            LayoutInflater inflater = LayoutInflater.from(context);
            switch (question.getType()) {
                case FREE_RESPONSE_SHORT:
                    convertView = inflater.inflate(R.layout.question_free_response, parent, false);
                    TextView questionText = (TextView) convertView.findViewById(R.id.question_text);
                    EditText questionAnswer = (EditText) convertView.findViewById(R.id.question_answer);
                    questionText.setText(question.getPrompt());
                    questionAnswer.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            question.answerQuestion(s.toString());
                        }
                    });
                    Log.e("PROMPT", question.getPrompt());
                    break;
                case FREE_RESPONSE_LONG:
                    convertView = inflater.inflate(R.layout.question_free_response, parent, false);
                    TextView questionTextFR = (TextView) convertView.findViewById(R.id.question_text);
                    questionTextFR.setText(question.getPrompt());
                    EditText questionAnswerFR = (EditText) convertView.findViewById(R.id.question_answer);
                    questionAnswerFR.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            question.answerQuestion(s.toString());
                        }
                    });
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
                    RadioButton yesButton = (RadioButton) convertView.findViewById(R.id.Yes);
                    RadioButton noButton = (RadioButton) convertView.findViewById(R.id.No);
                    yesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            question.answerQuestion("Yes");
                        }
                    });
                    noButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            question.answerQuestion("No");
                        }
                    });
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
        Form correct;

        public FormTask(Activity newParent) {
            parent = newParent;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("BACKGROUND", "RETRIEVING QUESTIONS");
            HashMap<String, Form> forms = MinistryQuestionRetriever.getAllCommunityGroupForms();
            correct = forms.get(ministryID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            form = correct;
            Log.e("SETTING ADAPTER", "TRUE");
            listView.setAdapter(new FormFragmentAdapter(form.getQuestions(), parent));
            Log.e("FormTask", "Set Adapter");
        }
    }

}
