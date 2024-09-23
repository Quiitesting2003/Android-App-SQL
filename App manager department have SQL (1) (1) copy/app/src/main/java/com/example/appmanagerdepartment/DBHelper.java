package com.example.appmanagerdepartment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "onedrive", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Departments(" + " depcode text primary key, " + " name text," + "phone text);");
        db.execSQL("Create table Employees(" + "emcode primary key," + "emname text," + "emgender text," + "emphone text," + "emaddress text," + "image Blob," + "depcode integer not null constraint depcode references " +
                "Departments(depcode) ON DELETE CASCADE ON UPDATE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists Departments");
        db.execSQL("Drop table if exists Emplhoyees");
        onCreate(db);
    }

    public int insertDepartment(classDepartment dep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("depcode", dep.getCode());
        content.put("name", dep.getName());
        content.put("phone", dep.getPhone());
        int result = (int) db.insert("Departments", null, content);
        db.close();
        return result;
    }
    public int insertEmployee(classEmployee em) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("image", em.getImage());
        content.put("emcode", em.getEmpcode());
        content.put("emname", em.getName());
        content.put("emgender", em.getGender());
        content.put("emphone", em.getPhone());
        content.put("emaddress", em.getAddress());
        content.put("depcode", em.getDepcode());
        int result = (int) db.insert("Employees", null, content);
        db.close();
        return result;
    }

    public int deleteDepartment(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "depcode=?";
        String[] whereArgs = {id};
        int result = db.delete("Departments", whereClause, whereArgs);
        db.close();
        return result;
    }

    public int deleteEmployee(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "emcode=?";
        String[] whereArgs = {id};
        int result = db.delete("Employees", whereClause, whereArgs);
        db.close();
        return result;
    }

    public int updateDepartment(classDepartment dep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("depcode", dep.getCode());
        content.put("name", dep.getName());
        content.put("phone", dep.getPhone());
        String whereClause = "depcode=?";
        String[] whereArgs = {dep.getCode()};
        int result = db.update("Departments", content, whereClause, whereArgs);
        db.close();
        return result;
    }

    public ArrayList<classDepartment> searchDepartments(String code, String name, String phone) {
        ArrayList<classDepartment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("SELECT * FROM Departments WHERE 1=1");
        ArrayList<String> args = new ArrayList<>();

        if (!code.isEmpty()) {
            query.append(" AND depcode LIKE ?");
            args.add("%" + code + "%");
        }
        if (!name.isEmpty()) {
            query.append(" AND name LIKE ?");
            args.add("%" + name + "%");
        }
        if (!phone.isEmpty()) {
            query.append(" AND phone LIKE ?");
            args.add("%" + phone + "%");
        }

        Cursor cursor = db.rawQuery(query.toString(), args.toArray(new String[0]));
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new classDepartment(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return list;
    }
    public ArrayList<classEmployee> searchEmployees(String code, String name, String phone, String address) {
        ArrayList<classEmployee> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Employees WHERE 1=1";
        ArrayList<String> args = new ArrayList<>();
        if (!code.isEmpty()) {
            query += " AND emcode LIKE ?";
            args.add("%" + code + "%");
        }
        if (!name.isEmpty()) {
            query += " AND emname LIKE ?";
            args.add("%" + name + "%");
        }
        if (!phone.isEmpty()) {
            query += " AND emphone LIKE ?";
            args.add("%" + phone + "%");
        }
        if (!address.isEmpty()) {
            query += " AND emaddress LIKE ?";
            args.add("%" + address + "%");
        }
        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new classEmployee(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getBlob(5), cursor.getString(6)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return list;
    }
    public int updateEmployee(classEmployee em) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("emcode", em.getEmpcode());
        content.put("emname", em.getName());
        content.put("emgender", em.getGender());
        content.put("emphone", em.getPhone());
        content.put("emaddress", em.getAddress());
        content.put("image", em.getImage());
        content.put("depcode", em.getDepcode());
        String whereClause = "emcode=?";
        String[] whereArgs = {em.getEmpcode()};
        int result = db.update("Employees", content, whereClause, whereArgs);
        db.close();
        return result;
    }

    public ArrayList<classDepartment> getAllDepartment() {
        ArrayList<classDepartment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select*from Departments", null);
        {
            if (cursor != null)
                cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new classDepartment(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return list;
        }
    }

    public ArrayList<classEmployee> getAllEmployee() {
        ArrayList<classEmployee> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Employees", null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new classEmployee(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),cursor.getBlob(5),
                    cursor.getString(6)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    public ArrayList<String> getAllDepartmentNames() {
        ArrayList<String> departmentNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Departments", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                departmentNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return departmentNames;
    }
    public classDepartment getDepartmentByCode(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Departments WHERE depcode = ?", new String[]{code});
        if (cursor != null && cursor.moveToFirst()) {
            classDepartment department = new classDepartment(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            db.close();
            return department;
        }
        db.close();
        return null;
    }
}
