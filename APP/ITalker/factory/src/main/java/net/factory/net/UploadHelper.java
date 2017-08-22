package net.factory.net;

import android.text.format.DateFormat;
import android.util.Log;


import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;

import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

<<<<<<< HEAD
=======
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
>>>>>>> first hibernate and web connect succesed
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import net.common.utils.HashUtil;

import net.factory.main.Factory;

import java.io.File;
import java.util.Date;

/**
 * 上传工具，主要将文件上传到OSS存储
 * Created by CLW on 2017/8/20.
 */

public class UploadHelper {
    private static final String TAG = UploadHelper.class.getName();
    private static  final String ENDPOINT = "http://oss-cn-hongkong.aliyuncs.com";
    private static  final String ACCESS_KEY_ID ="LTAIhf7PgYEmByoR";
    private static  final String ACCESS_KEY_SECRET ="YjIdCJ2eCvptsHVDktuNrC6PSPwdeO";
    private static final  String BUCKT_NAME ="qintan-new";
<<<<<<< HEAD
    public String SecurityToken ="";
=======
    private static final String SECURITY_TOKEN ="CAES7QIIARKAAZPlqaN9ILiQZPS+JDkS/GSZN45RLx4YS/p3OgaUC+oJl3XSlbJ7StKpQ";
>>>>>>> first hibernate and web connect succesed

    public static OSS getCilnet()
    {

        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
<<<<<<< HEAD
       //可以等后面配置尝试 OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyID, AccessKeySecret, "<StsToken.SecurityToken>");
=======
       //可以等后面配置尝试
        //OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET, SECURITY_TOKEN);
>>>>>>> first hibernate and web connect succesed
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        OSS oss = new OSSClient(Factory.getApp(), ENDPOINT, credentialProvider);
        return  oss;
    }

    /**
     * 上传文件后，成功返回一个地址
     * @param objectKey 上传上去，在服务器独立的key
     * @param path  需要上传的地址
     * @return  存储地址
     */
    public static String upload(String objectKey,String path)
    {
        //构造一个上传请求
        PutObjectRequest put = new PutObjectRequest(BUCKT_NAME, objectKey, path);
        try {
            //初始化上传的cilnet
            OSS cilent = getCilnet();
            //开始同步上传
            PutObjectResult putResult = cilent.putObject(put);
            //上传成功获得一个外网的访问地址
            String URL = cilent.presignPublicObjectURL(BUCKT_NAME,objectKey);
            //格式打印输出
            Log.d(TAG,String.format("PutOjectResltURL:%s",URL));
            return URL;
        } catch (Exception e) {
           e.printStackTrace();
            return null;
        }

    }


    /**
     * 上传图片信息
     * @param path 本地地址
     * @return  返回外网访问地址
     */
    public static String uploadImage(String path)
    {
        String key = getloadImageKey(path);
        return  upload(key,path);
    }

    /**
     * 上传头像信息
     * @param path  本地地址
     * @return 返回外网访问地址
     */
    public static String uploadPortrait(String path)
    {
        String key = getloadPortraitKey(path);
        return  upload(key,path);
    }

    /**
     * 上传录音信息
     * @param path  本地地址
     * @return  返回外网访问地址
     */
    public static String uploadAudio(String path)
    {
        String key = getloadAudioKey(path);
        return  upload(key,path);
    }

    private static String getDate()
    {
        return DateFormat.format("yyyyMM",new Date()).toString();
    }
    /**
     * 通过图片地址进行外网区别地址
     * @param path 本地地址
     * @return  返回一个key
     */
    //Image/201708/xxas7eqsdx.jpg
    private static String getloadImageKey(String path)
    {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String date = getDate();
        return String.format("Image/%s/%s.jpg",date,fileMd5);
    }
    /**
     * 通过图片地址进行外网区别地址
     * @param path 本地地址
     * @return  返回一个key
     */
    //Portrait/201708/xxas7eqsdx.jpg
    private static String getloadPortraitKey(String path)
    {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String date = getDate();
        return String.format("Portrait/%s/%s.jpg",date,fileMd5);
    }
    /**
     * 通过图片地址进行外网区别地址
     * @param path 本地地址
     * @return  返回一个key
     */
    //Audio/201708/xxas7eqsdx.mp3
    private static String getloadAudioKey(String path)
    {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String date = getDate();
        return String.format("Audio/%s/%s.mp3",date,fileMd5);
    }


}
