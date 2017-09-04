package com.lovcreate.core.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 遮盖层对话框工具
 * 注意: 打开的遮盖框是共享的,如果遮盖框要与Activity或fragment绑定,不能使用这个工具,可以直接调用对应方法
 * Created by Albert.Ma on 2015/10/20.
 */
public class CoverLayerDialog {

    private static ProgressDialog dialog;

    /**
     * 打开一个 内容为message 的遮盖层
     *
     * @param context    上下文
     * @param message    遮盖层显示的内容
     * @param cancelable 是否可以撤销
     */
    public static ProgressDialog openProgressDialogForLoading(Context context, String message, boolean cancelable) {
        openProgressDialogForLoading(context, message, cancelable, null, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        return dialog;
    }

    /**
     * 打开一个 内容为message 有取消监听 的遮盖层
     *
     * @param context          上下文
     * @param message          遮盖层显示的内容
     * @param cancelable       是否可以撤销
     * @param onCancelListener 取消监听
     */
    public static ProgressDialog openProgressDialogForLoading(Context context, String message, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        openProgressDialogForLoading(context, message, cancelable, onCancelListener, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        return dialog;
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
    public static ProgressDialog openProgressDialogForLoading(Context context, String message, boolean cancelable, DialogInterface.OnCancelListener onCancelListener, int theme) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        if (onCancelListener != null) {
            dialog.setOnCancelListener(onCancelListener);
        }
        dialog.setProgressStyle(theme);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    /**
     * 关闭遮盖层
     */
    public static void closeProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();//关闭
        }
    }

    /**
     * 进度条遮盖层,设置进度
     *
     * @param max      最大进度
     * @param progress 当前进度
     */
    public static void setProgress(long max, long progress) {
        dialog.setMax(100);
        int progressPercent = (int) (progress / (float) max * 100);
        dialog.setProgress(progressPercent);
    }
}
