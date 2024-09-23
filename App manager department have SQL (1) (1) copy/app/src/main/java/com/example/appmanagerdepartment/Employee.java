package com.example.appmanagerdepartment;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Employee extends AppCompatActivity {
    ArrayList<String> listItem = new ArrayList<>();
    ArrayList<classDepartment> list_dep = new ArrayList<>();
    DBHelper dbhelper = new DBHelper(this);
    Bitmap emp_image;
    ImageView imgView;
    ListView listview_employee;
    private classEmployee selectedEmployee = null;
    private EmployeeListAdapter employeeAdapter;
    private List<classEmployee> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        EditText edittext_id = findViewById(R.id.edittext_id);
        EditText edittext_name = findViewById(R.id.edittext_name);
        EditText edittext_address = findViewById(R.id.edittext_address);
        EditText edittext_phone = findViewById(R.id.edittext_phone);

        RadioGroup radiogroup_gender = findViewById(R.id.radiogroup_gender);
        RadioButton radio_male = findViewById(R.id.radio_male);
        RadioButton radio_female = findViewById(R.id.radio_female);

        Button button_add = findViewById(R.id.button_add);
        Button button_search = findViewById(R.id.button_search);
        Button button_update = findViewById(R.id.button_update);
        Button button_delete = findViewById(R.id.button_delete);
        Button bt_new_image = findViewById(R.id.button_new_image);
        Button button_exit = findViewById(R.id.button_exit);
        imgView = findViewById(R.id.image_employee);
        listview_employee = findViewById(R.id.listview_employee);
        list_dep = dbhelper.getAllDepartment();
        for (classDepartment dep : list_dep)
            listItem.add(dep.getCode() + "\t" + dep.getName());
        Spinner spinner_department = findViewById(R.id.spinner_department);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        spinner_department.setAdapter(adapter);

        bt_new_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 9999);
            }
        });

        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
                finish();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classEmployee emp = new classEmployee();
                emp.setImage(ConverttoArrayByte(imgView));
                emp.setEmpcode(edittext_id.getText().toString());
                emp.setName(edittext_name.getText().toString());
                String gender = ((RadioButton) findViewById(radiogroup_gender.getCheckedRadioButtonId())).getText().toString();
                emp.setGender(gender);
                emp.setAddress(edittext_address.getText().toString());
                emp.setPhone(edittext_phone.getText().toString());
                String[] depCode = (spinner_department.getSelectedItem().toString().split("\t"));
                emp.setDepcode(depCode[0]);

                if (dbhelper.insertEmployee(emp) > 0) {
                    Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchId = edittext_id.getText().toString().trim();
                String searchName = edittext_name.getText().toString().trim();
                String searchPhone = edittext_phone.getText().toString().trim();
                String searchAddress = edittext_address.getText().toString().trim();

                ArrayList<classEmployee> filteredEmployees = dbhelper.searchEmployees(searchId, searchName, searchPhone, searchAddress);

                employeeList.clear();
                employeeList.addAll(filteredEmployees);
                employeeAdapter.notifyDataSetChanged();
            }
        });

        employeeAdapter = new EmployeeListAdapter(this, employeeList);
        listview_employee.setAdapter(employeeAdapter);
        listview_employee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployee = employeeList.get(position);
                edittext_id.setText(selectedEmployee.getEmpcode());
                edittext_name.setText(selectedEmployee.getName());
                edittext_phone.setText(selectedEmployee.getPhone());
                edittext_address.setText(selectedEmployee.getAddress());

                String gender = selectedEmployee.getGender();
                if (gender.equals("Male")) {
                    radio_male.setChecked(true);
                } else {
                    radio_female.setChecked(true);
                }

                Bitmap imageBitmap = BitmapFactory.decodeByteArray(selectedEmployee.getImage(), 0, selectedEmployee.getImage().length);
                imgView.setImageBitmap(imageBitmap);
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedEmployee != null) {
                    String currentEmpcode = selectedEmployee.getEmpcode();
                    String currentName = selectedEmployee.getName();
                    String currentPhone = selectedEmployee.getPhone();
                    String currentAddress = selectedEmployee.getAddress();
                    String currentGender = selectedEmployee.getGender();
                    byte[] currentImage = selectedEmployee.getImage();
                    String currentDepcode = selectedEmployee.getDepcode();
                    String updatedEmpcode = edittext_id.getText().toString();
                    String updatedName = edittext_name.getText().toString();
                    String updatedPhone = edittext_phone.getText().toString();
                    String updatedAddress = edittext_address.getText().toString();
                    String updatedGender = ((RadioButton) findViewById(radiogroup_gender.getCheckedRadioButtonId())).getText().toString();
                    byte[] updatedImage = (emp_image != null) ? ConverttoArrayByte(imgView) : currentImage;
                    String[] depCode = (spinner_department.getSelectedItem().toString()).split("\t");
                    String updatedDepcode = depCode[0];

                    boolean changesMade = !updatedEmpcode.equals(currentEmpcode) ||
                            !updatedName.equals(currentName) ||
                            !updatedPhone.equals(currentPhone) ||
                            !updatedAddress.equals(currentAddress) ||
                            !updatedGender.equals(currentGender) ||
                            !updatedDepcode.equals(currentDepcode) ||
                            (updatedImage != null && !Base64.encodeToString(updatedImage, Base64.DEFAULT).equals(Base64.encodeToString(currentImage, Base64.DEFAULT)));

                    if (changesMade) {
                        selectedEmployee.setEmpcode(updatedEmpcode);
                        selectedEmployee.setName(updatedName);
                        selectedEmployee.setPhone(updatedPhone);
                        selectedEmployee.setAddress(updatedAddress);
                        selectedEmployee.setGender(updatedGender);
                        selectedEmployee.setImage(updatedImage);
                        selectedEmployee.setDepcode(updatedDepcode);

                        if (dbhelper.updateEmployee(selectedEmployee) > 0) {
                            Toast.makeText(getApplicationContext(), "Employee updated successfully!", Toast.LENGTH_LONG).show();
                            clearFields();
                            refreshEmployeeList();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to update employee!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No changes made.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please select an employee to update.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9999) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (photo == null) {
                Toast.makeText(getApplicationContext(), "No Image!", Toast.LENGTH_LONG).show();
            } else {
                emp_image = photo;
                imgView.setImageBitmap(photo);
            }
        }
    }

    public byte[] ConverttoArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        if (bitmapDrawable == null) {
            return new byte[0];
        }
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    private void clearFields() {
        EditText edittext_id = findViewById(R.id.edittext_id);
        EditText edittext_name = findViewById(R.id.edittext_name);
        EditText edittext_phone = findViewById(R.id.edittext_phone);
        EditText edittext_address = findViewById(R.id.edittext_address);
        RadioGroup radiogroup_gender = findViewById(R.id.radiogroup_gender);
        ImageView imgView = findViewById(R.id.image_employee);

        edittext_id.setText("");
        edittext_name.setText("");
        edittext_phone.setText("");
        edittext_address.setText("");
        radiogroup_gender.clearCheck();
        imgView.setImageResource(R.mipmap.ic_launcher);
        selectedEmployee = null;
    }

    private void refreshEmployeeList() {
        employeeList.clear();
        employeeList.addAll(dbhelper.getAllEmployee());
        employeeAdapter.notifyDataSetChanged();
    }

}
