package com.maria.employees.ui.fragment.employee;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maria.employees.R;
import com.maria.employees.model.db.DbProvider;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static com.maria.employees.ui.fragment.employee.EmployeePresenter.BIRTH_DATE_DEFOULT_VALUE;

public class EmployeeFragment extends Fragment implements EmployeeView {

    private static final String ARG_EMP_ID = "employeeId";
    private final EmployeePresenter mPresenter = new EmployeePresenter();
    private View mView;
    private int mEmpId;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    public static EmployeeFragment newInstance(String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMP_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String emp_id = bundle.getString(ARG_EMP_ID);
            if (emp_id != null) {
                mEmpId = Integer.parseInt(emp_id);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_employee, container, false);

        mPresenter.onCreateView(this, getContext());

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart(mEmpId);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showEmployee(Cursor employee) {
        if (employee.moveToFirst()) {
            setInfoItem(employee, R.id.last_name, DbProvider.EMP_LAST_NAME);
            setInfoItem(employee, R.id.name, DbProvider.EMP_NAME);

            setInfoBirthDate(employee);

            StringBuilder specialities = new StringBuilder();

            do {
                specialities.append(getValue(employee, DbProvider.SPEC_NAME_EMP)).append(getString(R.string.employee_spec_devider));
            } while (employee.moveToNext());

            setInfoItem(R.id.speciality, specialities.toString());
        }

        employee.close();
    }

    private void setInfoBirthDate(Cursor employee) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        String birthDate = getValue(employee, DbProvider.EMP_BIRTH_DATE);
        Timestamp bd = Timestamp.valueOf(birthDate);

        if (bd.compareTo(current) == 1) {
            setInfoItem(R.id.birth_date, BIRTH_DATE_DEFOULT_VALUE);
        } else {
            setInfoItem(R.id.birth_date, birthDate);

            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(new Date(bd.getDate()));

            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            int age2 = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age2--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age2--;
                }
            }
            setInfoItem(R.id.age, String.valueOf(age2));
        }
    }

    public void setInfoItem(int textViewId, String infoItem) {
        TextView tv = mView.findViewById(textViewId);
        tv.setText(infoItem);
    }

    private void setInfoItem(Cursor employee, int textViewId, String dataColumn) {
        String infoItem = getValue(employee, dataColumn);
        setInfoItem(textViewId, infoItem);
    }

    private String getValue(Cursor employee, String dataColumn) {
        return employee.getString(employee.getColumnIndex(dataColumn));
    }
}
