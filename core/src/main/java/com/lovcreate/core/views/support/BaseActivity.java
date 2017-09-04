package com.lovcreate.core.views.support;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lovcreate.core.utils.CoverLayerDialog;
import com.lovcreate.core.utils.Logcat;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 向上兼容的基本Activity
 * 向上兼容低版本的Fragment,替代FragmentActivity.
 * 同时向上兼容低版本的ActionBar(由ToolBar替代),替代ActionBarActivity
 * <p/>
 * 当前BaseActivity已经实现的公用特性:
 * <p/>
 * 1,沉浸式状态栏
 * 使用:在继承此BaseActivity的Activity中重写setStatusBar()方法.
 * 一,如果要改变沉浸式状态栏颜色,使用StatusBarUtil.setColor();
 * 二,如果根布局有背景图片,使用StatusBarUtil.setTranslucent();例如WelcomeActivity
 * 三,如果根布局下为头部是ImageView的界面设置状态栏透明,使用StatusBarUtil.setTranslucentForImageView()方法;例如LoginActivity
 * <p/>
 * 2,EditView点击空白区域隐藏输入法软键盘
 * 借鉴于 http://blog.csdn.net/djl461260911/article/details/45893451
 * <p/>
 * 3,遮盖框
 * 将与BaseActivity共生,这样当网络要求打开多个BaseActivity时,每一个BaseActivity打开的遮盖框将不会互相影响(工具类CoverLayerDialog打开的遮盖框是共享的)
 * <p/>
 * 4,关闭App
 * 每次创建Activity时在这里进行记录,当要进去退出时,finish所有的activity实现退出
 * <p/>
 * Created by Peter.Pan on 2016/3/24.
 * Update by Albert.Ma on 2017/5/18.
 */
public class BaseActivity extends AppCompatActivity {

    protected Context baContext;//上下文
    protected FragmentManager baFragmentManger;
    protected static List<BaseActivity> baAllActivity = new LinkedList<>();//保存每个Activity

    /**
     * 遮盖框
     */
    protected ProgressDialog bfDialog;

    /**
     * 创建时
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baAllActivity.add(this);//每次创建时,加入到activity容器中
        baContext = this;
        baFragmentManger = getSupportFragmentManager();
        Logcat.i(this.toString() + " - ==> onCreate...");
    }

    /**
     * 重写setContentView(int)方法,添加初始化ButterKnife控件
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //初始化ButterKnife
        ButterKnife.bind(this);
        //设置沉浸式状态栏
        setStatusBar();
    }

    /**
     * 开始时
     */
    @Override
    protected void onStart() {
        super.onStart();
        Logcat.i(this.toString() + " - ==> onStart...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logcat.i(this.toString() + " - ==> onResume...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logcat.i(this.toString() + " - ==> onPause...");
    }

    /**
     * 结束时
     */
    @Override
    protected void onStop() {
        super.onStop();
        Logcat.i(this.toString() + " - ==> onStop...");
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logcat.i(this.toString() + " - ==> onDestroy...");
    }

    //////////////////////////////   沉浸式状态栏   //////////////////////////////

    /**
     * 重写此方法可自定义沉浸式状态栏
     */
    protected void setStatusBar() {

    }

    //////////////////////////////   EditView点击空白区域隐藏输入法软键盘   //////////////////////////////

    /**
     * EditView点击空白区域隐藏输入法软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * EditView点击空白区域隐藏输入法软键盘:
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * EditView点击空白区域隐藏输入法软键盘:获取InputMethodManager隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
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

    //////////////////////////////   关闭App   //////////////////////////////

    public void shutDownApp(){
        try {
            for (BaseActivity activity: baAllActivity) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
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
