package net.italker.cilent.fragment.search;



import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.PresentFragment;
import net.common.widget.recycle.AdapterCallBack;
import net.common.widget.recycle.EmptyView;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.present.contact.FollowContract;
import net.factory.present.contact.FollowPresnet;
import net.factory.present.search.SearchContract;
import net.factory.present.search.UserPresent;
import net.factory.model.card.UserCard;
import net.italker.cilent.R;
import net.italker.cilent.activity.PersonalActivity;
import net.italker.cilent.activity.SearchActivity;
import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


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
            protected MyViewHolder onCreaterViewHolde(View mview, int viewType) {
                return new UserSearchFragment.MyHolder(mview,this);
            }

            @Override
            protected int getItemViewType(int position, UserCard data) {
                return R.layout.cell_search_list;
            }
        });
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    public class MyHolder extends RecycleAdapter.MyViewHolder<UserCard> implements
            FollowContract.FollowView {
        @BindView(R.id.search_portrait)
        PortraitView portraitView;
        @BindView(R.id.search_content)
        TextView textView;
        @BindView(R.id.im_follow)
        ImageView imageView;
        private FollowContract.Present presnet;

        public MyHolder(View itemView, AdapterCallBack adapterCallBack) {
            super(itemView, adapterCallBack);
            presnet = new FollowPresnet(this);
        }


        @Override
        protected void OnBind(UserCard data) {
            Glide.with(UserSearchFragment.this).load(data.getPortrait())
                    .centerCrop()
                    .into(portraitView);
            textView.setText(data.getName());
            imageView.setEnabled(!data.isfollow());
        }

        @OnClick(R.id.im_follow)
        void onFollowClik(){
            presnet.follow(mData.getId());
        }

        @OnClick(R.id.search_portrait)
        void onClikPortrait()
        {
            PersonalActivity.show(getContext(),mData.getId());
        }

        @Override
        public void showError(@StringRes int str) {
            if(imageView.getDrawable() instanceof LoadingDrawable) {
               LoadingDrawable loadingDrawable = ((LoadingDrawable) imageView.getDrawable());
                //二号方案 如果下面不能执行
                // loadingDrawable.setProgress(1);
                loadingDrawable.stop();
                //设置为默认的操作
                imageView.setEnabled(!mData.isfollow());

            }
        }

        @Override
        public void showLoading() {
            int minSize = (int) Ui.dipToPx(getResources(),22);
            int maxSize = (int) Ui.dipToPx(getResources(),30);
            //初始化圆形drawable
            LoadingDrawable drawable =new LoadingCircleDrawable(minSize,maxSize);
            drawable.setBackgroundColor(0);
            drawable.setForegroundColor(new int[]{getResources().getColor(R.color.white)});
            //将圆形设置进去
            imageView.setImageDrawable(drawable);
            drawable.start();

        }

        @Override
        public void setPresent(FollowContract.Present present) {
            this.presnet = present;
        }

        @Override
        public void followDone(UserCard userCard) {
            //更改drawable更改状态
            if(imageView.getDrawable() instanceof LoadingCircleDrawable) {
                ((LoadingCircleDrawable) imageView.getDrawable()).stop();
                //设置为默认的操作
                imageView.setImageResource(R.drawable.sel_opt_done_add);
                updataData(userCard);
            }
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
