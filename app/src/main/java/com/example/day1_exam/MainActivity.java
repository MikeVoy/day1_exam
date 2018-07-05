package com.example.day1_exam;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_mobile;
    private EditText et_pwd;
    private Button btn_deng;
    private Button btn_zhuce;
    OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //控件初始化
        initView();

        //注册跳转
        btn_zhuce.setOnClickListener(this);
        //登陆验证
        btn_deng.setOnClickListener(this);


    }
    private boolean isPwdNo(String pwd) {
        int length = pwd.length();
        if (length>=6){
            return true;
        }
       return false;
    }
    private boolean isMobileNo(String mobile) {
        Pattern compile = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = compile.matcher(mobile);
        return matcher.matches();
    }

    private void initView() {
        et_mobile = findViewById(R.id.et_mobile);
        et_pwd = findViewById(R.id.et_pwd);
        btn_deng = findViewById(R.id.btn_deng);
        btn_zhuce = findViewById(R.id.btn_zhuce);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_deng:
                //获取输入框中的值
                String mobile = et_mobile.getText().toString();
                String pwd = et_pwd.getText().toString();
                if(!TextUtils.isEmpty(mobile)&&!TextUtils.isEmpty(pwd)){
                    //1:验证手机号格式
                    boolean isMobile = isMobileNo(mobile);
                    //2:验证密码格式
                    boolean isPwd = isPwdNo(pwd);
                    if(isMobile&&isPwd){
                        //格式正确,就将值通过okHttp传过去
                        postRequest(mobile,pwd);



                    }else {

                        Toast.makeText(this,"账号或密码不正确",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_zhuce:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;

        }

    }

    private void postRequest(String mobile, String pwd) {

        RequestBody formBody =   new FormBody.Builder()

                .add("tel",mobile)
                .add("pwd",pwd)
                .build();

        Request request = new Request.Builder()

                .url("")

                .build();

    }
}
