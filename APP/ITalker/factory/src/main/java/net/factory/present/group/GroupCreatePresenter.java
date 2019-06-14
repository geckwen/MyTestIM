package net.factory.present.group;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import net.common.factory.data.DataSource;
import net.common.factory.present.BaseRecyclePresent;
import net.factory.data.Helper.ContactHelper;
import net.factory.data.Helper.GroupHelper;
import net.factory.model.card.GroupCard;
import net.factory.model.view.UserSampleModel;
import net.factory.model.group.GroupCreateModel;
import net.factory.net.UploadHelper;
import net.factory.present.Factory;
import net.lang.R;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 群创建界面的Presenter
 * Created by CLW on 2019/5/31.
 */

public class GroupCreatePresenter extends BaseRecyclePresent<GroupCreateContract.ViewModel,GroupCreateContract.View>
        implements GroupCreateContract.Presenter,DataSource.Callback<GroupCard> {

    private Set<String> users = new HashSet<>();
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        //加载
        Factory.runAsync(loader);
    }

    @Override
    public void create(String name, final String desc, final String picture) {
        GroupCreateContract.View view = getmView();
        view.showLoading();
        //判断参数
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(desc)||TextUtils.isEmpty(picture)) {
            view.showError(R.string.label_group_create_invalid);
            return;
        }
        //上传图片
        Factory.runAsync(new Runnable() {
            @Override
            public void run() {
                String url = uploadPicture(picture);
                if(TextUtils.isEmpty(url)) {
                    return;
                }
                //运行网络请求
                GroupCreateModel model = new GroupCreateModel(name,desc,url,users);
                GroupHelper.create(model,GroupCreatePresenter.this);
            }
        });
        //请求接口
        //处理回调
    }

    //同步上传操作
    private String uploadPicture(String path){
        String url = UploadHelper.uploadPortrait(path);
        if(TextUtils.isEmpty(url)){
            //切换到UI线程 提示信息
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    GroupCreateContract.View view = getmView();
                    if(view!=null) {
                        view.showError(R.string.data_network_error);
                    }
                }
            });
        }
        return url;
    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
            if(isSelected)
                users.add(model.author.getId());
            else
                users.remove(model.author.getId());
    }


    private  Runnable loader = new Runnable() {
        @Override
        public void run() {
            List<UserSampleModel> userSampleModels = ContactHelper.getContact();
            List<GroupCreateContract.ViewModel> viewModels = new ArrayList<>();
            for(UserSampleModel userSampleModel : userSampleModels)
            {
                GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
                viewModel.author = userSampleModel;
                viewModels.add(viewModel);
            }
            refreshData(viewModels);
        }
    };

    @Override
    public void onDataLoader(GroupCard user) {
        //成功
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupCreateContract.View view = getmView();
                if(view!=null) {
                    view.showError(R.string.data_network_error);
                }
            }
        });
    }

    @Override
    public void onDataNotAvaliable(@StringRes final int res) {
        //失败情况
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupCreateContract.View view = getmView();
                if(view!=null) {
                    view.showError(res);
                }
            }
        });
    }
}
