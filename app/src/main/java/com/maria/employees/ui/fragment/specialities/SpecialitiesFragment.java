package com.maria.employees.ui.fragment.specialities;

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
import com.maria.employees.ui.fragment.employees.EmployeesFragment;
import com.maria.employees.ui.uitools.UiTools;

public class SpecialitiesFragment extends Fragment implements SpecialitiesView,
        SpecialtiesFeedAdapter.OnItemClickListener {

    private View mView;
    private RecyclerView mRecycler;
    private SpecialtiesFeedAdapter mAdapter;

    private SpecialitiesPresenter mPresenter = new SpecialitiesPresenter();

    public SpecialitiesFragment() {
    }

    public static SpecialitiesFragment newInstance() {
        return new SpecialitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_specialities, container, false);

        mPresenter.onCreateView(this, getContext());

        configureViews();

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter == null) {
            configureViews();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showSpecialities(Cursor specialities) {

        specialities.moveToFirst();
        mAdapter.updateItems(specialities);
    }

    @Override
    public void onItemClick(String specialityId) {
        mPresenter.onItemClick(specialityId);
    }

    @Override
    public void showEmployees(String specialityId) {
        EmployeesFragment employeesFragment = EmployeesFragment.newInstance(specialityId);
        if (getFragmentManager() != null) {
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.add(R.id.specialities_containier, employeesFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        } else {
            UiTools.showMessage(getString(R.string.error_message), getContext());
        }
    }

    private void configureViews() {
        mRecycler = mView.findViewById(R.id.recycler);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mAdapter = new SpecialtiesFeedAdapter(this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
