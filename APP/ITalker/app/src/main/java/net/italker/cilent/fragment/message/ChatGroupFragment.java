package net.italker.cilent.fragment.message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.common.app.BaseFragment;
import net.factory.model.db.Group;
import net.factory.model.view.MemberUserModel;
import net.factory.present.message.ChatContract;
import net.factory.present.message.ChatGroupPresent;
import net.italker.cilent.R;
import net.italker.cilent.activity.GroupMemberActivity;
import net.italker.cilent.activity.PersonalActivity;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatGroupFragment extends ChatFragment<Group> implements ChatContract.GroupView {

    @BindView(R.id.im_header)
    ImageView mHeader;

    @BindView(R.id.lay_members)
    LinearLayout mLayMembers;

    @BindView(R.id.txt_member_more)
    TextView mMembermore;
    public ChatGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.lay_chat_header_group;
    }



    @Override
    protected ChatContract.Present initPresent() {
        return new ChatGroupPresent(mReceiverId,this);
    }




    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        View view = mLayMembers;
        if(view==null)
            return;

        if(verticalOffset==0)
        {
            //完全展开
            view.setVisibility(View.VISIBLE);
            //设置缩放
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);

        }else{
            //abs计算
            verticalOffset = Math.abs(verticalOffset);
            final int totalScrollRange= appBarLayout.getTotalScrollRange();
            if(verticalOffset>=totalScrollRange)
            {
                //完全关闭
                view.setVisibility(View.INVISIBLE);
                //设置缩放
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);


            }else{
                //中间状态
                float progress =1- verticalOffset/(float)totalScrollRange;
                view.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);

            }
        }
    }



    @Override
    protected void initEditContent() {

    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        Glide.with(this)
                .load(R.drawable.default_banner_group)
                .centerCrop()
                .into(new ViewTarget<CollapsingToolbarLayout,GlideDrawable>(mCollapsingToolbarLayout) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //这个尽可能完整
                        this.view.setContentScrim(resource.getCurrent());
                    }
                });

    }


    @Override
    public void init(Group group) {
        mCollapsingToolbarLayout.setTitle(group.getName());
        Glide.with(this)
                .load(group.getPicture())
                .centerCrop()
                .placeholder(R.drawable.default_banner_group)
                .into(mHeader);
    }

    @Override
    public void showAdminOption(boolean isAdmin) {
        if(isAdmin){
            mToolbar.inflateMenu(R.menu.chat_group_item);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.action_create)
                    {
                        //mReceiverId就是群的Id
                        GroupMemberActivity.show(getContext(),mReceiverId);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, int moreCount) {
        if(members == null || members.size()==0)
            return;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (final MemberUserModel memberUserModel : members)
        {
            //添加成员头像
            ImageView p  = (ImageView) inflater.inflate(R.layout.lay_chat_group_portrait,mLayMembers,false);
            mLayMembers.addView(p,0);
            Glide.with(this)
                    .load(memberUserModel.getPortrait())
                    .placeholder(R.drawable.default_portrait)
                    .centerCrop()
                    .dontAnimate()
                    .into(p);
            //个人界面信息查看
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalActivity.show(getContext(),memberUserModel.getUserId());
                }
            });
        }

        //更多按钮
        if(moreCount>0){
            mMembermore.setText(String.format("+%s",moreCount));
            mMembermore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupMemberActivity.showAdmin(getContext(),mReceiverId);
                }
            });
        }else{
            mMembermore.setVisibility(View.GONE);
        }
    }
}
