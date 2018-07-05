package com.example.day1_exam;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.day1_exam.bean.UserBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_tel;
    private EditText et_pwd;
    private Button btn_login;
    private Handler handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){

                String ReturnMessage = (String) msg.obj;
                Log.i("获取的返回信息",ReturnMessage);
                final UserBean userBean = new Gson().fromJson(ReturnMessage, UserBean.class);
                String tal = userBean.getTal();
                String pwd = userBean.getPwd();
                Log.i("获取的电话号码和密码",tal+pwd);
            }
        }
    };


    final  OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();


        btn_login.setOnClickListener(this);


    }



    private void initView() {
        et_tel = findViewById(R.id.et_tel);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
                String tel = et_tel.getText().toString();
                String pwd = et_pwd.getText().toString();
                if(!TextUtils.isEmpty(tel)&&!TextUtils.isEmpty(pwd)){
                    //1:验证手机号格式
                    boolean isMobile = isTelNo(tel);
                    //2:验证密码格式
                    boolean isPwd = isPwdNo(pwd);
                    if(isMobile&&isPwd){
                        //格式正确,就将值通过okHttp传过去
                        postRequest(tel,pwd);



                    }else {

                        Toast.makeText(this,"账号或密码不正确",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void postRequest(String tel, String pwd) {

       //建立请求表单,
        FormBody formBody = new FormBody.Builder()

                .add("tel",tel)
                .add("pwd",pwd)
                .build();

        final Request request = new Request.Builder()
                .url("http://120.27.23.105/user/reg?mobile="+tel+"&?pwd="+pwd)
                .post(formBody)
                .build();

           new  Thread(new Runnable() {

               Response response = null;
               @Override
               public void run() {

                   try {
                       response = client.newCall(request).execute();
                       if(response.isSuccessful()){

                           Message message = Message.obtain();
                           handler.obtainMessage(1,response.body().string()).sendToTarget();
                       }else {

                           throw new IOException("Unexpected code:" + response);

                       }


                   } catch (Exception e) {
                       e.printStackTrace();
                   }

               }
           }).start();


    }

    private boolean isPwdNo(String pwd) {
        int length = pwd.length();
        if (length>=6){
            return true;
        }
        return false;
    }
    private boolean isTelNo(String mobile) {
        Pattern compile = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = compile.matcher(mobile);
        return matcher.matches();
    }
}
