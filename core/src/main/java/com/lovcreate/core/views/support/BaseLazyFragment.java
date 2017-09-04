package com.lovcreate.core.views.support;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovcreate.core.utils.CoverLayerDialog;
import com.lovcreate.core.utils.Logcat;

import butterknife.ButterKnife;

/**
 * 向上兼容的基本Fragment
 * 当前BaseActivity已经实现的公用特性:
 * <p/>
 * 1.懒加载
 * 借鉴于 http://blog.csdn.net/maosidiaoxian/article/details/38300627
 * <p/>
 * 2.遮盖框
 * 将与LazyFragment共生,这样当网络要求打开多个LazyFragment时,每一个LazyFragment打开的遮盖框将不会互相影响(工具类CoverLayerDialog打开的遮盖框是共享的)
 * <p/>
 * Created by Peter.Pan on 2016/4/6.
 * Update by Albert.Ma on 2017/5/18.
 */
public abstract class BaseLazyFragment extends Fragment {

    private Activity bfActivity;//Fragment所属的Activity

    /**
     * 懒加载
     */
    protected boolean isVisible;

    /**
     * 遮盖框
     */
    protected ProgressDialog bfDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bfActivity = getActivity();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onCreate...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onCreateView...");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 子类onCreateView后,初始化ButterKnife控件
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化ButterKnife
        ButterKnife.bind(this, getView());
    }

    @Override
    public void onStart() {
        super.onStart();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onStart...");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onResume...");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onPause...");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onStop...");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onDestroyView...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onDestroy...");
    }

    ///////////////////////////////////    懒加载    ////////////////////////////////////

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

    ///////////////////////////////////    遮盖框    ////////////////////////////////////

    /**
     * 打开一个 内容为message 的遮盖层
     *
     * @param context    上下文
     * @param message    遮盖层显示的内容
     * @param cancelable 是否可以撤销
     */
    public void openProgressDialogForLoading(Context context, String message, boolean cancelable) {
        bfDialog = CoverLayerDialog.openProgressDialogForLoading(context, message, cancelable, null, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
    }

    /**
     * 打开一个 内容为message 有取消监听 的遮盖层
     *
     * @param context          上下文
     * @param message          遮盖层显示的内容
     * @param cancelable       是否可以撤销
     * @param onCancelListener 取消监听
     */
    public void openProgressDialogForLoading(Context context, String message, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        bfDialog = CoverLayerDialog.openProgressDialogForLoading(context, message, cancelable, onCancelListener, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
    }

    /**
     * 打开一个 内容为message  有取消监听 主题为theme 的遮盖层
     *
     * @param context          上下文
     * @param message          遮盖层显示的内容
     * @param cancelable       是否可以撤销
     * @param onCancelListener 取消监听
     * @param theme            主题
     */
    public void openProgressDialogForLoading(Context context, String message, boolean cancelable, DialogInterface.OnCancelListener onCancelListener, int theme) {
        bfDialog = CoverLayerDialog.openProgressDialogForLoading(context, message, cancelable, onCancelListener, theme);
    }

    /**
     * 关闭遮盖层
     */
    public void closeProgressDialog() {
        if (bfDialog != null) {
            bfDialog.dismiss();//关闭
        }
    }

    /**
     * 进度条遮盖层,设置进度
     *
     * @param max      最大进度
     * @param progress 当前进度
     */
    public void setProgress(long max, long progress) {
        bfDialog.setMax(100);
        int progressPercent = (int) (progress / (float) max * 100);
        bfDialog.setProgress(progressPercent);
    }

///////////////////////////////////    日志    ////////////////////////////////////

    /**
     * 用于日志输出,仅打印类名
     */
    @Override
    public String toString() {
        String packageClazz = super.getClass().getName();
        return packageClazz.substring(packageClazz.lastIndexOf(".") + 1, packageClazz.length());
    }


}
