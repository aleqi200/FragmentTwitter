package com.codepath.apps.adroidtweet.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by acampelo on 11/8/15.
 */
public class TextWatcherComposeMenu implements TextWatcher {

    private final MenuItem countView;
    private final MenuItem saveButton;


    public TextWatcherComposeMenu(MenuItem countView, MenuItem saveButton) {
        this.countView = countView;
        this.saveButton = saveButton;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //This sets a textview to the current length
        int remaining = 140 - s.length();
        if (remaining <= 0) {
            countView.setTitle(String.valueOf(0));
            saveButton.setEnabled(false);
        } else if (remaining == 140) {
            saveButton.setEnabled(false);
        } else {
            if (!saveButton.isEnabled()) {
                saveButton.setEnabled(true);
            }
            countView.setTitle(String.valueOf(remaining));
        }

    }

    public void afterTextChanged(Editable s) {

    }
}
