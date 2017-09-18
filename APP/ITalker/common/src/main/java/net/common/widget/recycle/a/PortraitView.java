package net.common.widget.recycle.a;

import android.content.Context;

import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;

import net.common.R;
import net.common.model.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CLW on 2017/8/5.
 */

public  class PortraitView extends CircleImageView {


    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPortraitView(RequestManager manage){

    }

    public void setPortraitView(RequestManager manager, Author author)
    {
        if(author==null)
            return;
        setPortraitView(manager,author.getPortrait());
    }

    public void setPortraitView(RequestManager manage,String url)
    {

            setPortraitView(manage,url, R.drawable.default_portrait);
    }

    public void setPortraitView(RequestManager manage, String url,int res)
    {
            manage.load(url)
                .placeholder(res)
                .centerCrop()
                .dontAnimate()  //不允许加载动画,因为这样会延迟画面
                .into(this);
    }
}
