package com.yng.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lovcreate.core.utils.Logcat;
import com.lovcreate.core.views.support.BaseActivity;
import com.yng.demo.R;
import com.yng.demo.constant.CallManager;
import com.yng.demo.utils.VMSPUtil;

import butterknife.Bind;

/**
 * Created by Albert.Ma on 2017/6/23 0023.
 */

public class ECMainActivity extends BaseActivity {

    // 发起聊天 username 输入框
    @Bind(R.id.ec_edit_chat_id)
    EditText mChatIdEdit;
    // 发起聊天
    @Bind(R.id.ec_btn_start_chat)
    Button mStartChatBtn;
    // 退出登录
    @Bind(R.id.ec_btn_sign_out)
    Button mSignOutBtn;

    private String contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断sdk是否登录成功过，并没有退出和被踢，否则跳转到登陆界面
        if (!EMClient.getInstance().isLoggedInBefore()) {
            Intent intent = new Intent(ECMainActivity.this, ECLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.ec_activity_main);

        mStartChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 视频呼叫
                 */
                if (!checkContacts()) {
                    return;
                }
                Intent intent = new Intent(ECMainActivity.this, VideoCallActivity.class);
                Log.i(">>>userId:", contacts);
                Log.i(">>>type1:", EMMessage.ChatType.Chat.toString());
                Log.i(">>>type2:", EMMessage.ChatType.Chat + "");
                intent.putExtra("userId", contacts);
                intent.putExtra("chatType", EMMessage.ChatType.Chat);
                CallManager.getInstance().setChatId(contacts);
                CallManager.getInstance().setInComingCall(false);
                CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
                startActivity(intent);

            }
        });

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    /**
     * 退出登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Logcat.e("logout success");
                // 调用退出成功，结束app
                // finish();
                Intent intent = new Intent(baContext, ECLoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(int i, String s) {
                Logcat.e("logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private boolean checkContacts() {
        contacts = mChatIdEdit.getText().toString().trim();
        if (contacts.isEmpty()) {
            Toast.makeText(ECMainActivity.this, "constact user not null", Toast.LENGTH_LONG).show();
            return false;
        }
        VMSPUtil.put(this, "contacts", contacts);
        return true;
    }
}
