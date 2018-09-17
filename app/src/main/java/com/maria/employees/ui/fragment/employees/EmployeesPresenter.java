package com.maria.employees.ui.fragment.employees;

import android.content.Context;

import com.maria.employees.model.db.DbProvider;

public class EmployeesPresenter {

    private Context mContext;
    private EmployeesView mView;

    public void onCreateView(EmployeesView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void onStart(int specialityId) {
        mView.showEmployees(new DbProvider(mContext).getEmployees(specialityId));
    }

    public void onDestroy() {
        mView = null;
    }

    public void onItemClick(String employeeId) {
        mView.showEmployee(employeeId);
    }
}
