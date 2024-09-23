package com.example.appmanagerdepartment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText et_enterusername=findViewById(R.id.edit_newusername);
        EditText et_enterpassword=findViewById(R.id.edit_newpassword);
        EditText et_enterpasswordagain=findViewById(R.id.edit_conpassword);
        Button bt_logininsignin=findViewById(R.id.button_close);
        Button bt_signininsignin=findViewById(R.id.button_newcreate);

        et_enterpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_enterpasswordagain.setTransformationMethod(PasswordTransformationMethod.getInstance());
        bt_logininsignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1=new Intent(Signup.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        bt_signininsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=et_enterpassword.getText().toString();
                String passwordagain=et_enterpasswordagain.getText().toString();
                if(!password.equals(passwordagain)){
                    Toast.makeText(Signup.this,"Confirm password does not match",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences ref=getApplicationContext().getSharedPreferences("mydb",0);
                    SharedPreferences.Editor editor=ref.edit();
                    editor.putString("name",et_enterusername.getText().toString());
                    editor.putString("password",et_enterpassword.getText().toString());
                    editor.commit();
                    Toast.makeText(Signup.this,"Account created successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}