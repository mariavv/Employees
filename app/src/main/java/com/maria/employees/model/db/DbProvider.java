package com.maria.employees.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maria.employees.model.entity.Employee;
import com.maria.employees.model.entity.Speciality;

import java.util.List;

public class DbProvider {

    public static final String EMP_ID = SqliteHelper.EMP_COLUMN_ID;
    public static final String EMP_NAME = SqliteHelper.EMP_COLUMN_NAME;
    public static final String EMP_LAST_NAME = SqliteHelper.EMP_COLUMN_LAST_NAME;
    public static final String EMP_BIRTH_DATE = SqliteHelper.EMP_COLUMN_BIRTH_DATE;

    public static final String SPEC_ID = SqliteHelper.SPEC_COLUMN_ID;
    public static final String SPEC_NAME = SqliteHelper.SPEC_COLUMN_NAME;

    public static final String SPEC_NAME_EMP = "speciality";

    private SQLiteDatabase mDb;

    public DbProvider(Context context) {
        SqliteHelper sqliteHelper = new SqliteHelper(context);
        mDb = sqliteHelper.getWritableDatabase();
    }

    public void saveData(List<Employee> employees) {
        for (Employee employee : employees) {
            long employeeId = insertEmployee(employee);
            insertSpecialities(employee.getSpecialities());
            insertLinkEmpSpec(employeeId, employee.getSpecialities());
        }
    }

    public Cursor getSpecialities() {
        return mDb.query(SqliteHelper.SPECIALITIES_TABLE_NAME, null, null, null,
                null, null, SqliteHelper.SPEC_COLUMN_NAME);
    }

    public Cursor getEmployees(int specialityId) {
        String sqlQuery = "select distinct emp." + SqliteHelper.EMP_COLUMN_ID
                + " , emp." + SqliteHelper.EMP_COLUMN_LAST_NAME
                + " , emp." + SqliteHelper.EMP_COLUMN_NAME + " , emp." + SqliteHelper.EMP_COLUMN_BIRTH_DATE
                + " from " + SqliteHelper.EMPLOYEES_TABLE_NAME + " as emp , "
                + SqliteHelper.LINK_TABLE_NAME + " as lk "
                + " where lk." + SqliteHelper.LINK_COLUMN_SPECIALITY_ID + " = " + specialityId
                + " and emp." + SqliteHelper.EMP_COLUMN_ID + " = lk." + SqliteHelper.LINK_COLUMN_EMPLOYEE_ID
                + " order by " + SqliteHelper.EMP_COLUMN_LAST_NAME + ", " + SqliteHelper.EMP_COLUMN_NAME;
        return mDb.rawQuery(sqlQuery, null);
    }

    public Cursor getEmployee(int employeeId) {
        String sqlQuery = "select emp." + SqliteHelper.EMP_COLUMN_LAST_NAME
                + " , emp." + SqliteHelper.EMP_COLUMN_NAME
                + " , emp." + SqliteHelper.EMP_COLUMN_BIRTH_DATE
                + " , spec." + SqliteHelper.SPEC_COLUMN_NAME + " as " + SPEC_NAME_EMP
                + " from " + SqliteHelper.EMPLOYEES_TABLE_NAME + " as emp, "
                + SqliteHelper.LINK_TABLE_NAME + " as lk, "
                + SqliteHelper.SPECIALITIES_TABLE_NAME + " as spec "
                + " where emp." + SqliteHelper.EMP_COLUMN_ID + " = " + employeeId
                + " and spec." + SqliteHelper.SPEC_COLUMN_ID + " = lk." + SqliteHelper.LINK_COLUMN_SPECIALITY_ID
                + " and emp." + SqliteHelper.EMP_COLUMN_ID + " = lk." + SqliteHelper.LINK_COLUMN_EMPLOYEE_ID;
        return mDb.rawQuery(sqlQuery, null);
    }

    public void clearTables() {
        mDb.delete(SqliteHelper.LINK_TABLE_NAME, null, null);
        mDb.delete(SqliteHelper.EMPLOYEES_TABLE_NAME, null, null);
        mDb.delete(SqliteHelper.SPECIALITIES_TABLE_NAME, null, null);
    }

    private long insertEmployee(Employee employee) {
        ContentValues cv = new ContentValues();
        Formatter formatter = new Formatter();
        cv.put(SqliteHelper.EMP_COLUMN_NAME, formatter.formatName(employee.getName()));
        cv.put(SqliteHelper.EMP_COLUMN_LAST_NAME, formatter.formatName(employee.getLastName()));
        cv.put(SqliteHelper.EMP_COLUMN_BIRTH_DATE, formatter.formatDate(employee.getBirthDate()).toString());
        return mDb.insert(SqliteHelper.EMPLOYEES_TABLE_NAME, null, cv);
    }

    private void insertSpecialities(List<Speciality> specialities) {
        for (Speciality speciality : specialities) {
            if (!hasSpeciality(speciality)) {
                insertSpeciality(speciality);
            }
        }
    }

    private boolean hasSpeciality(Speciality speciality) {
        Cursor c = mDb.query(SqliteHelper.SPECIALITIES_TABLE_NAME, null, null, null,
                null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(SqliteHelper.SPEC_COLUMN_ID);

            do {
                if ((Integer.parseInt(speciality.getId()) == c.getInt(idColIndex))) {
                    c.close();
                    return true;
                }
            } while (c.moveToNext());
        }

        c.close();
        return false;
    }

    private void insertSpeciality(Speciality speciality) {
        ContentValues cv = new ContentValues();
        cv.put(SqliteHelper.SPEC_COLUMN_ID, speciality.getId());
        cv.put(SqliteHelper.SPEC_COLUMN_NAME, speciality.getName());
        mDb.insert(SqliteHelper.SPECIALITIES_TABLE_NAME, null, cv);
    }

    private void insertLinkEmpSpec(long employeeId, List<Speciality> specs) {
        for (Speciality spec : specs) {
            ContentValues cv = new ContentValues();
            cv.put(SqliteHelper.LINK_COLUMN_EMPLOYEE_ID, employeeId);
            cv.put(SqliteHelper.LINK_COLUMN_SPECIALITY_ID, spec.getId());
            mDb.insert(SqliteHelper.LINK_TABLE_NAME, null, cv);
        }
    }
}
