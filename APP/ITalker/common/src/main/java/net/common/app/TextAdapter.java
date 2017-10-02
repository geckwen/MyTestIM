package net.common.app;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by CLW on 2017/10/2.
 */

public  abstract class TextAdapter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
