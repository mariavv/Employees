package com.maria.employees.ui.fragment.employees;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maria.employees.R;
import com.maria.employees.ui.fragment.employee.EmployeeFragment;
import com.maria.employees.ui.uitools.UiTools;

public class EmployeesFragment extends Fragment implements EmployeesView,
        EmployeesFeedAdapter.OnItemClickListener {

    private static final String ARG_SPECIALITY_ID = "speciality_id";

    private View mView;
    private RecyclerView mRecycler;
    private EmployeesFeedAdapter mAdapter;

    private EmployeesPresenter mPresenter = new EmployeesPresenter();

    private int mSpecialityId;

    public EmployeesFragment() {
        // Required empty public constructor
    }

    public static EmployeesFragment newInstance(String specialityId) {
        EmployeesFragment fragment = new EmployeesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPECIALITY_ID, specialityId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSpecialityId = Integer.parseInt(getArguments().getString(ARG_SPECIALITY_ID));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_employees, container, false);
        configureViews();

        mPresenter.onCreateView(this, getContext());

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart(mSpecialityId);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter == null) {
            configureRecyclerView();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onItemClick(String employeeId) {
        mPresenter.onItemClick(employeeId);
    }

    @Override
    public void showEmployees(Cursor employees) {
        mAdapter.updateItems(employees);
    }

    @Override
    public void showEmployee(String employeeId) {
        EmployeeFragment employeeFragment = EmployeeFragment.newInstance(employeeId);
        if (getFragmentManager() != null) {
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.add(R.id.employees_containier, employeeFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        } else {
            UiTools.showMessage(getString(R.string.error_message), getContext());
        }
    }

    private void configureViews() {
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mRecycler = mView.findViewById(R.id.recycler);
        mAdapter = new EmployeesFeedAdapter(this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
