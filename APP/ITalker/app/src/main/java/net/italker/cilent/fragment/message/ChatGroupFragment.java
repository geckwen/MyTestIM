package net.italker.cilent.fragment.message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.common.app.BaseFragment;
import net.factory.model.db.Group;
import net.factory.model.view.MemberUserModel;
import net.factory.present.message.ChatContract;
import net.factory.present.message.ChatGroupPresent;
import net.italker.cilent.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatGroupFragment extends ChatFragment<Group> implements ChatContract.GroupView {


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

    }



    @Override
    protected void initEditContent() {

    }

    @Override
    public void init(Group group) {

    }

    @Override
    public void showAdminOption(boolean isAdmin) {

    }

    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, int moreCount) {

    }
}
