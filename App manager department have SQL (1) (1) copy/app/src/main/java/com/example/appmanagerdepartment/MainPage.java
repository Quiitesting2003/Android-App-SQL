package com.example.appmanagerdepartment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainPage extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id == R.id.employee) {
            Intent intent_employee = new Intent(MainPage.this,Employee.class);
            startActivity(intent_employee);
        }if(id==R.id.department){
            Intent intent_department = new Intent(MainPage.this,Department.class);
            startActivity(intent_department);
        }
        else  {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}