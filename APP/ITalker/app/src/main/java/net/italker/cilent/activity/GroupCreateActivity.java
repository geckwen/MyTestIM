package net.italker.cilent.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.common.app.Application;
import net.common.app.PresentToolBarActivity;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.present.group.GroupCreateContract;
import net.factory.present.group.GroupCreatePresenter;
import net.italker.cilent.R;
import net.italker.cilent.fragment.media.GalleyFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * 群创建
 */
public class GroupCreateActivity extends PresentToolBarActivity<GroupCreateContract.Presenter> implements GroupCreateContract.View{

    @BindView(R.id.recycler)
    RecyclerView  mRecycler;

    @BindView(R.id.edit_name)
    EditText mName;

    @BindView(R.id.edit_desc)
    EditText mDesc;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    private Adapter mAdapter;

    //头像地址
    private String mPortraitPath;
    public static void show(Context context)
    {
        context.startActivity(new Intent(context,GroupCreateActivity.class));
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_group_create;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle("");
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new Adapter());
    }

    @Override
    public void initData() {
        super.initData();
        mPresent.start();
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick()
    {
        hideSoftKeyboard();
        new GalleyFragment().registerListener(new GalleyFragment.OnSelectListener() {
            @Override
            public void onSelectImage(String path) {
                UCrop.Options options = new UCrop.Options();
                //设置图片处理格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //设置图片压缩的精度
                options.setCompressionQuality(96);
                //设置图片缓存的位置
                File dpath = Application.getPortraitTempFile();
                UCrop.of(Uri.fromFile(new File(path)),Uri.fromFile(dpath))
                        .withAspectRatio(1,1)   //1:1比例
                        .withMaxResultSize(520,520) //返回最大的尺寸
                        .withOptions(options)
                        .start(GroupCreateActivity.this);
            }//show里面建议使用getChildFragmentManager(),因为Activity会造成一系列的麻烦
        }).show(getSupportFragmentManager(),
                GalleyFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Urix
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**加载头像并上传头像
     * @param resultUri ucrop的图片地址
     */
    private void loadPortrait(Uri resultUri)
    {
        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
        mPortraitPath = resultUri.getPath();
        final String localPath = resultUri.getPath();
        Log.e("TAG",String.format("localPath:%s",localPath));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_create,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_create) {
            //创建
            onCreateClick();
        }

        return super.onOptionsItemSelected(item);
    }

    //进行创建操作
    private void onCreateClick(){
        hideSoftKeyboard();
        String name = mName.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        mPresent.create(name,desc,mPortraitPath);
    }

    //隐藏软件盘
    private  void hideSoftKeyboard(){
        //当前焦点的View
        View view = getCurrentFocus();
        if(view == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onCreateSucceed() {
            showHide();
            Application.showToast(R.string.label_group_create_succeed);
    }

    @Override
    protected GroupCreateContract.Presenter initPresent() {
        return new GroupCreatePresenter(this);
    }

    @Override
    public RecycleAdapter<GroupCreateContract.ViewModel> getRecycleAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataRefresh() {
        showHide();
    }



    private class Adapter extends RecycleAdapter<GroupCreateContract.ViewModel>{

        @Override
        protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
            return new ViewHolder(view);
        }

        @Override
        protected int getItemViewType(int position, GroupCreateContract.ViewModel data) {
            return R.layout.cell_group_create_contact;
        }
    }

    class ViewHolder extends RecycleAdapter.MyViewHolder<GroupCreateContract.ViewModel>
    {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mNmae;
        @BindView(R.id.cb_select)
        CheckBox mSelect;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @OnCheckedChanged(R.id.cb_select)
        void onCheckedChanged(boolean checked){
            //进行状态更改
            mPresent.changeSelect(mData,checked);
        }

        @Override
        protected void OnBind(GroupCreateContract.ViewModel data) {
            mPortrait.setPortraitView(Glide.with(GroupCreateActivity.this),data.author);
            mNmae.setText(data.author.getName());
            mSelect.setChecked(data.isSelected);
        }
    }
}
