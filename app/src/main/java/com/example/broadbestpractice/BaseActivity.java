package com.example.broadbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();

    private ForceOfflineReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause");
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    class ForceOfflineReceiver extends BroadcastReceiver{

        /*onResume中动态创建intent-filter类型的实例，并对其添加指定的action动作
        再新建了receiver类的实例对象，并将receiver和intent-filter作为参数动态注册，
        因为在onResume中动态注册，所以只有activity运行在栈顶的时候（执行了onResume）才能接收这条广播
        在onPause中取消注册是因为receiver接收器接收到这条广播之后接收器会打开一个Dialog对话框（悬浮在界面上，依然能看见原界面，
        所以要在onPause中取消注册，执行完onPause之后退出前台生存期)
        （广播由MainActivity发出，前面onResume只是决定此接收器能接收这样的广播）*/

        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning(警告): ");
            builder.setMessage("You are forced to be offline. Please try to login again.(您已经被强制下线请重新登录)");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();
                    startActivity(new Intent(context,LoginActivity.class));
                }
            });
            builder.show();
        }
    }
}
