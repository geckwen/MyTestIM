package net.common.factory.present;

/**
 * Created by CLW on 2017/8/25.
 */

public class BasePresent<T extends BaseContract.View> implements BaseContract.Present {

    private T mView;

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     * @param view
     */
    public BasePresent(T view)
    {
        setView(view);
    }

    /**
     * 注册一个view
     * @param view
     */
    private void setView(T view)
    {
        this.mView = view;
        this.mView.setPresent(this);
    }
    protected  final T getmView(){
        return mView;
    }

    @Override
    public void start() {
        //开始进行Loading调用
        T view = mView;
        if(view!=null) {
            view.showLoading();
        }
    }

    @Override
    public void destroy() {
        T view = mView;
        mView = null;
        if(view !=null){
            //将Present设置为null
             view.setPresent(null);
        }
    }
}
