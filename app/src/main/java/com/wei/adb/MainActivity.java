package com.wei.adb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView mTipsTv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTipsTv = findViewById(R.id.tv_tips);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    public void openADB(View view)
    {
        String tips;
        if (canOpenADB())
        {
            tips = String.format("请在PC终端输入:\nadb connect %s:5555", NetworkUtils.getLocalIpAddress(this));
        }
        else
        {
            tips = "请检查手机是否已root并且app已经获取root权限！";
        }
        mTipsTv.setText(tips);
    }

    private boolean canOpenADB() {
        String[] commandList = new String[]{
                "setprop service.adb.tcp.port 5555",
                "stop adbd",
                "start adbd"
        };
        return ShellUtil.exeCmdByRoot(commandList);
    }
}
