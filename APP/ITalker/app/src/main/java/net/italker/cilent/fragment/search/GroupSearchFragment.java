package net.italker.cilent.fragment.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igexin.push.core.g;

import net.common.app.PresentFragment;
import net.common.widget.recycle.AdapterCallBack;
import net.common.widget.recycle.EmptyView;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.model.card.GroupCard;
import net.factory.model.card.UserCard;
import net.factory.model.db.Group;
import net.factory.present.contact.FollowContract;
import net.factory.present.contact.FollowPresnet;
import net.factory.present.search.GroupPresent;
import net.factory.present.search.SearchContract;
import net.italker.cilent.activity.PersonalActivity;
import net.italker.cilent.activity.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import net.italker.cilent.R;




public class GroupSearchFragment extends PresentFragment<SearchContract.Present> implements
        SearchActivity.SearchFragment,SearchContract.GroupView{

    @BindView(R.id.group_search_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;

    private RecycleAdapter<GroupCard> mAdapter;

    @Override
    protected SearchContract.Present initPresent() {
        return new GroupPresent(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group_search;
    }


    @Override
    public void initData() {
        super.initData();
        search("");
    }

    @Override
    public void search(String content) {
        mPresent.search(content);
    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter = new RecycleAdapter<UserCard>() {
            @Override
            protected MyViewHolder onCreaterViewHolde(View mview, int viewType) {
                return new GroupSearchFragment().ViewHolder(mview,this);
            }

            @Override
            protected int getItemViewType(int position, UserCard data) {
                return R.layout.cell_search_list;
            }
        });

        public class ViewHolder extends RecycleAdapter.MyViewHolder<GroupCard>{
            @BindView(R.id.search_portrait)
            PortraitView portraitView;
            @BindView(R.id.search_content)
            TextView textView;
            @BindView(R.id.im_join)
            ImageView mJoin;


            public ViewHolder(View itemView, AdapterCallBack adapterCallBack) {
                super(itemView, adapterCallBack);
            }


            @Override
            protected void OnBind(GroupCard data) {
                Glide.with(GroupSearchFragment.this)
                        .load(data.getPicture())
                        .centerCrop()
                        .into(portraitView);
                textView.setText(data.getName());
                //加入时间判断是否加入群
                mJoin.setEnabled(data.getJoinAt()==null);
            }

            @OnClick(R.id.im_join)
            void onJoinClick(){
                //进入创建者的界面
                PersonalActivity.show(getContext(), mData.getOwnerId());
            }}
    }

    @Override
    public void onSearchDone(List<GroupCard> groupCards){
        //搜索成功后回调这个函数
        mAdapter.replace(groupCards);
        //如果有数据,则是ok，没有数据则显示空布局
        mplaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }




}
