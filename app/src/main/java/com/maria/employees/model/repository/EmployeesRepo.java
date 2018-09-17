package com.maria.employees.model.repository;

import com.maria.employees.model.entity.Employee;
import com.maria.employees.model.network.BaseResponse;
import com.maria.employees.model.network.RestService;
import com.maria.employees.model.network.RestServiceProvider;

import java.util.List;

import io.reactivex.Observable;

public class EmployeesRepo {

    private RestService mRestService = RestServiceProvider.newInstance().getRestService();

    public Observable<List<Employee>> getEmployees() {
        return mRestService.getEmployees().map(BaseResponse::getData);
    }
}
