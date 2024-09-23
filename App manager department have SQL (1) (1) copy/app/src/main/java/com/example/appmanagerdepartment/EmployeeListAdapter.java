package com.example.appmanagerdepartment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EmployeeListAdapter extends BaseAdapter {
    private List<classEmployee> listEmployee;
    private LayoutInflater layoutInflater;
    private Context context;

    public EmployeeListAdapter(Context context, List<classEmployee> listEmployee) {
        this.context = context;
        this.listEmployee = listEmployee;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listEmployee.size();
    }

    @Override
    public Object getItem(int position) {
        return listEmployee.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_list_layout, null);
            holder = new ViewHolder();
            holder.imgView = convertView.findViewById(R.id.image_employee);
            holder.idView = convertView.findViewById(R.id.textView_id);
            holder.nameView = convertView.findViewById(R.id.textView_name);
            holder.phoneView = convertView.findViewById(R.id.textView_phone);
            holder.depView = convertView.findViewById(R.id.textViewdepartment);
            holder.addressView = convertView.findViewById(R.id.textView_address);
            holder.genderView = convertView.findViewById(R.id.textView_gender);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        classEmployee employee = this.listEmployee.get(position);
        holder.idView.setText("ID: " + employee.getEmpcode());
        holder.nameView.setText("Name: " + employee.getName());
        holder.phoneView.setText("Phone: " + employee.getPhone());
        holder.addressView.setText("Address: " + employee.getAddress());
        holder.genderView.setText("Gender: " + employee.getGender());

        DBHelper dbHelper = new DBHelper(context);
        classDepartment department = dbHelper.getDepartmentByCode(employee.getDepcode());
        holder.depView.setText("Department: " + (department != null ? department.getName() : "Unknown"));

        byte[] imageBytes = employee.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgView.setImageBitmap(decodedImage);
        } else {
            holder.imgView.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgView;
        TextView idView;
        TextView nameView;
        TextView phoneView;
        TextView depView;
        TextView addressView;
        TextView genderView;
    }

    private Bitmap getBitmapFromEncodedString(String encodedString) {
        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return image;
    }
}
