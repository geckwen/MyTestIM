package net.common.widget.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import  net.common.Data;
import net.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 这个是RecycleView的适配器,里面包含了ViewHolder以及回掉AdapterListenr接口
 * Created by CLW on 2017/7/30.
 */

public abstract class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>
implements View.OnClickListener,View.OnLongClickListener,AdapterCallBack{
    private List<Data> mDatalist;
    private AdapterListenr adapterListenr = null;

    public RecycleAdapter(){
            this.mDatalist=new ArrayList<>();
    }
    public RecycleAdapter(List mDatalist)
    {
        this.mDatalist=mDatalist;
    }
    public RecycleAdapter(AdapterListenr adapterListenr,List mDatalist)
    {
           registerClickListener(adapterListenr);
            this.mDatalist=mDatalist;
    }

    /**创建一个videHolde
     * @param parent RecycleView
     * @param viewType 界面的类型
     * @return viewHolde
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //得到LayoutInlater用于把XML初始化为View
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //把XML id为viewType的文件转化为一个root
        View view=layoutInflater.inflate(viewType,parent,false);
        //通过子类的方法定义一个自己的ViewHolder
        MyViewHolder viewHolder=createrViewHolde(view,viewType);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        //设置View Tag 为ViewHoler,进行双向绑定
        view.setTag(R.id.tag_recycler_holder,viewHolder);

        //将view控件进行holde的绑定
        viewHolder.unbinder=ButterKnife.bind(viewHolder,view);
        viewHolder.adapterCallBack=this;
        return null;
    }

    /**
     * 得到一个新的ViewHolder
     * @param view 根布局
     * @param viewType 布局类型，就是XML的ID
     * @return 获得一个ViewHoler
     */
    protected  abstract   MyViewHolder  createrViewHolde(View view,int viewType);

    /**
     * 绑定一个数据到viewHolde上
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //获得绑定的数据
        Data data=(Data)mDatalist.get(position);
        //绑定数据触发
        holder.bind(data);
    }

    /**
     *获得布局的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    /**  复写Type类型
     * @param position 坐标
     * @return 返回的是XML的ID，用于创建ViewHolder
     */
    @Override
    public int getItemViewType(int position) {

        return getItemViewType(position,mDatalist.get(position));
    }

    /** 得到布局类型
      * @param position 坐标
     * @param data 当前的数据
     * @return  XML文件的ID，用于创建ViewHolder
     */
    protected  abstract  int getItemViewType(int position,Data data);


    /**
     * 添加数据Data
     * @param data 数据
     */
    public void add(Data data)
    {
        mDatalist.add(data);
        notifyItemInserted(mDatalist.size()-1);
    }


    /**
     * 添加一堆数据
     * @param datalist 一堆数据
     */
    public void addDataList(Data... dataList)
    {
        if (dataList != null && dataList.length>0)
        {
            int startPos=mDatalist.size();
            Collections.addAll(mDatalist,dataList);
             notifyItemRangeInserted(startPos,dataList.length);
        }
    }

    /**
     * 添加一堆集合数据
     * @param dataList 集合数据
     */
    public void addCollection(Collection dataList)
    {
        if (dataList != null && dataList.size()>0)
        {
            int startPos=mDatalist.size();
            mDatalist.addAll(dataList);
            notifyItemRangeInserted(startPos,dataList.size());

        }
    }

    /**
     * 清空数据
     */
    public void clear()
    {
        mDatalist.clear();
        notifyDataSetChanged();
    }


    /**
     * 替换所有数据
     * @param dataList  替换数据
     */
    public void replace(Data...dataList)
    {
        mDatalist.clear();
        Collections.addAll(mDatalist,dataList);
        notifyDataSetChanged();
    }

    /**
     * j监听点击事件
     * @param v 根布局
     */
    @Override
    public void onClick(View v) {
        MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.tag_recycler_holder);
        if(adapterListenr != null)
        {
            //获得viewHolder适配器在view的坐标
            int position=viewHolder.getAdapterPosition();
         adapterListenr.onClick(viewHolder,mDatalist.get(position));
        }
    }


    /**  监听点击事件
     * @param v 根布局
     * @return 如果拦截点击事件就为false,如果不拦截就为true
     */
    @Override
    public boolean onLongClick(View v) {
        MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.tag_recycler_holder);
        if(adapterListenr != null)
        {
            int position=viewHolder.getAdapterPosition();
            adapterListenr.onLongClick(viewHolder,mDatalist.get(position));
            return true;
        }
        return false;
    }

    /**
     * 注册监听器
     * @param adapterListenr 监听器
     */
    public void registerClickListener(AdapterListenr adapterListenr)
    {
        this.adapterListenr=adapterListenr;
    }

    /**
     *  我们的自定义监听器主要是监听onClick与onLongClick
     */
    public interface  AdapterListenr
    {
        void onClick(RecycleAdapter.MyViewHolder holder,Data data);
        void onLongClick(RecycleAdapter.MyViewHolder holder,Data data);
    }
    /**
     *  自定viewHolder
     */
    public static abstract class  MyViewHolder extends RecyclerView.ViewHolder
    {

        protected Unbinder unbinder;
        private  AdapterCallBack adapterCallBack;
        private Data mData;

        public MyViewHolder(View itemView) {
            super(itemView);
        }


        /** 用于绑定数据的触发
         * @param data 绑定数据
         */
        void bind(Data data)
        {
          this.mData = data;
            OnBind(data);
        }

        /**
         * 用于绑定数据的回调
         * @param data 绑定数据
         */
        protected abstract void OnBind(Data data);


        /** 自己对应的Data进行更新
         * @param data
         */
        public void UpdataData(Data data)
        {
             if(adapterCallBack != null)
             {
                 adapterCallBack.update(data,this);
             }
        }

    }
}