package net.italker.cilent.fragment.assist;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.common.Application;
import net.italker.cilent.R;
import net.italker.cilent.fragment.media.GalleyFragment;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public class PermissionFragment extends BottomSheetDialogFragment
    implements EasyPermissions.PermissionCallbacks
{
    //权限回调标识
    private  static  final  int RC = 0x001;

        public PermissionFragment(){

        }

    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //默认的Dialog
        return new GalleyFragment.TranStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_permissionragment,container,false);
        refreshState(root);
        return root;
    }

    /**
     * 刷新状态
     * @param root 父布局
     */
    private void refreshState(View root)
    {
        Context  context = getContext();
        root.findViewById(R.id.im_state_permission_network)
                .setVisibility( haveNetWork(context)? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_read)
                .setVisibility( haveNetWork(context)? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_write)
                .setVisibility( haveNetWork(context)? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_record_audio)
                .setVisibility( haveNetWork(context)? View.VISIBLE : View.GONE);

    }

    /**
     * 检查是否有网络权限
     * @param context 上下文
     * @return 判断是否有权限
     */
    private static boolean haveNetWork(Context context)
    {
        String []  permissionNetWork = new String []{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        return EasyPermissions.hasPermissions(context,permissionNetWork);
    }

    /**
     * 检查是否有读取权限
     * @param context 上下文
     * @return 判断是否有权限
     */
    private static boolean haveRead(Context context)
    {
        String []  permissionNetWork = new String []{
             Manifest.permission.READ_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context,permissionNetWork);
    }
    /**
     * 检查是否有写权限
     * @param context 上下文
     * @return 判断是否有权限
     */
    private static boolean haveWrite(Context context)
    {
        String []  permissionNetWork = new String []{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        return EasyPermissions.hasPermissions(context,permissionNetWork);
    }
    /**
     * 检查是否有录音权限
     * @param context 上下文
     * @return 判断是否有权限
     */
    private static boolean haveRecordAudio(Context context)
    {
        String []  permissionNetWork = new String []{
                Manifest.permission.RECORD_AUDIO
        };

        return EasyPermissions.hasPermissions(context,permissionNetWork);
    }

    /**
     * 私有的show方法
     * @param mFragmentManager
     */
    private static void show(FragmentManager mFragmentManager)
    {
        new PermissionFragment().show(mFragmentManager,PermissionFragment.class.getName());
    }

    /**
     * 检查是否拥有所有权限
     * @param context 上下文
     * @param mFragmentManager
     * @return
     */
    public static boolean haveAllPermission(Context context,FragmentManager mFragmentManager)
    {
        boolean haveAll = haveNetWork(context) && haveRead(context) && haveWrite(context) && haveRecordAudio(context)
        if ( !haveAll ) {
            show(mFragmentManager);
        }
        return  haveAll;
    }

    /**
     * 授权所有权限
     */
    @AfterPermissionGranted(RC)
    private void requestPem()
    {
        String [] allPermission = new String [] {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
                Manifest.permission.RECORD_AUDIO
        };
        if(EasyPermissions.hasPermissions(getContext(),allPermission))
        {
            Application.showToast(R.string.label_permission_ok);
        }
        else {
            EasyPermissions.requestPermissions(this
                    ,getString(R.string.title_assist_permissions),RC,allPermission);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    /**
     * 申请权限失败
     * @param requestCode 权限回调的标识
     * @param perms 权限
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms))
        {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    /**
     * 权限申请的时候的回调方法，只需要把对应的参数写入
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //对应的参数,最后一个参数为自己
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
