package com.maria.employees.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class SqliteHelper extends SQLiteOpenHelper {

    static final String EMPLOYEES_TABLE_NAME = "Employees";
    static final String SPECIALITIES_TABLE_NAME = "Specialities";
    static final String LINK_TABLE_NAME = "EmployeesSpecialities";
    static final String EMP_COLUMN_ID = "id";
    static final String EMP_COLUMN_NAME = "name";
    static final String EMP_COLUMN_LAST_NAME = "lastName";
    static final String EMP_COLUMN_BIRTH_DATE = "birthDate";
    static final String SPEC_COLUMN_ID = "id";
    static final String SPEC_COLUMN_NAME = "name";
    static final String LINK_COLUMN_ID = "id";
    static final String LINK_COLUMN_EMPLOYEE_ID = "employeeId";
    static final String LINK_COLUMN_SPECIALITY_ID = "specialityId";
    private static final String DB_NAME = "employees";
    private static final int DB_VERSION = 1;

    SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + EMPLOYEES_TABLE_NAME + " ( "
                + EMP_COLUMN_ID + " integer primary key, "
                + EMP_COLUMN_NAME + " text, "
                + EMP_COLUMN_LAST_NAME + " text, "
                + EMP_COLUMN_BIRTH_DATE + " timestamp "
                + " ); "
        );

        db.execSQL(" create table " + SPECIALITIES_TABLE_NAME + " ("
                + SPEC_COLUMN_ID + " integer primary key, "
                + SPEC_COLUMN_NAME + " text "
                + " ); "
        );

        db.execSQL(" create table " + LINK_TABLE_NAME + " ("
                + LINK_COLUMN_ID + " integer primary key, "
                + LINK_COLUMN_EMPLOYEE_ID + " text, "
                + LINK_COLUMN_SPECIALITY_ID + " text, "
                + "FOREIGN KEY (" + LINK_COLUMN_EMPLOYEE_ID + " ) REFERENCES " + EMPLOYEES_TABLE_NAME
                + " ( " + EMP_COLUMN_ID + " ) ON DELETE CASCADE ON UPDATE CASCADE, "
                + "FOREIGN KEY (" + LINK_COLUMN_SPECIALITY_ID + ") REFERENCES " + SPECIALITIES_TABLE_NAME
                + " ( " + SPEC_COLUMN_ID + " ) ON DELETE CASCADE ON UPDATE CASCADE "
                + " ); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + EMPLOYEES_TABLE_NAME + ";");
        db.execSQL("drop table " + SPECIALITIES_TABLE_NAME + ";");
        db.execSQL("drop table " + LINK_TABLE_NAME + ";");
        onCreate(db);
    }
}
