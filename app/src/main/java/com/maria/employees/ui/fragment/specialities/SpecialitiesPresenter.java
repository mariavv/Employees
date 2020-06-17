package com.maria.employees.ui.fragment.specialities;

import android.content.Context;

import com.maria.employees.model.db.DbProvider;

class SpecialitiesPresenter {

    private SpecialitiesView mView;
    private Context mContext;

    void onCreateView(SpecialitiesView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    void onStart() {
        mView.showSpecialities(new DbProvider(mContext).getSpecialities());
    }

    void detachView() {
        mView = null;
    }

    void onItemClick(String specialityId) {
        mView.showEmployees(specialityId);
    }
}
