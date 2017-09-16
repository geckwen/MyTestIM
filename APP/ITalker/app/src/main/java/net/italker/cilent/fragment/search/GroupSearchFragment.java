package net.italker.cilent.fragment.search;




import net.common.app.PresentFragment;
import net.factory.main.present.search.GroupPresent;
import net.factory.main.present.search.SearchContract;
import net.factory.model.card.UserCard;
import net.italker.cilent.R;
import net.italker.cilent.activity.SearchActivity;

import java.util.List;

public class GroupSearchFragment extends PresentFragment<SearchContract.Present> implements
        SearchActivity.SearchFragment,SearchContract.GroupView{


    @Override
    protected SearchContract.Present initPresent() {
        return new GroupPresent(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group_search;
    }


    @Override
    public void search(String query) {

    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {

    }




}
