package com.maria.employees.ui.fragment.employees;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maria.employees.R;
import com.maria.employees.model.db.DbProvider;

class EmployeesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String FULL_NAME = "%s %s";

    private View view;
    private TextView name;
    private Cursor mItems;
    private Listener mListener;

    EmployeesViewHolder(View itemView, Cursor items, Listener listener) {
        super(itemView);

        this.mItems = items;
        this.mListener = listener;

        view = itemView.findViewById(R.id.item_view);
        view.setOnClickListener(this);
        name = itemView.findViewById(R.id.name);
    }

    void bindData() {
        name.setText(String.format(FULL_NAME,
                mItems.getString(mItems.getColumnIndex(DbProvider.EMP_LAST_NAME)),
                mItems.getString(mItems.getColumnIndex(DbProvider.EMP_NAME))));
        view.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            mItems.moveToPosition(position);
            mListener.itemClick();
        }
    }

    public interface Listener {
        void itemClick();
    }
}
