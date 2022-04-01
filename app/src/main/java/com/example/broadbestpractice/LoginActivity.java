package com.example.broadbestpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences remember_Password;
    private SharedPreferences.Editor editor;
    private CheckBox  remember_PassCheck;
    private EditText account;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.textpassword);

        remember_Password = PreferenceManager.getDefaultSharedPreferences(this);
        remember_PassCheck = (CheckBox) findViewById(R.id.remember_pass);

        Boolean isRemember = remember_Password.getBoolean("remember_password",false);
        if(isRemember){
            String account_String = remember_Password.getString("account","");
            String password_String = remember_Password.getString("password","");
            account.setText(account_String);
            account.setSelection(account_String.length());
            password.setText(password_String);
            password.setSelection(password_String.length());
            remember_PassCheck.setChecked(true);
        }


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account_string = account.getText().toString();
                String password_string = password.getText().toString();

                if(account_string.equals("admin") && password_string.equals("123456")){

                    editor = remember_Password.edit();
                    if(remember_PassCheck.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account_string);
                        editor.putString ("password",password_string);
                    }
                    else{
                        editor.clear();
                        /*将SharedPreference文件的全部数据擦掉*/
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "account OR password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}