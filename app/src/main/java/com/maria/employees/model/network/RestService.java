package com.maria.employees.model.network;

import com.maria.employees.model.entity.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestService {

    String API_URL = "https://raw.githubusercontent.com/VladShturma/testTask/master/";

    @GET("testTask.json")
    Observable<BaseResponse<List<Employee>>> getEmployees();
}
