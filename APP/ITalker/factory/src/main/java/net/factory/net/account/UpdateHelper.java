package net.factory.net.account;

import net.common.factory.data.DataSource;
import net.factory.R;
import net.factory.main.Factory;
import net.factory.model.api.account.AccountRsqModel;
import net.factory.model.base.RspModel;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;
import net.factory.model.user.UpdateInfoModel;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CLW on 2017/9/9.
 */

public class UpdateHelper {
    public static  void upDateInfo(UpdateInfoModel model, final DataSource.Callback<UserCard> callback)
    {
        RemoteService service = NetWork.getAccountRemoteService();
        Call<RspModel<UserCard>> call = service.UpdateInfo(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                        RspModel<UserCard> rspModel = response.body();
                        if(rspModel.success()) {
                            UserCard userCard = rspModel.getResult();
                            User user =userCard.build();
                            user.save();
                            callback.onDataLoader(userCard);
                        }else{
                            Factory.decodeRsqCode(rspModel,callback);
                        }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvaliable(R.string.data_rsp_error_service);
            }
        });
    }
}
