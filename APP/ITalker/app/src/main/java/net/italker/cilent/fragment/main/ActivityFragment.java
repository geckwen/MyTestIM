package net.italker.cilent.fragment.main;


import android.view.View;

import net.common.app.BaseFragment;
import net.common.widget.recycle.GalleyView;
import net.italker.cilent.R;

import butterknife.BindView;


/**
 *
 */
public class ActivityFragment extends BaseFragment {
    private GalleyView galleyView ;

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
       return  R.layout.fragment_activity;
    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
         galleyView = (GalleyView) root.findViewById(R.id.galleryView);
        galleyView.setUp(getLoaderManager(), new GalleyView.SelectListener() {
            @Override
            public void onSelectChange(int count) {

            }
        });


    }
}
