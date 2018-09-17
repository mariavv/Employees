package com.maria.employees.ui.fragment.employees;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maria.employees.R;
import com.maria.employees.model.db.DbProvider;

class EmployeesFeedAdapter extends RecyclerView.Adapter<EmployeesViewHolder>
        implements EmployeesViewHolder.Listener {

    private EmployeesFeedAdapter.OnItemClickListener mOnItemClickListener;

    private Cursor mItems;

    EmployeesFeedAdapter(EmployeesFeedAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_employee, parent, false);

        return new EmployeesViewHolder(v, mItems, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesViewHolder holder, int position) {
        mItems.moveToPosition(position);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.getCount();
        }
        return 0;
    }

    @Override
    public void itemClick() {
        mOnItemClickListener.onItemClick(mItems.getString(mItems.getColumnIndex(DbProvider.EMP_ID)));
    }

    public void updateItems(Cursor entities) {
        if (entities != null) {
            mItems = entities;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String employeeId);
    }
}
