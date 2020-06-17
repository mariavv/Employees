package com.maria.employees.ui.fragment.employee;

import android.content.Context;

import com.maria.employees.model.db.DbProvider;

class EmployeePresenter {

    static final String BIRTH_DATE_DEFOULT_VALUE = "--";

    private Context mContext;
    private EmployeeView mView;

    void onCreateView(EmployeeView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    void onStart(int employeeId) {
        mView.showEmployee(new DbProvider(mContext).getEmployee(employeeId));
    }

    void onDestroy() {
        mView = null;
    }
}
