package net.italker.cilent.fragment.account;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

import net.common.app.BaseFragment;
import net.common.app.PresentFragment;
import net.factory.main.present.account.LoginContract;
import net.factory.main.present.account.LoginPresent;
import net.italker.cilent.R;
import net.italker.cilent.activity.MainActivity;
import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends PresentFragment<LoginContract.Present> implements LoginContract.View{
    @BindView(R.id.edit_phone)
    EditText mEditPhone;

    @BindView(R.id.edit_password)
    EditText mEditPassword;

    @BindView(R.id.txt_go_login)
    TextView mTxtGoLogin;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
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
    protected LoginContract.Present initPresent() {
        return new LoginPresent(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    //登录按钮绑定
    @OnClick(R.id.btn_submit)
    void onSubmitOnclick()
    {
        //获得相对应的值
        String phone = mEditPhone.getText().toString();
        String password = mEditPassword.getText().toString();
        //present进行model的处理
        mPresent.login(phone,password);
    }

    @Override
    public void loginSuccess() {
        //跳转到activity
        MainActivity.show(getActivity());
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //让控件不可以输入
        mLoading.start();
        mEditPhone.setEnabled(false);
        mEditPassword.setEnabled(false);
        //让提交按钮不可以再次提交
        mBtnSubmit.setEnabled(false);

    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        //当显示错误时触发
        //停止loading
        mLoading.stop();
        //让控件可以输入
        mEditPhone.setEnabled(true);
        mEditPassword.setEnabled(true);
        //让提交按钮可以再次提交
        mBtnSubmit.setEnabled(true);
    }

    /**
     * 进行界面的切换
     */
    @OnClick(R.id.txt_go_login)
    void changeRegister()
    {
        mAccountTrigger.trigger();
    }
}
