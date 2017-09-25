package net.italker.cilent.activity;



import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.PresentToolBarActivity;
import net.common.widget.recycle.a.PortraitView;
import net.factory.present.present.contact.PersonalContract;
import net.factory.present.present.contact.PersonalPresent;
import net.factory.model.db.User;
import net.italker.cilent.R;
import net.qiujuer.genius.ui.widget.Button;

import java.net.Socket;

import butterknife.BindView;
import butterknife.OnClick;


public class PersonalActivity extends PresentToolBarActivity<PersonalContract.Present> implements PersonalContract.View{

    public static final String BOUND_KEY_ID = "BOUND_KEY_ID";

    private String userId;

    @BindView(R.id.im_header)
    ImageView mHeader;
    @BindView(R.id.txt_name)
    TextView mName;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.txt_des)
    TextView mDes;
    @BindView(R.id.btn_say_hello)
    Button mBtnHello;
    @BindView(R.id.txt_follows)
    TextView mFollows;
    @BindView(R.id.txt_following)
    TextView mFollowing;


    private MenuItem mFollow;
    private boolean isFollow = false;

    public static void show(Context context, String userId)
    {
        Intent intent =new Intent(context,PersonalActivity.class);
        intent.putExtra(BOUND_KEY_ID,userId);
        context.startActivity(intent);
    }



    @Override
    public int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getString(BOUND_KEY_ID);
        return TextUtils.isEmpty(userId);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.personal,menu);
        mFollow = menu.findItem(R.id.action_follow);
        changerFollowStatus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_follow)
        {
            //TODO 进行关注操作


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 更改关注状态
     */
    private void changerFollowStatus()
    {
        if(mFollow==null)
            return;
        Drawable drawable = isFollow?getResources().getDrawable(R.drawable.ic_favorite):
                getResources().getDrawable(R.drawable.ic_favorite_border);
       drawable  =  DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,getResources().getColor(R.color.white));
        //重新设置
        mFollow.setIcon(drawable);
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void onLoadDone(User user) {
        if(user==null)
        return;

        mName.setText(user.getName());
        mDes.setText(user.getDesc());
        mPortrait.setPortraitView(Glide.with(this),user);
        mFollows.setText(String.format(getString(R.string.label_follows),user.getFollows()));
        mFollowing.setText(String.format(getString(R.string.label_following),user.getFollowing()));
        showHide();
    }

    @Override
    public void sayHello(boolean isFollow) {
            mBtnHello.setVisibility(isFollow?View.VISIBLE:View.GONE);
    }

    @Override
    public void setFollowStatus(boolean isFollow) {
            this.isFollow = isFollow;
            changerFollowStatus();
    }

    @Override
    protected PersonalContract.Present initPresent() {
        return new PersonalPresent(this);
    }

    @OnClick(R.id.btn_say_hello)
    void sayHelloMessage()
    {
        User user = mPresent.getAuthor();
        if(user==null)
            return;
        MessageActivity.show(this,user);
    }

}
