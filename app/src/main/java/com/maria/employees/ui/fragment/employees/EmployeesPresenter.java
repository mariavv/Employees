package com.maria.employees.ui.fragment.employees;

import android.content.Context;

import com.maria.employees.model.db.DbProvider;

class EmployeesPresenter {

    private Context mContext;
    private EmployeesView mView;

    void onCreateView(EmployeesView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    void onStart(int specialityId) {
        mView.showEmployees(new DbProvider(mContext).getEmployees(specialityId));
    }

    void onDestroy() {
        mView = null;
    }

    void onItemClick(String employeeId) {
        mView.showEmployee(employeeId);
    }
}
