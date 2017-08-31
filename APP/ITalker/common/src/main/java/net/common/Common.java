package net.common;

/**
 * Created by CLW on 2017/8/28.
 */

public class Common {
    /**
     * 一些永恒不变的参数
     * 通常用于一些配置
     */
        public interface  Constance{
            //基础的手机正则配置
            String REGAX_PHONE="[1][3,4,5,7,8][0-9]{9}$";
             //基础的网络连接地址
            String API_URL = "http://192.168.0.156:8080/ITalkePush/api/";
        }
}
