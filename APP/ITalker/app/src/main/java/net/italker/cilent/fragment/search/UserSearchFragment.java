package net.italker.cilent.fragment.search;



import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.BaseFragment;
import net.common.app.PresentFragment;
import net.common.widget.recycle.EmptyView;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.main.present.search.SearchContract;
import net.factory.main.present.search.UserPresent;
import net.factory.model.card.UserCard;
import net.italker.cilent.R;
import net.italker.cilent.activity.SearchActivity;

import java.util.List;

import butterknife.BindView;


public class UserSearchFragment extends PresentFragment<SearchContract.Present>
        implements SearchActivity.SearchFragment,SearchContract.UserView {

    @BindView(R.id.user_search_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    EmptyView emptyView;

    private RecycleAdapter<UserCard> mAdapter;

    @Override
    protected SearchContract.Present initPresent() {
        return new UserPresent(this);
    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter = new RecycleAdapter<UserCard>() {
            @Override
            protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
                return new UserSearchFragment.MyHolder(view);
            }

            @Override
            protected int getItemViewType(int position, UserCard data) {
                return R.layout.search_item;
            }
        });
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    public class MyHolder extends RecycleAdapter.MyViewHolder<UserCard>{
        @BindView(R.id.search_portrait)
        PortraitView portraitView;
        @BindView(R.id.search_content)
        TextView textView;
        @BindView(R.id.im_follow)
        ImageView imageView;


        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void OnBind(UserCard data) {
            Glide.with(UserSearchFragment.this).load(data.getPortrait())
                    .centerCrop()
                    .into(portraitView);
            textView.setText(data.getName());
            imageView.setEnabled(data.isfollow());
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_user_search;
    }

    @Override
    public void search(String query) {

    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {
        //搜索成功后回调这个函数
        mAdapter.replace(userCards);
        //如果有数据,则是ok，没有数据则显示空布局
        mplaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }


}
