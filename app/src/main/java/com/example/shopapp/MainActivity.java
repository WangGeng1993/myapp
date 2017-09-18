package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.shopapp.baseinfo.Connectinfo;
import com.example.shopapp.util.Md5Util;
import com.example.shopapp.util.MyApplication;
import com.example.shopapp.util.MyVolleyRequest;
import com.example.shopapp.util.PrefStore;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    private Button btnLogin;
    private EditText usernameEd;
    private EditText passwordEd;
    private CheckBox isAutoLoginCB;
    private Intent intent;
    private String userName;
    private String userPassword;
    private Boolean isAutoLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        usernameEd=(EditText)this.findViewById(R.id.editText1);
        passwordEd=(EditText)this.findViewById(R.id.editText2);
        isAutoLoginCB=(CheckBox)findViewById(R.id.checkBox1);
        usernameEd.setText("user");
        passwordEd.setText("123");

        usernameEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String username = usernameEd.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    usernameEd.setError("请输入用户名");
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = usernameEd.getText().toString();
                userPassword = passwordEd.getText().toString();
                isAutoLogin = isAutoLoginCB.isChecked();
                Log.d("11111111", "222222222222222222222");
                Map<String,String> params=new HashMap<String, String>();
                params.put("userName",userName);
                params.put("userPass",userPassword);

                Log.d("11111111", String.valueOf(params)+Connectinfo.loginurl);


                //像后台发送请求
                MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.loginurl, params, MainActivity.this);
                //将请求添加到Volley请求队列
                MyApplication.getInstance().addToRequestQueue(mr,"login");


            }

        });
        TextView reg=(TextView)findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,RegActivity.class);
                startActivityForResult(intent, 200);
            }
        });
    }

    //Volley请求成功的回调函数
    @Override
    public void onResponse(JSONObject response) {
        // TODO Auto-generated method stub
        if(response!=null){
            Log.d("11111111", String.valueOf(response));

            if(response.optString("loginFlag").equals("1")){
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                this.finish();//关闭当前登录窗口
                PrefStore pref = PrefStore.getInstance(MainActivity.this);
                if(isAutoLogin){//选中自动登录
                    //authority格式：MD5加密过的(username+’,’+pwd)（MD5为32位小写）
                    pref.savePref("userName", userName);
                    pref.savePref("authority", Md5Util.getMD5Str(userName+","+Md5Util.getMD5Str(userPassword)));
                    Log.v("authority",Md5Util.getMD5Str(userName+","+Md5Util.getMD5Str(userPassword)));
                }
                pref.savePref("curUserName", userName);
            }
            else{
                Toast.makeText(MainActivity.this, response.optString("msg"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
