package net.italker.cilent.fragment.account;


import android.content.Context;
import android.support.v4.app.Fragment;

import net.common.app.BaseFragment;
import net.italker.cilent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment{
    private  AccountTrigger mAccountTrigger;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }
}
