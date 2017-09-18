package net.factory.net.contact;

import net.common.factory.data.DataSource;
import net.common.tools.UiShow;
import net.factory.R;
import net.factory.main.Factory;
import net.factory.model.base.RspModel;
import net.factory.model.card.UserCard;
import net.factory.net.NetWork;
import net.factory.net.RemoteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CLW on 2017/9/18.
 */

public class ContactHelper {

        public static void contact(final DataSource.Callback<List<UserCard>> callback)
        {
           final RemoteService remoteService = NetWork.getAccountRemoteService();
            Call<RspModel<List<UserCard>>> call = remoteService.contact();
            call.enqueue(new Callback<RspModel<List<UserCard>>>() {
                @Override
                public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                    RspModel<List<UserCard>> result = response.body();
                    if(result.success()) {
                        List<UserCard> userCards = result.getResult();
                        callback.onDataLoader(userCards);
                    }else{
                        Factory.decodeRsqCode(result,callback);
                    }
                }

                @Override
                public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                   callback.onDataNotAvaliable(R.string.data_rsp_error_service);
                }
            });
        }
}
