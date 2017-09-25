package net.italker.cilent.fragment.account;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.common.app.PresentFragment;
import net.factory.present.present.account.RegisterContract;
import net.factory.present.present.account.RegisterPresent;
import net.italker.cilent.R;
import net.italker.cilent.activity.MainActivity;
import net.qiujuer.genius.ui.widget.Loading;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册的fragment
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends PresentFragment<RegisterContract.Present> implements RegisterContract.View {
    private  AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mEditPhone;

    @BindView(R.id.edit_password)
    EditText mEditPassword;

    @BindView(R.id.edit_name)
    EditText mEditName;

    @BindView(R.id.txt_go_login)
    TextView mTextViewLogin;

    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @BindView(R.id.loading)
    Loading mLoading;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Present initPresent() {
        return new RegisterPresent(this);
    }

    @Override
    protected int getContentLayoutId() {

        return R.layout.fragment_registerragment;
    }


    /**
     * 注册提交按钮的事件
     */
    @OnClick(R.id.btn_submit)
    void onSubmitOnclick()
    {
        //获得相对应的值
        String phone = mEditPhone.getText().toString();
        String password = mEditPassword.getText().toString();
        String name = mEditName.getText().toString();
        //present进行model的处理
        mPresent.Register(phone,name,password);
    }

    /**
     * 切换至Login界面
     */
    @OnClick(R.id.txt_go_login)
    void toLoginFragemtn()
    {
        mAccountTrigger.trigger();
    }

    /**
     * 显示错误时的操作
     * @param str 要显示的资源的int值
     */
    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        //当显示错误时触发
        //停止loading
        mLoading.stop();
        //让控件可以输入
        mEditPhone.setEnabled(true);
        mEditName.setEnabled(true);
        mEditPassword.setEnabled(true);
        //让提交按钮可以再次提交
        mBtnSubmit.setEnabled(true);
    }

    /**
     * 界面正在注册
     */
    @Override
    public void showLoading() {
        super.showLoading();
        //开始loading
        mLoading.start();
        //让控件不可以输入
        mEditPhone.setEnabled(false);
        mEditName.setEnabled(false);
        mEditPassword.setEnabled(false);
        //让提交按钮不可以再次提交
        mBtnSubmit.setEnabled(false);
    }

    @Override
    public void registerSuccess() {
        //注册成功，用户已经登录
        //跳转到activity
        MainActivity.show(getActivity());
        getActivity().finish();
    }
}
