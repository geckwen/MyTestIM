package net.italker.cilent.fragment.main;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.BaseFragment;
import net.common.app.PresentFragment;
import net.common.widget.recycle.AdapterCallBack;
import net.common.widget.recycle.EmptyView;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.model.db.Group;
import net.factory.model.db.User;
import net.factory.present.group.GroupContract;
import net.factory.present.group.GroupPrensenter;
import net.factory.present.search.GroupPresent;
import net.italker.cilent.R;
import net.italker.cilent.activity.MessageActivity;
import net.italker.cilent.activity.PersonalActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends PresentFragment<GroupContract.Present>
    implements GroupContract.GroupView{

    @BindView(R.id.contact)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    EmptyView emptyView;

    RecycleAdapter<Group> mAdapter;

    public GroupFragment() {
        // Required empty public constructor
    }



    @Override
    protected GroupContract.Present initPresent() {
        return new GroupPrensenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(mAdapter = new RecycleAdapter<Group>() {
            @Override
            protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
                return new GroupFragment.ViewHolder(view);
            }

            @Override
            protected int getItemViewType(int position, Group data) {
                return R.layout.cell_group_list;
            }
        });
        //绑定空布局
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
        mAdapter.registerClickListener(new RecycleAdapter.AdapterListenerImpl<Group>(){
            @Override
            public void onClick(RecycleAdapter.MyViewHolder holder, Group data) {
                super.onClick(holder, data);
                MessageActivity.show(getContext(),data);
            }
        });

    }

    @Override
    protected void initFirstData() {
        super.initFirstData();
        //开始初始化数据
        mPresent.start();

    }

    @Override
    public RecycleAdapter<Group> getRecycleAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataRefresh() {
        //进行界面操作
        mplaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }

    class ViewHolder extends RecycleAdapter.MyViewHolder<Group>{
        @BindView(R.id.im_portrait)
        PortraitView portraitView;
        @BindView(R.id.txt_name)
        TextView mNmae;
        @BindView(R.id.txt_des)
        TextView mDes;

        @BindView(R.id.txt_member)
        TextView mMembers;

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void OnBind(Group data) {
            portraitView.setPortraitView(Glide.with(GroupFragment.this),data.getPicture());
            mNmae.setText(data.getName());
            mDes.setText(data.getDescription());

            if( data.holder==null && data.holder instanceof String)
            {
                mMembers.setText((String)data.holder);
            }else {
                mMembers.setText("");
            }
        }


    }


}
