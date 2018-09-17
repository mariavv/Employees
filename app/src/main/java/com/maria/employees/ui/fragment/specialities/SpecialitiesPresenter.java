package com.maria.employees.ui.fragment.specialities;

import android.content.Context;

import com.maria.employees.model.db.DbProvider;

class SpecialitiesPresenter {

    private SpecialitiesView mView;
    private Context mContext;

    public void onCreateView(SpecialitiesView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void onStart() {
        mView.showSpecialities(new DbProvider(mContext).getSpecialities());
    }

    void detachView() {
        mView = null;
    }

    public void onItemClick(String specialityId) {
        mView.showEmployees(specialityId);
    }
}
