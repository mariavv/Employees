package com.maria.employees.ui.fragment.specialities;

import android.database.Cursor;

interface SpecialitiesView {
    void showSpecialities(Cursor specialities);

    void showEmployees(String specialityId);
}
