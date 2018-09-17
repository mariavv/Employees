package com.maria.employees.ui.fragment.employees;

import android.database.Cursor;

public interface EmployeesView {
    void showEmployees(Cursor employees);

    void showEmployee(String employeeId);
}
