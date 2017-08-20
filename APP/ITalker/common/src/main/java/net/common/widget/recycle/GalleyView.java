package net.common.widget.recycle;


import android.content.Context;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.common.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * 选择图片GalleyView
 *
 */
public class GalleyView extends RecyclerView {
    private static final int LOADER_ID = 0x0100;
    private static final int MAX_IMAGE_COUNT = 3;
    private static final int MIN_IMAGE_FILE_SIZE = 10*1024 ;
    private static final int GALLEYCOWS = 4 ;

    private LoaderCallBack mLoaderCallBack = new LoaderCallBack();
    private SelectListener mSelectlistener ;
    private List<Image> mImageList = new ArrayList<>();
    private Adapter mAdapter = new Adapter();

    public GalleyView(Context context) {
        super(context);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
          setLayoutManager(new GridLayoutManager(getContext(),GALLEYCOWS));
          setAdapter(mAdapter);
          mAdapter.registerClickListener(new RecycleAdapter.AdapterListenerImpl<Image>() {
              @Override
              public void onClick(RecycleAdapter.MyViewHolder holder, Image data) {
                      //cell点击操作，如果说我们的点击是允许的，则更新对应的cell状态
                      //然后更新界面，如果不能点击，则说明已经达到最大的选中数量，那么不刷新界面
                  if(onItemSelectClick(data))
                  {
                      holder.updataData(data);
                  }
              }
          });

    }

    /**
     * 初始化方法
     * @param loaderManager
     * @return 任何一个LOADER_ID都可以用于销毁loader
     */
    public int setUp(LoaderManager loaderManager, SelectListener mSelectlistener)
    {
        loaderManager.initLoader(LOADER_ID,null,mLoaderCallBack);
        this.mSelectlistener = mSelectlistener;
        return LOADER_ID;
    }

    /**
     * Cell点击的具体逻辑
     * @param image Image
     * @return True，代表我进行了数据更改，你需要更新;反之不更新
     */
    private boolean onItemSelectClick(Image image)
    {
        //是否需要进行刷新
        boolean notifyRefresh;
        if(mImageList.contains(image))
        {
            //如果之前选中了，现在就是取消
            mImageList.remove(image);
            image.isSelect = false;
            //状态改变需要刷新
            notifyRefresh = true ;
        }
        else
        {
            if(mImageList.size() >= MAX_IMAGE_COUNT)
            {
                //Toast一个提示
                String str = getResources().getString(R.string.label_gallery_select_max_size);
                str = String.format(str,MAX_IMAGE_COUNT);
                Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                notifyRefresh = false ;
            }else{
                mImageList.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }
        if(notifyRefresh)
        {
            //如果数据有更改，那么我们需要通知外面的监听者进行数据选中更改
            notifySelectChange();
        }
      return true;
    }

    private void notifySelectChange() {
        //得到监听者是否有，如果有，则进行回掉
        if(mSelectlistener != null)
        {
            mSelectlistener.onSelectChange(mImageList.size());
        }
    }

    /**
     *得到选中图片的地址数组
     * @return  返回一组数组
     */
    public  String[] getSelectPath()
    {
        String[] paths = new String[mImageList.size()];
        int index = 0;
        for(Image image : mImageList) {
            paths[index++] = image.path;
        }
        return paths;
    }

    /**
     * 取消选中图片
     */
    private void clear()
    {
        for(Image image : mImageList)
        {
            image.isSelect = false ;
        }
        mImageList.clear();
        //通知mAdapter更新界面
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 内部数据结构
     */
    private class Image{
        //图片的ID
        int id;
        //图片的地址
        String path;
        //图片创建的时间
        long date;
        //是否选中了
        boolean isSelect;


        @Override
        public int hashCode() {
            return path != null ? path.hashCode():0;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            Image image =(Image)obj;
            return path != null ? path.equals(image.path):image.path == null;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }

    /**
     * 适配器
     * @param
     */
    private class Adapter extends RecycleAdapter<Image>{

        @Override
        protected MyViewHolder onCreaterViewHolde(View view, int viewType) {

            return new GalleyView.ViewHolder(view);
        }

        @Override
        protected int getItemViewType(int position, Image data) {
            return R.layout.sample_galley_view;
        }


    }

    /**
     * cell对应的holder
     */
    private class ViewHolder extends RecycleAdapter.MyViewHolder<Image>{
        private ImageView mImageView;
        private View mView;
        private CheckBox mCheckBox;


        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.im_image);
            mView = itemView.findViewById(R.id.view_shade);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void OnBind(Image data) {
            Glide.with(getContext())
                    .load(data.getPath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不适用缓存，直接从电脑上加载
                    .centerCrop() //从中间裁剪
                    .placeholder(R.color.grey_50) //当图片没加载时默认的为
                    .into(mImageView);
            mView.setVisibility(data.isSelect()? VISIBLE : INVISIBLE);
            mCheckBox.setChecked(data.isSelect());
            mCheckBox.setVisibility(VISIBLE);
        }


    }

    /**
     * 通知Adapter更新数据
     * @param mImageList 更新的数据
     */
    private void updateSource(List<Image> mImageList)
    {
        mAdapter.replace(mImageList);
    }

    /**
     * 用于实际数据加载的Loader Callback
     */
    private class LoaderCallBack implements LoaderManager.LoaderCallbacks<Cursor>{
        private final String[] IMAGE_PROJECTION=new String[]{
                //相片ID
                MediaStore.Images.Media._ID,
                //相片地址
                MediaStore.Images.Media.DATA,
                //相片添加的时间
                MediaStore.Images.Media.DATE_ADDED
        };

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            //创建一个loader
            if(id == LOADER_ID)
            {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2]+" DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //加载完成时
            List<Image> mImageList = new ArrayList();
            if(data != null)
            {
                int count = data.getCount();
                if(count > 0)
                {
                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    data.moveToFirst();
                    do{
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long date = data.getLong(indexDate);

                        File file = new File(path);
                        if(!file.exists()|| file.length() <= MIN_IMAGE_FILE_SIZE)
                        {
                           continue;
                        }
                        Image image =new Image();
                        image.setId(id);
                        image.setPath(path);
                        image.setDate(date);
                        image.setSelect(false);
                        mImageList.add(image);

                    }while(data.moveToNext());
                }
            }
        updateSource(mImageList);
        }



        @Override
        public void onLoaderReset(Loader loader) {
            //loader重置或者销毁时,进行界面的清空
            updateSource(null);

        }
    }

    /**
     * 对外监听器
     */
    public interface SelectListener{
        void onSelectChange(int count);
    }



}
