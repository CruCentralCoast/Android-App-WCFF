package com.will_code_for_food.crucentralcoast.view.common;

/**
 * Created by mallika on 12/2/15.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.will_code_for_food.crucentralcoast.R;

public class CruFragment extends Fragment {

    public String name;
    private MainActivity parent;
    // add to an object to
    private View.OnFocusChangeListener hideKeyboardOnUnfocusListener;
    private TextView.OnEditorActionListener hideKeyboardOnEnterKeyListener;

    public void setParent(MainActivity parent) {
        this.parent = parent;
    }

    public MainActivity getParent() {
        return parent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        //these arguments should have always been passed by using loadFragmentById
        //if not you need to modify stuff
        int id = args.getInt("id", R.layout.fragment_main);
        name = args.getString("name", "");
        return inflater.inflate(id, container, false);
    }

    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getParent().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View curFocus = getParent().getCurrentFocus();
        if (curFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(curFocus.getWindowToken(), 0);
        }
    }

    protected void hideKeyboardOnUnfocus(final View... elements) {
        if (hideKeyboardOnUnfocusListener == null) {
            hideKeyboardOnUnfocusListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.e("Focus change", "" + hasFocus);
                    if (!hasFocus) {
                        hideKeyboard();
                    }
                }
            };
        }
        for (View element : elements) {
            element.setOnFocusChangeListener(hideKeyboardOnUnfocusListener);
        }
    }
    protected void unfocusOnEnterKey(final TextView... views) {
        if (hideKeyboardOnEnterKeyListener == null) {
            hideKeyboardOnEnterKeyListener = new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                        Log.e("Hit enter!!!", "Woo!");
                        view.clearFocus();
                    }
                    return true;
                }
            };
        }
        for (TextView view : views) {
            view.setOnEditorActionListener(hideKeyboardOnEnterKeyListener);
        }
    }
}
