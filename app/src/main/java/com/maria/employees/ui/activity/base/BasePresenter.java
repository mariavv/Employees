package com.maria.employees.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.maria.employees.R;
import com.maria.employees.model.db.DbProvider;
import com.maria.employees.model.entity.Employee;
import com.maria.employees.model.repository.EmployeesRepo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class BasePresenter {

    private final EmployeesRepo employeesRepo = new EmployeesRepo();
    private BaseView mView;
    private Context mContext;
    private DbProvider mDbProvider;

    void onCreate(BaseView view, boolean turn) {
        this.mView = view;
        this.mContext = (Context) view;
        mDbProvider = new DbProvider(mContext);

        loadData(turn);
    }

    @SuppressLint("CheckResult")
    private void loadData(boolean turn) {
        if (!turn) {
            mView.showProgress();
            employeesRepo.getEmployees()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::saveDataToBd, this::errorGetData);
        }
    }

    void onDestroy() {
        mView = null;
    }

    private void saveDataToBd(List<Employee> employees) {
        mDbProvider.clearTables();
        mDbProvider.saveData(employees);
        mView.showSpecialitiesList();
    }

    private void errorGetData(Throwable throwable) {
        mView.showMessage(String.format(mContext.getString(R.string.message_error_get_data), throwable.getMessage()));
    }
}
