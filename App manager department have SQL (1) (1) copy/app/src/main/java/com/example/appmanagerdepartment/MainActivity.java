package com.example.appmanagerdepartment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et_user=findViewById(R.id.edit_username);
        EditText et_password=findViewById(R.id.edit_password);
        Button bt_login=findViewById(R.id.button_login);
        Button bt_signup=findViewById(R.id.button_create);
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences ref=getApplicationContext().getSharedPreferences("mydb",0);
                String name=ref.getString("name",null);
                String password=ref.getString("password",null);
                if(name.equals(et_user.getText().toString())&& password.equals(et_password.getText().toString())){
                    Intent intent2=new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent2);
                }
                else {
                    AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Invalid username or password");
                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
            }
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this, Signup.class);
                startActivity(intent1);
            }
        });

    }
}