package net.common.widget.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import  net.common.Data;
import net.common.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by CLW on 2017/7/30.
 */

public abstract class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>
implements View.OnClickListener,View.OnLongClickListener{
    private List mData=new ArrayList<>();

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
        //通过子类的方法定义一个自己的V
        MyViewHolder<Data> viewHolder=createViewHolder(view,viewType);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        view.setTag(R.id.tag_recycle_holder,viewHolder);

        //将view控件进行holde的绑定
        viewHolder.unbinder=ButterKnife.bind(viewHolder,view);
        return null;
    }
    protected  abstract   MyViewHolder  createViewHolde(View view,int viewType);

    /**
     * 绑定一个数据到viewHolde上
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //获得绑定的数据
        Data data=mData.get(position);
        //绑定数据触发
        holder.bind(data);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static abstract class  MyViewHolder extends RecyclerView.ViewHolder
    {

        protected Unbinder unbinder;
        private  AdapterCallBack adapterCallBack;
        pricate Data mData;

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