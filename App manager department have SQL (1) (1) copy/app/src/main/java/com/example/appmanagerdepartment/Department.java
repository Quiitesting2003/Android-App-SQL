package com.example.appmanagerdepartment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Department extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);
    private classDepartment selectedDepartment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Button bt_exit =(Button) findViewById(R.id.bt_x);
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button bt_add =(Button) findViewById(R.id.bt_add);
        Button bt_search =(Button) findViewById(R.id.bt_search);
        Button bt_update =(Button) findViewById(R.id.bt_update);
        Button bt_delete =(Button) findViewById(R.id.bt_delete);
        EditText ed_enterthecode =(EditText) findViewById(R.id.enterthecode);
        EditText ed_enterthedepartmentname =(EditText) findViewById(R.id.enterthedepartmentname);
        EditText ed_enterthephonenumber =(EditText) findViewById(R.id.enterthephonenumber);
        ListView listview =(ListView) findViewById(R.id.listview);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classDepartment dep=new classDepartment();
                dep.setCode(ed_enterthecode.getText().toString());
                dep.setName(ed_enterthedepartmentname.getText().toString());
                dep.setPhone(ed_enterthephonenumber.getText().toString());
                if(dbHelper.insertDepartment(dep) > 0)
                    Toast.makeText(getApplicationContext(),
                            "Save successfully!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),
                            "Save error!", Toast.LENGTH_LONG).show();
            }
        });
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<classDepartment> list_dep;
                ArrayList<String> list_string = new ArrayList<>();
                if(ed_enterthecode.getText().toString().isEmpty()&&ed_enterthephonenumber.getText().toString().isEmpty()&&ed_enterthedepartmentname.getText().toString().isEmpty()){
                    list_dep = dbHelper.getAllDepartment();}
                else
                    list_dep=dbHelper.searchDepartments(ed_enterthecode.getText().toString(),ed_enterthedepartmentname.getText().toString(),ed_enterthephonenumber.getText().toString());
                for ( classDepartment dep:list_dep){
                    list_string.add(dep.toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        Department.this,
                        android.R.layout.simple_list_item_1, list_string);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener((parent, view, position, id) -> {
                    selectedDepartment = list_dep.get(position);
                    ed_enterthecode.setText(selectedDepartment.getCode());
                    ed_enterthedepartmentname.setText(selectedDepartment.getName());
                    ed_enterthephonenumber.setText(selectedDepartment.getPhone());
                });
            }});
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                classDepartment dep = new classDepartment();
                dep.setCode(ed_enterthecode.getText().toString());
                dep.setName(ed_enterthedepartmentname.getText().toString());
                dep.setPhone(ed_enterthephonenumber.getText().toString());
                int updateCount = dbHelper.updateDepartment(dep);
                if (updateCount > 0) {
                    Toast.makeText(getApplicationContext(), "Department updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Department not found or update failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDepartment == null) {
                    Toast.makeText(getApplicationContext(), "Please select a department to delete.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int deleteCount = dbHelper.deleteDepartment(selectedDepartment.getCode());

                if (deleteCount > 0) {
                    Toast.makeText(getApplicationContext(), "Department deleted successfully!", Toast.LENGTH_SHORT).show();
                    listview.setAdapter(null);
                    selectedDepartment = null;
                    ed_enterthecode.setText("");
                    ed_enterthedepartmentname.setText("");
                    ed_enterthephonenumber.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Department not found or deletion failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}